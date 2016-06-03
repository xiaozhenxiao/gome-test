package com.gome.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ClassLoaderTest {
	public static void main(String[] args) throws IOException {
		String fileName = null;
		String protocol = null;
		String path = null;
		ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
		URL url = classLoader.getResource("");
		
		File directory = new File("1");
		System.out.println("1:"+directory.getCanonicalPath()); //得到的是C:/test/abc 
		System.out.println("2:"+directory.getAbsolutePath());    //得到的是C:/test/abc 
		System.out.println("3:"+directory.getPath());          //得到的是abc 

		
		System.out.println("当前路径："+System.getProperty("user.dir"));
		System.out.println("url: " + url);
		if(url != null){
			fileName = url.getFile();
			protocol = url.getProtocol();
			path = url.getPath();
		}
		
		String filePath = fileName + "word.txt";
		System.out.println("filePath:" + filePath);
		File file = new File(filePath);
		if(file.exists()){
			System.out.println("file exists!");
		}
		
		InputStream in = classLoader.getResourceAsStream("word.txt");
		if(in == null){
			in = classLoader.getResourceAsStream("/word.txt");
			System.out.println("input:" + in);
		}
		
		ClassLoader classLoader2 = Thread.currentThread().getContextClassLoader();
		if(classLoader.equals(classLoader2)){
			System.out.println("equals");
		}else {
			System.out.println("no equals");
		}
		
		System.out.println("fileName: " + fileName);
		System.out.println("protocol: " + protocol);
		System.out.println("path: " + path);
		
		getJarPath(ClassLoaderTest.class);
	}
	/**
	 *  根据class获得对应的jar包路径
	 * @param clazz
	 * @return
	 */
	public static String getJarPath(Class<?> clazz){
		return clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
	}

}
