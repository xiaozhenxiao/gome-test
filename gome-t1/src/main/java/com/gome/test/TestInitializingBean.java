package com.gome.test;

import org.springframework.beans.factory.InitializingBean;

public class TestInitializingBean implements InitializingBean{

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ceshi InitializingBean");        
    }
    public void testInit(){
        System.out.println("ceshi init-method");        
    }
    public static void testStatic(){
    	System.out.println("ceshi static-method");        
    }
}