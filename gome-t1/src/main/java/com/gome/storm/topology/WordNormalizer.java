package com.gome.storm.topology;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordNormalizer implements IRichBolt{ 
	private final static Logger LOGGER = LoggerFactory.getLogger(WordNormalizer.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OutputCollector collector; 
	public void cleanup(){} 
	/** 
	 ***bolt*从单词文件接收到文本行，并标准化它。 
	 ** 文本行会全部转化成小写，并切分它，从中得到所有单词。
	 **/ 
	public void execute(Tuple input){ 
		String sentence = input.getString(0); 
		String testWord = input.getString(1);
		LOGGER.info("---------------streamId:" + input.getSourceStreamId() + " -WordNormalizer- " + input.getSourceComponent());
		String[] words = sentence.split(" "); 
		for(String word : words){ 
			word = word.trim(); 
			if(!word.isEmpty()){ 
				word=word.toLowerCase(); //发布这个单词 
				List<Tuple> a = new ArrayList<Tuple>(); 
				a.add(input); 
				collector.emit(a,new Values(word)); 
			} 
		} //对元组做出应答 
		String test[] = testWord.split(" ");
        for (String string : test) {
        	string = string.trim();
            if(!string.isEmpty()){
            	string = string.toLowerCase();
                collector.emit(new Values(string));
            }
		}
		collector.ack(input); 
	} 
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) { 
		System.setProperty("projectName", (String)stormConf.get("projectName"));
		this.collector=collector; 
	} 
	/** * 这个*bolt*只会发布“word”域 */ 
	public void declareOutputFields(OutputFieldsDeclarer declarer) { 
		declarer.declare(new Fields("word")); 
	}
	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	} 
	
}