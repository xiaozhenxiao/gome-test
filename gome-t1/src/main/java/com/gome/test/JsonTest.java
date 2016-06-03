package com.gome.test;

import cn.com.gome.rebate.model.CommonDto;

import com.alibaba.fastjson.JSON;

public class JsonTest {
	
	public static void main(String[] args) {
		String jsonString = "{\"id\":\"123\",\"name\":\"zhangsan\"}";
		CommonDto dto = JSON.parseObject(jsonString, CommonDto.class);
		System.out.println(dto.getUserId());
	}

}
