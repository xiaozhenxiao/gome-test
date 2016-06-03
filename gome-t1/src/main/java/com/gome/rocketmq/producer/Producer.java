package com.gome.rocketmq.producer;

import java.util.concurrent.TimeUnit;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
/**
 * @author wangzhen-ds5
 */
public class Producer {
	
	public static void main(String[] args) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {

		/**
		 * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
		 * 注意： ProducerGroupName需要由应用来保证唯一
		 * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
		 * 因为服务器会查这个Group下的任意一个Producer
		 */
		DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
		producer.setNamesrvAddr("10.125.31.24:9876;10.125.31.25:9876");
		producer.setInstanceName("Producer");
		/**
		 * Producer对象在使用之前必须要调用start初始化，初始化一次即可
		 * 注意：切记不可以在每次发送消息时，都调用start方法
		 */
		producer.start();

		for (int i = 0; i < 1; i++) {
			{// topic tag key body
				Message msg = new Message("TopicTest1", "TagA", "OrderID001",
					"中文测试,什么都不需要".getBytes());
				//1、简单发送消息
				SendResult sendResult = producer.send(msg);
				SendStatus status = sendResult.getSendStatus();
				
				System.out.println(sendResult);
			}
			/*{
				Message msg = new Message("TopicTest11", "TagAA", "OrderID101",
						"HelloMetaQ-TopicTest11".getBytes());
				*//**2、
				 * 严格按照时间消费的模式，这种模式需要用串行方式，生产者生产的时候，这时候生产者需要往特定的队列里有序push
				 * 同时消费者也要按照顺序严格有序消费-----看
				 * @link com.gome.rocketmq.consumer.PushConsumer
				 *//*
				SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
					
					@Override
					public MessageQueue select(List<MessageQueue> mqs, Message message,
							Object arg) {
						Integer id = arg.hashCode();
						int index = id % mqs.size();
						return mqs.get(index);
					}
				}, "");
				System.out.println(sendResult);
			}*/
			
			/*{
				Message msg = new Message("TopicTest2", "TagB", "OrderID063",
					"HelloMetaQ-TopicTest2".getBytes());
				SendResult sendResult = producer.send(msg);
				System.out.println(sendResult.getSendStatus());
			}
			
			{
				Message msg = new Message("TopicTest1", "TagC", "OrderID055",
					"HelloMetaQ-TopicTest1".getBytes());
				SendResult sendResult = producer.send(msg);
				System.out.println(sendResult);
			}*/
		}
		
		TimeUnit.MILLISECONDS.sleep(1000);
		/**
		 * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
		 * 注意： 我们建议在JBoss，tomcat等容器的退出钩子里调用shutdown方法
		 */
		producer.shutdown();
	}

}
