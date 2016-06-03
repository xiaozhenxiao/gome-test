package com.gome.sort.summary;

/**
 * O(n^2）
 * @author wangzhen-ds5
 *
 */
public class MaoPao {
	
	public void bubbleSort(int... array) {
		
		int temp = 0;
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j] > array[j + 1]) {
					temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
	}

}
