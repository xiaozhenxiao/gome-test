package com.gome.storm.spouts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class WordReaderSpout extends BaseRichSpout {
	private final static Logger logger = LoggerFactory.getLogger(WordReaderSpout.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
//	private FileReader fileReader;
	private boolean completed = false;
	private static Integer flag = 0;
	
	@Override
	public void ack(Object msgId) {
		super.ack(msgId);
		logger.info("------------------------Spout OK:"+msgId);
	}
	@Override
	public void close() {
		logger.info("------------------------Spout close()! ");
	}
	
	@Override
	public void fail(Object msgId) {
		logger.info("------------------Spout FAIL:"+msgId);
	}

	/**
	 * The only thing that the methods will do It is emit each 
	 * file line
	 * 翻译：
	 * 这个方法做的惟一一件事情就是分发文件中的文本行
	 */
	@Override
	public void nextTuple() {
		/**
		 * The nextuple it is called forever, so if we have been readed the file
		 * we will wait and then return
		 * 
		 * 这个方法会不断的被调用，直到整个文件都读完了，我们将等待并返回。
		 */
		logger.info("---------------------Spout nextTuple!! ");
		if(completed){
			try {
				logger.info("-------------------sleep 1s!!");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//Do nothing
			}finally{
				completed = false;
			}
			
			return;
		}
		String str;
		//Open the reader
//		BufferedReader reader = new BufferedReader(fileReader);
		try{
			//Read all lines
//			while((str = reader.readLine()) != null){
				/**
				 * By each line emmit a new value with the line as a their
				 * 
				 * 按行发布一个新值
				 */
			if(flag%10000 == 0){
				str = "happy Birthday to you! " + flag;
				this.collector.emit("streamIdA", new Values(str,"xiaoxiao azhen happy test azhen xiao azhen"),"testIdA");
				this.collector.emit("streamIdB", new Values(str,"xiaoxiao azhen happy test azhen xiao azhen"),"testIdB");
				if(flag == Integer.MAX_VALUE)
					flag = 0;
				flag++;
				completed = true;
			}
		}catch(Exception e){
			throw new RuntimeException("Error reading tuple",e);
		}finally{
		}
	}

	/**
	 * We will create the file and get the collector object
	 * 我们将创建一个文件并维持一个collector对象
	 */
	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		System.setProperty("projectName", (String)conf.get("projectName"));
//		try {
//			this.fileReader = new FileReader(conf.get("wordsFile").toString());
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException("Error reading file ["+conf.get("wordFile")+"]");
//		}
		this.collector = collector;
		logger.info("----------------Spout open()!! ");
	}

	/**
	 * Declare the output field "word"
	 * 声明输入域"word"
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		logger.info("-------------------------Spout declareOutputFields");
//		当发送单个tuple时
//		declarer.declare(new Fields("line","xiao"));
		declarer.declareStream("streamIdA",new Fields("line","xiao"));
		declarer.declareStream("streamIdB", new Fields("line","xiao"));
	}
	
}
