
package han.nds.client;

import han.nds.Debug;
import han.nds.StringPool;
import han.nds.TextLine;
import han.nds.Timer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ClientReceiver implements Runnable
{

    private String hostName;

    private Socket clientSocket;
    BufferedReader inFromServer;
    DataOutputStream outToServer;
    
    private int startLineNum = 0;
    private int endLineNum;
    
    private ConcurrentSkipListMap<Integer,String> concurrentSkipListMap;

    public ClientReceiver(String hostName,
    		Socket clientSocket, BufferedReader inFromServer,
    		DataOutputStream outToServer,
    		ConcurrentSkipListMap<Integer,String> concurrentSkipListMap,
    		int startLineNum,int endLineNum){
    	
        this.hostName = hostName;
        this.clientSocket = clientSocket;
        this.inFromServer = inFromServer;
        this.outToServer = outToServer;
        this.concurrentSkipListMap = concurrentSkipListMap;
        this.startLineNum = startLineNum;
        this.endLineNum = endLineNum;
    }

	@Override
	public void run() {
		try {
			Timer timer = new Timer(getClass() + " " + hostName);
			
			Boolean isRequestDataYet = false;
			// save the input data directly into file
//			FileWriter fileWriter = null;
//			BufferedWriter bufferedWriter = null;
//			fileWriter = new FileWriter("wordlist-local-" + hostName + ".txt",
//					false);
//			bufferedWriter = new BufferedWriter(fileWriter);
//
//			while (stringPool.continueProducing || data != null) {
//				bufferedWriter.write(data + '\n');
//				data = stringPool.get();
//			}
//			bufferedWriter.close();
//			fileWriter.close();


			//begin to recieve data
//			FileWriter fileWriter sb = new StringBuilder(); = null;packageString
//			BufferedWriter bufferedWriter = null;
//			fileWriter = new FileWriter("wordlist-result"+hostName+".txt",
//			false);
//			bufferedWriter = new BufferedWriter(fileWriter);
			
			String serverStr;
//			serverStr = inFromServer.readLine();
			serverStr = "#start";
			Debug.show("what i have read first is"+serverStr);
			while( !serverStr.equals("#exit") ){
				
				 if(serverStr.equals("#DHFC") ){//ClientSideDataHandleFinishedConfirmed
					 Debug.show(hostName + ": I am confirming");
					 outToServer.writeBytes("#DataHandleFinishedConfirmed" + '\n');
					 outToServer.writeBytes("#RequestData" +'*'+startLineNum +'*'+endLineNum+'\n');
				 }
				 
				 if(Client.currentLoopNumber!=0 && !isRequestDataYet){
					 Debug.show("I am here");
					 outToServer.writeBytes("#RequestData" +'*'+startLineNum +'*'+endLineNum+'\n');
					 isRequestDataYet = true;
				 }
				 
				 if(serverStr.startsWith("#BeginToSendRequestData")){
					 Debug.show(hostName + ": "+serverStr );

					 serverStr = inFromServer.readLine();
					 while(!serverStr.startsWith("#SRDF") ){
//						 SendRequestDataFinish
							
//						 bufferedWriter.write(serverStr + '\n');
//						 serverStr = inFromServer.readLine();
						 if(serverStr!=null&&!serverStr.equals("")){
//							 D.show("See what I got :"+serverStr);
							 TextLine textLine = new TextLine(serverStr);
							 concurrentSkipListMap.put(textLine.id,textLine.line);
						 }
						 serverStr = inFromServer.readLine();
//						 D.show("See what I got :"+textLine.id );
//						 D.show("See what I got :"+textLine.line);
//						 );
					 }
					 Debug.show(hostName + ": haha send RDF ");
					 outToServer.writeBytes("#RDF" + '\n');
					 outToServer.writeBytes("#RDF" + '\n');
				 }
				 
				 serverStr = inFromServer.readLine();
//				 D.show(hostName + ": Here I have recieve "+serverStr );
			}

			Debug.show(hostName + ": concurrentSkipListMap size is "+concurrentSkipListMap.size());
			Debug.show(hostName + ": Server tell me to exit");
			
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
