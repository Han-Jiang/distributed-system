
package han.nds.server;



import han.nds.Debug;
import han.nds.TextLine;
import han.nds.Timer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Server condumer, handle data
 */
public class ServerSender implements Runnable
{
    private String name;
    
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    
    LinkedHashSet<TextLine> hashSet = new LinkedHashSet<TextLine>();
    
    boolean isDataHandleFinishedConfirmed = false;

    
    public ServerSender(BufferedReader inFromClient,DataOutputStream outToClient, String name)
    {
        this.name = name;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }

    @Override
    public void run()
    {
		try {
			Timer timer = new Timer(getClass()+"");

			//start to work
			String clientSentence = inFromClient.readLine();
			
			while (!clientSentence.equals("#exitAll")) {
//			while (!clientSentence.equals("#RDF")) {
				//#RDF for Request Data Finished

				if(clientSentence.startsWith("#")){
//					D.show("command is " + clientSentence);
					
					if(clientSentence.startsWith("#RequestData")){
						Debug.show("get request data command "+clientSentence);
						sendRequestData(clientSentence);
						Debug.show("finished one round");
					}
					
					if(clientSentence.equals("#DataHandleFinishedConfirmed")){
						Debug.show("Here I am confirmed!");
						isDataHandleFinishedConfirmed = true;
					}
				}
				
				if(!isDataHandleFinishedConfirmed){
					Debug.show("DataHandleFinishedNotConfirmed ");
					outToClient.writeBytes("#DHFC"+'\n');
				}
							
//				 if(clientSentence.startsWith("*")){
//					 clientSentence = clientSentence.substring(1);
//				 }
					
				clientSentence = inFromClient.readLine();
				Debug.show("Here I have got " + clientSentence);
					
//					if(isConsumerWorkerFinished){
//						outToClient.writeBytes("#ServerFinished"+"\n");
//						isConsumerWorkerFinished = false;
//					}

			}
			
			Debug.show("Haha. I finished my work!");
			timer.end();
				
		} catch (IOException e) {

			e.printStackTrace();
		}
        
    }
    
    public void sendRequestData(String requestDataCommand) throws IOException{
		
    	int a  = Integer.parseInt(
    			requestDataCommand.substring(
    					requestDataCommand.indexOf('*')+1,
    					requestDataCommand.lastIndexOf('*')
				)
			);
		int b  = Integer.parseInt(
				requestDataCommand.substring(
						requestDataCommand.lastIndexOf('*')+1)
			);
		Debug.show("a and b is " + a +" "+b);
		
    	
		outToClient.writeBytes("#BeginToSendRequestData"+"\n");
		Debug.show("Send command " + "#BeginToSendRequestData");
		
		
		sendDataToClientByReadFile(a, b);
		
		
		outToClient.writeBytes("#SRDF"+'*'+a +'*'+b+'\n');
//		D.show("Send command " + "#SendRequestDataFinish");
		Debug.show("Send command " + "#SRDF"+'*'+a +'*'+b+'\n');
//		sendResultToTheClient(outToClient);s
		
		outToClient.writeBytes("#exit"+"\n");
    }

    public void sendDataToClientByReadFile(int a, int b) throws IOException{
    	
    	ExecutorService threadPool = Executors.newFixedThreadPool(WorkerThread.numOfFile);
    	Future[] futureArray = new Future[WorkerThread.numOfFile];
    	ConcurrentSkipListMap<Integer,String> concurrentSkipListMap = 
    		new ConcurrentSkipListMap<Integer, String>();
    	
    	
    	for(int i = 0; i<WorkerThread.numOfFile; i++){
			futureArray[i] = threadPool.submit(new ServerFileReader(i,a,b,concurrentSkipListMap));
		}

    	//wait for all the file saver to finish
		
			try {
				for(int i = 0; i<WorkerThread.numOfFile; i++){
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
			
			Iterator itr = ns.iterator();
			while (itr.hasNext()) {
				Integer key = (Integer) itr.next();
				String str =  ""+key+ " "+ concurrentSkipListMap.get(key)+'\n';
				outToClient.writeBytes(str);
//				Debug.show("I am write"+str);
			}
    	
//    	for(TextLine textLine: hashSet){
//		    if(textLine.id>=a&&textLine.id<b){
//		    	outToClient.writeBytes(textLine.toString()+'\n');
////				D.show("Send data " + textLine.toString() );
//		    }
//		 }
    }
}
