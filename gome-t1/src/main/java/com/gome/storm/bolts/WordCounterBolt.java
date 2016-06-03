package com.gome.storm.bolts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class WordCounterBolt extends BaseBasicBolt {
	private final static Logger logger = LoggerFactory.getLogger(WordCounterBolt.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Map<String, Integer> counters;
	private int flag = 0;
	 
	/**
	 * At the end of the spout (when the cluster is shutdown
	 * We will show the word counters
	 * 这个spout结束时（集群关闭的时候），我们会显示单词数量
	 */
	@Override
	public void cleanup() {
		logger.info("-------------- Word Counter ["+name+"--"+id+"] --");
	}

	/**
	 * On create 
	 * 初始化
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		System.setProperty("projectName", (String)stormConf.get("projectName"));
		this.counters = new HashMap<String, Integer>();
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
		logger.info("-------------------TaskId: " + context.getThisTaskId() + " WordCounterBolt prepare!! ");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
			logger.info("----------------WordCounter declareOutputFields!! "+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ms").format(new Date()));
	}


	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		
		String str = input.getString(0);
		
		/**
		 * If the word dosn't exist in the map we will create
		 * this, if not We will add 1 
		 * 如果单词尚不存在于map，我们就创建一个，如果已在，我们就为它加1
		 */
		if(!counters.containsKey(str)){
			counters.put(str, 1);
			flag++;
		}else{
			Integer c = counters.get(str) + 1;
			counters.put(str, c);
			flag++;
		}
		if (flag > 10) {
			logger.info("-------------- Word Counter [" + name + "--" + id + "] --");
			for (Map.Entry<String, Integer> entry : counters.entrySet()) {
				logger.info("--------------" + entry.getKey() + " : " + entry.getValue());
			}
			flag = 0;
		}
		
	}
}
