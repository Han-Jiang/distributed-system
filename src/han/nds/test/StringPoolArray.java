package han.nds.test;

import han.nds.StringPool;

public class StringPoolArray {

	
	public static void main(String[] args){
		
		int num = 8;
		
		StringPool[] stringPoolArray = new StringPool[num];

		for(int i = 0; i<num; i++){
			 stringPoolArray[i] = new StringPool();
		}
		
		int[] counterArray = new int[num];
		
		System.out.println(counterArray[0]);

		
	}
}
