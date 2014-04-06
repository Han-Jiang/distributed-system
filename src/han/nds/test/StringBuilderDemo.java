package han.nds.test;

public class StringBuilderDemo {
	
	public static void main(String[] args){
		
		// creates empty builder, capacity 16
		 StringBuilder sb = new StringBuilder();
		 // adds 9 character string at beginning
		 sb.append("Greetings");
		 
		System.out.println(sb);
		System.out.println(sb.length());
		System.out.println(sb.capacity());
		
		String hi ="hello\n";
		sb.append(hi);
		System.out.println(sb);
		System.out.println(sb.length());
		System.out.println(sb.capacity());
		
		sb.append(hi);
		System.out.println(sb);
		System.out.println(sb.length());
		System.out.println(sb.capacity());
		
		String b=sb.toString();
		System.out.println(b);
		System.out.println(b.length());

		
	}

}
