package com.gome.rocketmq.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;

public class PullConsumer {
	private static final Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();

	public static void main(String[] args) throws MQClientException {
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(
				"PullConsumerGroup");
		consumer.setNamesrvAddr("10.128.31.24:9876;10.128.31.25:9876");
		consumer.start();

		// 拉取订阅主题的队列，默认队列大小是4
		Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("topic");
		for (MessageQueue mq : mqs) {
			System.out.println("Consume from the queue: " + mq);

			SINGLE_MQ: while (true) {
				try {
					PullResult pullResult = consumer.pullBlockIfNotFound(mq,
							null, getMessageQueueOffset(mq), 32);
					List<MessageExt> messages = pullResult.getMsgFoundList();
					if (messages != null && messages.size() < 100) {
						for (MessageExt msg : messages) {
							System.out.println(new String(msg.getBody()));
						}
					}
					System.out.println(pullResult.getNextBeginOffset());
					putMessageQueueOffset(mq, pullResult.getNextBeginOffset());

					switch (pullResult.getPullStatus()) {
					case FOUND:
						// TODO
						break;
					case NO_MATCHED_MSG:
						// TODO
						break;
					case NO_NEW_MSG:
						// TODO
						break SINGLE_MQ;
					case OFFSET_ILLEGAL:
						// TODO
						break;
					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		consumer.shutdown();
	}

	private static long getMessageQueueOffset(MessageQueue mq) {
		Long offset = offsetTable.get(mq);
		if (offset != null) {
			System.out.println(offset);
			return offset;
		}
		return 0;
	}

	private static void putMessageQueueOffset(MessageQueue mq, long offset) {
		offsetTable.put(mq, offset);
	}

}
