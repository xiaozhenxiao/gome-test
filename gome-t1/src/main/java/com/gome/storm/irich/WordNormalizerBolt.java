package com.gome.storm.irich;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordNormalizerBolt extends BaseRichBolt {
	private OutputCollector collector;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void cleanup() {
		System.out.println("WordNormalizer cleanup()!! ");
	}

	/**
	 * The bolt will receive the line from the
	 * words file and process it to Normalize this line
	 * 
	 * The normalize will be put the words in lower case
	 * and split the line to get all words in this 
	 */
	@Override
	public void execute(Tuple input) {
		System.out.println("**********************start***********************");
		
		String testWord = input.getStringByField("xiao");
		System.out.println("byName: "+testWord);
		
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
        System.out.println("**********************end***********************\n\r");
//        collector.ack(input);
	}
	

	/**
	 * The bolt will only emit the field "word" 
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		System.out.println("WordNormalizer declareOutputFields!! "+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ms").format(new Date()));
		declarer.declare(new Fields("word"));
		
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		System.out.println("TaskId: " + context.getThisTaskId() + " WordNormalizer prepare!! ");
		
	}
}
