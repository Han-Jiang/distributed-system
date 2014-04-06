package han.nds.test;

public class TestBytes {
	
	public static void main(String[] args){
		
			  byte[] buffer = new byte[65536]; 
			  
			  String s = "hello1\nhello2\nhello3\n";
			  
			  s= s+"hello4\nhello5\n";
			  
			  System.out.println(s.length());
			  System.out.println(s);
			  
			  buffer = s.getBytes();
			  

			  String s2 = new String(buffer, 0, buffer.length);

			  System.out.println(s2);
		
	}

}
