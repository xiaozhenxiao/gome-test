package com.gome.storm.irich;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class WordCounterBolt extends BaseRichBolt {
	private OutputCollector collector;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer id;
	String name;
	Map<String, Integer> counters;
	 

	/**
	 * At the end of the spout (when the cluster is shutdown
	 * We will show the word counters
	 * 这个spout结束时（集群关闭的时候），我们会显示单词数量
	 */
	@Override
	public void cleanup() {
		System.out.println("-- Word Counter ["+name+"--"+id+"] --");
		for(Map.Entry<String, Integer> entry : counters.entrySet()){
			System.out.println(entry.getKey()+": "+entry.getValue());
		}
	}

	/**
	 * On create 
	 * 初始化
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		this.counters = new HashMap<String, Integer>();
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
		System.out.println("TaskId: " + context.getThisTaskId() + " WordCounterBolt prepare!! ");
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
			System.out.println("WordCounter declareOutputFields!! "+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ms").format(new Date()));
	}

	@Override
	public void execute(Tuple input) {
		String str = input.getString(0);
		
		/**
		 * If the word dosn't exist in the map we will create
		 * this, if not We will add 1 
		 * 如果单词尚不存在于map，我们就创建一个，如果已在，我们就为它加1
		 */
		if(!counters.containsKey(str)){
			counters.put(str, 1);
		}else{
			Integer c = counters.get(str) + 1;
			counters.put(str, c);
		}
//		collector.ack(input);
	}

}
