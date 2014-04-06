
package han.nds;

public class Timer{
	private String name;
	
	// timer
	private long startTime = 0;
	private long currentTime = 0;
	private long elapsedTime = 0;
	
	public Timer(String name) {
		this.name = name;
		startTime = System.currentTimeMillis();
		Debug.show(this.name  + " begin its job;");
	}
	
	public void start(){
		startTime = System.currentTimeMillis();
	}

	public void end(){
		currentTime = System.currentTimeMillis();
		elapsedTime = currentTime - startTime;
		
		Debug.show(this.name  + " finished its job.");
//		System.out.println("Start   time is : " + startTime);
//		System.out.println("Current time is : " + currentTime);
		Debug.show("Elapsed time is : " + elapsedTime);
		Debug.show("");
	}
}
