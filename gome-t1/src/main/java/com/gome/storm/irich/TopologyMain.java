package com.gome.storm.irich;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;


public class TopologyMain {
	public static void main(String[] args) throws InterruptedException {
        //Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader",new WordReaderSpout(),1);
		builder.setBolt("word-normalizer", new WordNormalizerBolt(),1)
			.shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounterBolt(),1)
			.shuffleGrouping("word-normalizer");
//		.fieldsGrouping("word-normalizer", new Fields("word"));
		
        //Configuration
		Config conf = new Config();
		conf.put("wordsFile", "src/main/resources/word.txt");
		conf.setDebug(false);
//		conf.setNumWorkers(1);
//		conf.setMaxTaskParallelism(1);
        //Topology run
//		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
		Thread.sleep(10000);
		cluster.shutdown();
	}
}
