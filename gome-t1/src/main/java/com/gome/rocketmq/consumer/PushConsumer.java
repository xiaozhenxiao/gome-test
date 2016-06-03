package com.gome.rocketmq.consumer;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageConst;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

public class PushConsumer {
	
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
		consumer.setNamesrvAddr("10.125.31.24:9876;10.125.31.25:9876");
		consumer.setInstanceName("Consumer");
		//订阅指定topic下tags分别等于TagA或TagC或TagD
		consumer.subscribe("TopicTest1", "TagA || TagC || TagD");
		/**
		 * 订阅指定topic下所有消息<br>
		 * 注意: 一个consumer对象可以订阅多个topic
		 */
//		consumer.subscribe("TopicTest2", "*");
		
//		consumer.setConsumeMessageBatchMaxSize(2);
		
		/**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		
        consumer.setMessageModel(MessageModel.CLUSTERING);
        
		/****************************************************************
		 ***                      顺序消费监听                                                                                 ***
		 ***                       start                             ***
		****************************************************************/
		/*consumer.registerMessageListener(new MessageListenerOrderly() {
			
			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
					ConsumeOrderlyContext context) {
				System.out.println("orderly consume: "+msgs.get(0));
				return ConsumeOrderlyStatus.SUCCESS;
			}
		});*/
		
		/****************************************************************
		 ***                       顺序消费监听                                                                               ***
		 ***                         end                              ***
		****************************************************************/
		
		//////////////////////////////////////////////////////////////////////
		
		/****************************************************************
		 ***                      普通消费监听                                                                                 ***
		 ***                       start                             ***
		****************************************************************/
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			/**
			 * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
			 */
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
					ConsumeConcurrentlyContext context) {
				System.out.println(Thread.currentThread().getName() + " Receive NewMessages: "
						+ msgs.size());
				
				/**
				 * 发生消息堆积时，如果消费速度一直跟不上发送速度，可以尝试丢弃不重要的消息
				 */
				/*long offset = msgs.get(0).getQueueOffset();
				String maxOffset = msgs.get(0).getProperty(MessageConst.PROPERTY_MAX_OFFSET);
				long diff = Long.valueOf(maxOffset) - offset;
				if(diff>100000){
					//处理消息堆积情况
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}*/
				/****************************************************************/
				
				MessageExt msg = msgs.get(0);
				System.out.println("msg size: " + msgs.size());
				if(msg.getTopic().equals("TopicTest1")){
					//执行TopicTest1的消费逻辑
					if(msg.getTags() != null && msg.getTags().equals("TagA")){
						//执行TagA的消费
						System.out.println("TagA> "+new String(msg.getBody()));
					}else if(msg.getTags() != null && msg.getTags().equals("TagC")){
						//执行TagC的消费
						System.out.println("TagC> "+new String(msg.getBody()));
					}else if (msg.getTags() != null && msg.getTags().equals("TagD")) {
						//执行TagD的消费
						System.out.println("TagD> "+new String(msg.getBody()));
					}
				}else if (msg.getTopic().equals("TopicTest2")) {
					System.out.println("TopicTest2>> "+new String(msg.getBody()));
				}
				
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		/****************************************************************
		 ***                      普通消费监听                                                                                 ***
		 ***                        end                              ***
		****************************************************************/
		
		/**
		 * Consumer 对象在使用之前必须要调用start初始化，初始化一次即可<br>
		 */
		
//		consumer.sendMessageBack(msg, delayLevel);
		consumer.start();
		
		System.out.println("Consumer Started.");
	}

}
