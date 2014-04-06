
package han.nds.test;

import han.nds.Timer;

public class TestTimer{
	public static void main(String[] args){
		TestTimer testTimer = new TestTimer();
		
	}
	
	public TestTimer() {
		Timer timer = new Timer(getClass()+"");
		timer.start();
		timer.end();
	}
}
