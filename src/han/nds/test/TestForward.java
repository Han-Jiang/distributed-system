package han.nds.test;

public class TestForward {
	
	public static void main(String[] args){
		char a = 'A';
		System.out.println(forward('A'));
		
		System.out.println(forward('B'));
		
		System.out.println(forward('C'));
		
	}
	
	
	public static int forward(char c){
//		96
		return c%4;
	}

}
