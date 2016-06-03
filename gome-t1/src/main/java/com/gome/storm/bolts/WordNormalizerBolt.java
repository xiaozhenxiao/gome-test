package com.gome.storm.bolts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordNormalizerBolt implements IBasicBolt {
	private final static Logger logger = LoggerFactory.getLogger(WordNormalizerBolt.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void cleanup() {
		logger.info("================================WordNormalizerBolt cleanup()!! ");
	}

	/**
	 * The bolt will receive the line from the
	 * words file and process it to Normalize this line
	 * 
	 * The normalize will be put the words in lower case
	 * and split the line to get all words in this 
	 */
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		logger.info("**********************start***********************");
		
		logger.info("------------streamId:" + input.getSourceStreamId() + " -WordNormalizerBolt- " + input.getSourceComponent());
		
		String testWord = input.getStringByField("xiao");
		logger.info("----------byName: "+testWord);
		
        String sentence = input.getStringByField("line");
        String[] words = sentence.split(" ");
        for(String word : words){
            word = word.trim();
            if(!word.isEmpty()){
                word = word.toLowerCase();
                collector.emit(new Values(word));
            }
        }
        
        String test[] = testWord.split(" ");
        for (String string : test) {
        	string = string.trim();
            if(!string.isEmpty()){
            	string = string.toLowerCase();
                collector.emit(new Values(string));
            }
		}
        logger.info("**********************end***********************\n\r");
	}
	

	/**
	 * The bolt will only emit the field "word" 
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		logger.info("----------------------WordNormalizer declareOutputFields!! "+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ms").format(new Date()));
		declarer.declare(new Fields("word"));
		
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		System.setProperty("projectName", (String)stormConf.get("projectName"));
		logger.info("--------------------TaskId: " + context.getThisTaskId() + " WordNormalizer prepare!! ");
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		logger.info("--------------------WordNormalizerBolt getComponentConfiguration");
		return null;
	}
}
