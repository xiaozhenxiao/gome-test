package com.gome.test;

import cn.com.gome.rebate.model.merchantsettled.MerchantSettledDto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestJava {
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	private String test = new String("jslkfj");

	public static void main(String[] args) throws IOException {
		TestJava testJava = new TestJava();
		testJava.getTest();
		testJava.setTest("");
		System.out.println(new Date().getTime());

		testArrays();
		// testString();

		// testMap();

		// testBigDecimal();

//		 testJson();

		// testRegex();

		// testMapSort();

		// testClass();

//		testCollections();

//		testType();

	}
	
	public static void testType(){
		TypeClassModal type = new TypeClassModal();
		System.out.println(type.getType());
		System.out.println(type.getId());
		
		System.out.println("===");
	}

	public static void testClass() {
		Class<TestJava> clazz = TestJava.class;
		clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
		System.out.println(TestJava.class.getProtectionDomain().getCodeSource().getLocation().getFile());
	}

	public static void testMapSort() {
		Map<Long, Float> map = new HashMap<>();
		List<Map.Entry<Long, Float>> list = new ArrayList<Map.Entry<Long, Float>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Long, Float>>() {
			//升序排序
			public int compare(Entry<Long, Float> o1,
							   Entry<Long, Float> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}

		});

		for (Map.Entry<Long, Float> mapping : list) {
			System.out.println(mapping.getKey() + ":" + mapping.getValue());
		}
	}

	public static void testRegex() {
		String regex="<a[^>]*>(.*)</a>";//正则表达式字符串。  
        String input="<a href='http://www.baidu.com'>百度</a>";  
        Pattern p = Pattern.compile(regex);  
        Matcher m = p.matcher(input);  
        boolean b1 = m.matches();  
        System.out.println("第一种方式匹配的结果是："+b1);  
           
        boolean b2=Pattern.matches(regex, input);  
        System.out.println("第二种方式匹配的结果是："+b2);
        
      //以下是创建一个正则表达式，使之与“*”匹配  
        System.out.println("字面值模式:"+Pattern.quote("<[^>]>"));
        
        System.out.println("编译过此模式的正则表达式："+p.pattern());  
        System.out.println("模式的字符串表示形式        ："+p.toString());
        
        String regexa="a+";  
        String inputa="BBaCCCaaDDDaaaEEEE";  
          
        Pattern pa = Pattern.compile(regexa);  
        String str1[]=pa.split(inputa);  
        System.out.println("第一次输入的字符串被分割成数组的长度为："+str1.length);  
        for (int i = 0; i < str1.length; i++) {  
            System.out.println(str1[i]);  
        }
	}

	public static void testString() {
		String str1 = "helloworld";
		String str2 = new String("hello")+"world";
		String str3 = "hello"+"world";
		final String str11 = "hello";
		String str4 = str11 + "world";
		
		System.out.println(str1==str2);
		System.out.println(str1==str3);
		System.out.println(str1==str4);
		System.out.println("===========================");
		
		int a = 55;
		int b = 128;
		System.out.println(a==b);
		
		Integer aa = 55;
		Integer bb = 55;
		System.out.println(aa==bb);
		System.out.println(a==aa);
		
		Integer aaa = 128;
		Integer bbb = 128;
		System.out.println(b==bbb);
		System.out.println(aaa==bbb);
	}

	public static void testBigDecimal() {
		BigDecimal decimal = new BigDecimal(12.5643);
        System.out.println("舍入： "+decimal.setScale(3, BigDecimal.ROUND_HALF_UP));
        System.out.println("舍入： "+decimal.scale());
	}

	public static void testMap() {
		Map<String, String> map = new HashMap<>();
        map.put("sku_"+1, "{\"commonUserRatio\":0,\"gomeUserRatio\":0,\"itemId\":12391,\"lowerLineRatio\":0,\"merchantUserRatio\":0,\"pfShareRatio\":0,\"popDistributionRatio\":0,\"popShareRatio\":0.1,\"skuId\":12655}");
        
        int hashCode = map.hashCode();
        map.put("sku_"+1, "{\"commonUserRatio\":0,\"gomeUserRatio\":0,\"itemId\":12391,\"lowerLineRatio\":0,\"merchantUserRatio\":0,\"pfShareRatio\":0,\"popDistributionRatio\":0,\"popShareRatio\":0.02,\"skuId\":12655}");
        int newCode = map.hashCode();
        
        System.out.println("hashCode: "+hashCode + "  >> " + newCode);
        System.out.println(map.toString());
	}

	public static void testJson() {
		/*MerchantSettledDto merchantSettled = new MerchantSettledDto(
				"11111", "123456", "商家名称自测", new Date(), 
				"账户昵称自测", "22222");*/
		
		JSONArray jsonArray = JSON.parseArray("[]");
		System.out.println("jsonArray.size:" + jsonArray.size());
		for (Object object : jsonArray) {
			System.out.println(JSON.toJSONString(object));
		}
        
        String jsonMerchant = "{\"accountId\":\"22222\",\"date\":1441955718492,\"merchantId\":\"11111\",\"merchantName\":\"商家名称自测\",\"recommendCode\":\"123456\"}";
        MerchantSettledDto dto = JSON.parseObject(jsonMerchant, MerchantSettledDto.class);
        System.out.println("accountNick:"+dto.getAccountNick());
        System.out.println(JSON.toJSONString(dto));
        
        JSONObject jsonObject = JSON.parseObject("{\"commonuser\":1,\"gomeuser\":2}");
        System.out.println("size:>-->"+jsonObject.size());
        
        System.out.println("data-->>:"+jsonObject.getIntValue("merchantuser"));
        
        JSONObject recommond = new JSONObject();
        recommond.put("sn", "1");
        recommond.put("pictureUrl", "http://www.baidu.tupian.xxx");
        recommond.put("extendData", "{key:value,key:value}");
        recommond.put("recommendType", "1");
        
        JSONObject recommond1 = new JSONObject();
        recommond1.put("sn", "1");
        recommond1.put("pictureUrl", "http://www.baidu.tupian.xxx");
        recommond1.put("extendData", "{key:value,key:value}");
        recommond1.put("recommendType", "1");
        
        List<JSONObject> list = new ArrayList<JSONObject>();
        list.add(recommond);
        list.add(recommond1);
        
        System.out.println(JSON.toJSONString(list));
	}
	
	public static void testCollections(){
		List<String> list = Lists.newArrayList();
		list.add("mytest0000000032");
		list.add("mytest0000000012");
		list.add("mytest0000000022");
		list.add("mytest0000000085");
		list.add("mytest0000000064");
		list.add("mytest0000000091");
		list.add("mytest0000000075");
		list.add("mytest0000000043");
		list.add("mytest0000000050");
		list.add("mytest0000000070");
		
		System.out.println("list: "+list);
		
		Collections.sort(list);
		
		System.out.println("sort list: "+list);
		
		System.out.println("postion: "+ list.get(Collections.binarySearch(list, "mytest0000000070")-1));
	}
	
	public static void testArrays(){
		String [] array = new String[]{"1","2","3","4","5"};
		String []original = null;
		original = Arrays.copyOfRange(array, 4, array.length);
		for (String string : original) {
			System.out.println(string);
		}
	}
	
}
