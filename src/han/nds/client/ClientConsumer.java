/*******************************************************************************
 * Copyright 2011 @ Kapil Viren Ahuja
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
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

public class ClientConsumer implements Runnable
{

    private StringPool stringPool;
    
    private String hostName;
    
    private ConcurrentSkipListMap concurrentSkipListMap;
    
    private Socket clientSocket;
    BufferedReader inFromServer;
    DataOutputStream outToServer;

    public ClientConsumer(StringPool stringPool, String hostName,
    		Socket clientSocket,BufferedReader inFromServer,
    		DataOutputStream outToServer){

        this.stringPool = stringPool;
        this.hostName = hostName;
        this.clientSocket = clientSocket;
        this.inFromServer = inFromServer;
        this.outToServer = outToServer;
    }


	@Override
	public void run() {
		try {
			Timer timer = new Timer(getClass() + " " + hostName);

			 String data = stringPool.get();
			 
			 outToServer.writeBytes(hostName+"\n");
			 
//tradition way
			 while(data != null ||  stringPool.continueProducing ){
				
				 if(data!=null){
					 outToServer.writeBytes(data+ '\n');
				 }
				 data = stringPool.get();
			 }

			 
//			 // creates empty builder, capacity 800
//			 StringBuilder sb = new StringBuilder(800);
//			
//			 while(data != null ||  stringPool.continueProducing ){
//				
//				 if(data!=null){
//					 if(sb.length()>500){
////						 D.show("I am bigger than");
//						 outToServer.writeBytes(sb.toString());
//						 sb = new StringBuilder(800);
//					 }else{
////						 D.show("I am smaller than");
//						 sb.append(data+'\n');
//						 //	sb.append('\n');
//					 } 
//				 }
//				 data = stringPool.get();
////				 D.show(hostName + ": I get data" + data);
//			 }
//			 if(sb.length()>0){
//				 outToServer.writeBytes(sb.toString());
//				 sb = new StringBuilder();
//			 }
			 
			 outToServer.writeBytes("#ODF"+ '\n');
			 outToServer.writeBytes("#ODF"+ '\n');
			 Debug.show(hostName + ": I already send the ODF");
			 //#ODF ~OriginalDataFinish

			timer.end();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
