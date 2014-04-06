package han.nds.test;

import han.nds.TextLine;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

public class TestTextLineConcurrentSkipListMap {
	public static void main(String[] args){
	
		ConcurrentSkipListMap<Integer, String>  concurrentSkipListMap = new ConcurrentSkipListMap<Integer, String>();

//		String s1 = "5 bOnMrukcYr";
//		String s8 = "4 bOnMrukcYr";
//		String s9 = "14 bOnMrukcYr";
//		allSet.add(new TextLine(s1));
//		allSet.add(new TextLine(s8));
//		allSet.add(new TextLine(s9));
		
//		String s2 = "8 TeIiC";
//		String s3 = "10 NTs";ashSet.get
//		String s4 = "13 cij";
//		String s5 = "15 samgHbSeI";
//		String s6 = "18 WFgLSBecO";
//		String s7 = "20 mgXBlzuiU";
//		allSet.add(new TextLine(s2));
//		allSet.add(new TextLine(s3));
//		allSet.add(new TextLine(s4));
//		allSet.add(new TextLine(s5));
//		allSet.add(new TextLine(s6));
//		allSet.add(new TextLine(s7));    
//		
//		allSet.add(new TextLine("19 mgXBlzuiU"));        
//		allSet.add(new TextLine("220 mgXBlzuiU")); 
//		
//		allSet.add(new TextLine("2220 mgXBlzuiU")); 
//		
//		allSet.add(new TextLine("202 mgXBlzuiU")); 
//		allSet.add(new TextLine("9 mgXBlzuiU")); 
		TextLine textLine = new TextLine("19 mgXBlzuiU");
		concurrentSkipListMap.put(textLine.id,textLine.line);
		textLine = new TextLine("29 mgXBlzuiU");
		concurrentSkipListMap.put(textLine.id,textLine.line);
		textLine = new TextLine("9 mgXBlzuiU");
		concurrentSkipListMap.put(textLine.id,textLine.line);
		textLine = new TextLine("9 mgXBlzuiU");
		concurrentSkipListMap.put(textLine.id,textLine.line);
		textLine = new TextLine("11 mgXBlzuiU");
		concurrentSkipListMap.put(textLine.id,textLine.line);
		
		
		NavigableSet ns = concurrentSkipListMap.keySet();
		System.out.println("Values in reverse order......");
		
		Iterator itr = ns.iterator();
		while (itr.hasNext()) {
//			String s = (String) itr.next();
			System.out.println(itr.next());
		}
		
	//    System.out.println("headSet元素:" + allSet.headSet("C"));  
	//    System.out.println("tailSet元素:" + allSet.tailSet("C"));  
	//    System.out.println("subSet元素:" + allSet.subSet("B", "D"));  

//		for (Map.Entry<String, Integer> entry : map.entrySet()) {
//	        System.out.println(entry);
//	    }
	}
	
}
