package com.gome.test;

public class ChildClass extends ParentClass {
	
	private String childName;

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	@Override
	public void called() {
		System.out.println("child called=================");
	}
	
	public static void main(String[] args) {
		ChildClass child = new ChildClass();
		child.callMethod();
	}
}
