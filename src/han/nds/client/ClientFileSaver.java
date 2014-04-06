
package han.nds.client;

import han.nds.Debug;
import han.nds.Timer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

public class ClientFileSaver implements Runnable
{

    private String name;
    ConcurrentSkipListMap concurrentSkipListMap;
    private int loopNumber;

    public ClientFileSaver(ConcurrentSkipListMap<Integer,String> concurrentSkipListMap,
    		int loopNumber)
    {
        this.name = "ClientSideWriter";
        this.concurrentSkipListMap =  concurrentSkipListMap;
        this.loopNumber = loopNumber;
    }


	@Override
	public void run() {
		try {
			Timer timer = new Timer(getClass() + " " + name);
			
			// save the input data directly into file
			FileWriter fileWriter = null;
			BufferedWriter bufferedWriter = null;
//			fileWriter = new FileWriter("wordlist-local-" + hostName + ".txt",
//					false);
//			fileWriter = new FileWriter("/home/students/hxj393/data/" + name + "-result-" +loopNumber +".txt",false);

			//			fileWriter = new FileWriter(Client.FOLDER_PATH + Client.FILE_NAME +loopNumber +".txt",false);
			
			fileWriter = new FileWriter(Client.FOLDER_PATH + Client.FILE_NAME + ".txt",true);
			
			bufferedWriter = new BufferedWriter(fileWriter);
			
			Debug.show("Element in concurrentSkipListMap is"+concurrentSkipListMap.size());
			
			
			// returns the NavigableSet in reverse order
			NavigableSet ns = concurrentSkipListMap.keySet();
			Debug.show("Values in reverse order......");
			
			Iterator itr = ns.iterator();
			while (itr.hasNext()) {
				Integer key = (Integer) itr.next();
//				String s =  ""+key+ " "+ concurrentSkipListMap.get(key);
				String s =  (String) concurrentSkipListMap.get(key);
				bufferedWriter.write( s + '\n');
			}
			
//			Collection<String> values = concurrentSkipListMap.values(); 
//			for(String str : values){
//				bufferedWriter.write(str + '\n');
//			}
//			
//			for (Map.Entry<Integer,String> entry : concurrentSkipListMap.entrySet()) {
//				bufferedWriter.write(entry.getKey() +entry.getValue()+ '\n');
//			}
//			
//			for(int i = 0 ; i < values.length; i++)
//	    	{
//				
//	    	}

//			for()
//			while (stringPool.continueProducing || data != null) {
//				bufferedWriter.write(data + '\n');
//				data = stringPool.get();
//			}
			bufferedWriter.close();
			fileWriter.close();

//			 serverStr = inFromServer.readLine();
//			 while( !serverStr.equals("#SendResultFinished") ){
//				
//				 if(serverStr.equals("#ServerFinished")){
//					 outToServer.writeBytes("#RequestData" + '\n');
//				 }
//				 
//				 if(serverStr!=null){
//					 bufferedWriter.write(serverStr + '\n');
//				 }
//			 }
//
//				bufferedWriter.close();
//				fileWriter.close();
			 

			timer.end();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
