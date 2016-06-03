package com.gome.storm.topology;

import java.util.Arrays;

import com.gome.storm.bolts.WordCounterBolt;
import com.gome.storm.bolts.WordNormalizerBolt;
import com.gome.storm.spouts.WordReaderSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;


public class TopologyMain {
	private static String file;
	public static void main(String[] args) throws InterruptedException {
		if(args.length>0){
			file = args[0];
		}
        //Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader",new WordReaderSpout(),1);
		builder.setBolt("word-normalizer", new WordNormalizerBolt(),1)
			.shuffleGrouping("word-reader","streamIdA");
		builder.setBolt("word-normalizer2", new WordNormalizer(),1)
		.shuffleGrouping("word-reader","streamIdB");
		builder.setBolt("word-counter", new WordCounterBolt(),1).setNumTasks(2)
//			.shuffleGrouping("word-normalizer")
//			.shuffleGrouping("word-normalizer2");
		.fieldsGrouping("word-normalizer", new Fields("word"))
		.fieldsGrouping("word-normalizer2", new Fields("word"));
		
        //Configuration
		Config conf = new Config();
		conf.put("wordsFile", (file!=null)&&(file!="")?file:"/home/wangzhen-ds5/stormfile");
		conf.setDebug(false);
		conf.setNumWorkers(4);
		conf.setNumAckers(2);
		
		conf.put(Config.STORM_ZOOKEEPER_PORT, 2181);
		conf.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList("10.125.31.5"));
		conf.put("projectName", "storm-test");
		conf.put("appName", "storm-app");
		conf.setNumWorkers(2);
		
//		conf.setMaxTaskParallelism(1);
        //Topology run
//		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
		
		try {
			StormSubmitter.submitTopology("storm-topology", conf, builder.createTopology());
		} catch (AlreadyAliveException | InvalidTopologyException e) {
			e.printStackTrace();
		}
		
//		Thread.sleep(3000);
//		cluster.shutdown();
	}
}
