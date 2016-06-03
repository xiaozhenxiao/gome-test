package com.gome.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioTest {
	public static void main(String[] args) {
		
	}
	
	public static void testNio() throws IOException{
		FileInputStream fis = new FileInputStream("D:/Readme.txt");
		FileOutputStream fos = new FileOutputStream("D:/ReadmeBack.txt");
		
		FileChannel readChannel = fis.getChannel();
		FileChannel writeChannel = fos.getChannel();
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			buffer.clear();
			int len = readChannel.read(buffer);
			if(len == -1){
				break;
			}
			buffer.flip();
			writeChannel.write(buffer);
		}
		readChannel.close();
		writeChannel.close();
		fis.close();
		fos.close();
	}
	
	public static void testNioBuffer(){
		ByteBuffer buffer = ByteBuffer.allocate(15); //15个字节大小的缓冲区
		System.out.println("limit="+buffer.limit()+" capacity="+buffer.capacity()+" position="+buffer.position()+" mark="+buffer.mark());
		for (int i = 0; i < 10; i++) {
			buffer.put((byte)i);
		}
		System.out.println("limit="+buffer.limit()+" capacity="+buffer.capacity()+" position="+buffer.position()+" mark="+buffer.mark());
		buffer.flip();
		System.out.println("limit="+buffer.limit()+" capacity="+buffer.capacity()+" position="+buffer.position()+" mark="+buffer.mark());
		
		for (int i = 0; i < 5; i++) {
			System.out.print(buffer.get());
		}
		System.out.println();
		System.out.println("limit="+buffer.limit()+" capacity="+buffer.capacity()+" position="+buffer.position()+" mark="+buffer.mark());
//		buffer.flip();
//		buffer.rewind();
//		buffer.compact();
		buffer.clear();
		System.out.println("limit="+buffer.limit()+" capacity="+buffer.capacity()+" position="+buffer.position()+" mark="+buffer.mark());
		for (int i = 0; i < 15; i++) {
			System.out.print(buffer.get());
		}
		//mark()  reset()
		while(buffer.hasRemaining())
			System.out.println(buffer.get());
	}
	
	public void testSocketChannel() throws IOException{
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ServerSocket serverSocket = ssc.socket();
		serverSocket.bind(new InetSocketAddress(1234));
	}
	
	public static void selectSockets() throws IOException{
		Integer port = 1234;
		System.out.println("Listening on port " + port);
		
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		ServerSocket serverSocket = serverChannel.socket();
		Selector selector = Selector.open();
		
		serverSocket.bind(new InetSocketAddress(port));		
		serverChannel.configureBlocking(false);		
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while (true) {
			int n = selector.select();
			
			if(n == 0){
				continue;
			}
			
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();
				if(key.isAcceptable()){
					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel channel = server.accept();
					
					registerChannel(selector, channel, SelectionKey.OP_READ);
					sayHello(channel);					
				}
				if(key.isReadable()){
					readDataFromSocket(key);
				}
				
				it.remove();
			}
			
		}
	}
	
	private static ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

	private static void readDataFromSocket(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		int count;
		
		buffer.clear();
		
		while ((count = socketChannel.read(buffer)) > 0) {
			buffer.flip();
			while (buffer.hasRemaining()) {
				socketChannel.write(buffer);
			}
			buffer.clear();
		}
		if(count < 0){
			socketChannel.close();
		}
		
	}

	private static void sayHello(SocketChannel channel) throws IOException {
		buffer.clear();
		buffer.put("Hi there!\r\n".getBytes());
		buffer.flip();
		channel.write(buffer);
	}

	private static void registerChannel(Selector selector,
			SocketChannel channel, int ops) throws IOException {
		if(channel == null){
			return;
		}
		channel.configureBlocking(false);
		channel.register(selector, ops);
		
	}

}
