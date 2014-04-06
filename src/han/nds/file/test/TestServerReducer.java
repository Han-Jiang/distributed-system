package han.nds.file.test;

import han.nds.Debug;

import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestServerReducer {
	public static void main(String[] args){
		int a = 0;
		int b = 230000;
	
		int numOfFile = 4;
		ExecutorService threadPool = Executors.newFixedThreadPool(4);
    	Future[] futureArray = new Future[numOfFile];
    	ConcurrentSkipListMap<Integer,String> concurrentSkipListMap = 
    		new ConcurrentSkipListMap<Integer, String>();

    	for(int i = 0; i<numOfFile; i++){
			futureArray[i] = threadPool.submit(new ServerReducer(i));
		}

    	//wait for all the file saver to finish
			try {
				for(int i = 0; i<numOfFile; i++){
					futureArray[i].get();
				}
				Debug.show("producer thread finished");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			NavigableSet ns = concurrentSkipListMap.keySet();
			Debug.show("Number of element to be sent is "+concurrentSkipListMap.size());
			
//			Iterator itr = ns.iterator();
//			while (itr.hasNext()) {
//				Integer key = (Integer) itr.next();
//				String str =  ""+key+ " "+ concurrentSkipListMap.get(key)+'\n';
//				outToClient.writeBytes(str);
////				Debug.show("I am write"+str);
//			}
    	
//    	for(TextLine textLine: hashSet){
//		    if(textLine.id>=a&&textLine.id<b){
//		    	outToClient.writeBytes(textLine.toString()+'\n');
////				D.show("Send data " + textLine.toString() );
//		    }
//		 }
    }
	
}
	
