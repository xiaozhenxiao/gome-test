package com.gome.test;

public class TestClass<T> {
	void dis(T []arr){
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
	public static void testClass(ParentClass parent){
		ChildClass childClass = null;
		SonClass sonClass = null;
		if(parent instanceof ChildClass){
			childClass = (ChildClass)parent;
		}
		if(parent instanceof SonClass){
			sonClass = (SonClass)parent;
		}
		if(childClass==null){
			System.out.println("childClass is null");
		}else {
			System.out.println("childName:"+childClass.getChildName());
		}
		if(sonClass==null){
			System.out.println("sonClass is null");
		}else {
			System.out.println("sonName:"+sonClass.getSonName());
		}
		System.out.println("=======================================================");
	}
	
	public static void main(String[] args) {
		TestClass<Integer> jt = new TestClass<Integer>();
		Integer arr[] = {1,2,3};
		jt.dis(arr);
		
		ChildClass child = new ChildClass();
		child.setChildName("childName");
		SonClass son = new SonClass();
		son.setSonName("sonName");
		
		testClass(son);
		testClass(child);
		
	}
}
