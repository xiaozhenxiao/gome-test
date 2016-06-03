package com.gome.test;

import java.util.concurrent.SynchronousQueue;

public class QueueTest {
	public static void main(String[] args) throws InterruptedException {
		SynchronousQueue<String> queue = new SynchronousQueue<String>();
		System.out.println("1-poll:"+queue.poll());
		System.out.println("1-size:"+queue.size());
		queue.put("1");
		
		System.out.println("2-poll:"+queue.poll());
		System.out.println("2-size:"+queue.size());
//		queue.add("2");
		System.out.println("3-size:"+queue.size());
		
		System.out.println("3-poll"+queue.poll());
	}

}
