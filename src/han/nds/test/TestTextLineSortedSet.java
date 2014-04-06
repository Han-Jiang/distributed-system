package han.nds.test;

import han.nds.Debug;
import han.nds.TextLine;

import java.util.SortedSet;
import java.util.TreeSet;

public class TestTextLineSortedSet {
	public static void main(String[] args){
	

		

		SortedSet<TextLine> allSet = new TreeSet<TextLine>(new SortById());
//		String s1 = "5 bOnMrukcYr";
//		String s8 = "4 bOnMrukcYr";
//		String s9 = "14 bOnMrukcYr";
//		allSet.add(new TextLine(s1));
//		allSet.add(new TextLine(s8));
//		allSet.add(new TextLine(s9));
		
		String s2 = "8 TeIiC";
		String s3 = "10 NTs";
		String s4 = "13 cij";
		String s5 = "15 samgHbSeI";
		String s6 = "18 WFgLSBecO";
		String s7 = "20 mgXBlzuiU";
		allSet.add(new TextLine(s2));
		allSet.add(new TextLine(s3));
		allSet.add(new TextLine(s4));
		allSet.add(new TextLine(s5));
		allSet.add(new TextLine(s6));
		allSet.add(new TextLine(s7));    
		
		allSet.add(new TextLine("19 mgXBlzuiU"));        
		allSet.add(new TextLine("220 mgXBlzuiU")); 
		
		allSet.add(new TextLine("2220 mgXBlzuiU")); 
		
		allSet.add(new TextLine("202 mgXBlzuiU")); 
		allSet.add(new TextLine("9 mgXBlzuiU")); 
		


	
	    System.out.println("第一个元素:" + allSet.first());  
	    System.out.println("最后一个元素:" + allSet.last());  
	//    System.out.println("headSet元素:" + allSet.headSet("C"));  
	//    System.out.println("tailSet元素:" + allSet.tailSet("C"));  
	//    System.out.println("subSet元素:" + allSet.subSet("B", "D"));  
	    
	    for(TextLine textLine: allSet){
	    	Debug.show(textLine);
	    }
	
	}
	
}
