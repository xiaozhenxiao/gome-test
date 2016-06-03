package com.gome.sort.summary;

public class SortMain {
	public static void main(String[] args) {
		int array[] = { 49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4,
				62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51 };
		
		GuiBing guiBing = new GuiBing();
		guiBing.MergeSort(array);
		
//		MaoPao maoPao = new MaoPao();
//		maoPao.bubbleSort(array);
		print(array);
	}

	public static void print(int... array) {
		for (int i : array) {
			System.out.print(i + ",");
		}
		System.out.println();
	}

}
