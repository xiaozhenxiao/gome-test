package com.gome.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import redis.clients.jedis.JedisCluster;

public class SpringMain {
	public static void main(String[] args) {
		ApplicationContext content = new ClassPathXmlApplicationContext(
				"springContext.xml");
		JedisCluster jedisCluster = content.getBean("jedisCluster", JedisCluster.class);
		for (int i = 0; i < 50; i++) {
			long t1 = System.currentTimeMillis();
			jedisCluster.hset("Aash", "" + i, "hash_" + i);
			long t2 = System.currentTimeMillis();
			String value = jedisCluster.hget("Aash", "" + i);
			long t3 = System.currentTimeMillis();
			
			System.out.println("" + value);
			System.out.println((t2 - t1) + "mills");
			System.out.println((t3 - t2) + "mills");
	
		}
		
		AbstractUrlHandlerMapping handler;
	}
	
}
