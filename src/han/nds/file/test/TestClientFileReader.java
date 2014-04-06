package han.nds.file.test;


import han.nds.Debug;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestClientFileReader {
	public static void main(String[] args){

		ExecutorService threadPool = Executors.newFixedThreadPool(4);

    	//out put the result
		Future resultStatus = threadPool.submit(new ClientFileReader(9));
		try {
			resultStatus.get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Debug.show("all down");

		threadPool.shutdown();
  
    }
	
}
	
