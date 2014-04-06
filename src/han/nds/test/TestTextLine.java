package han.nds.test;

import han.nds.TextLine;
import han.nds.Timer;

public class TestTextLine {
	public static void main(String[] args){
//		5 bOnMrukcYr
//		8 TeIiC
//		10 NTs
//		13 cij
//		15 samgHbSeI
//		18 WFgLSBecO
//		20 mgXBlzuiU
		String dataString = "5 bOnMrukcYr";
		TextLine textLine = new TextLine(dataString);
		
		System.out.println("#"+textLine.id+"#");
		System.out.println("#"+textLine.line+"#");
		
		
	}
	
	public TestTextLine() {
		Timer timer = new Timer(getClass()+"");
		timer.start();
		timer.end();
	}
}
