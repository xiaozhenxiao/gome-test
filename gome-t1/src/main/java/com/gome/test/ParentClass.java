package com.gome.test;

public class ParentClass {
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void callMethod(){
		System.out.println("parent call method start================");
		called();
		System.out.println("parent call method end =================");
	}
	
	public void called(){
		System.out.println("parent called method---------------");
	}

}
