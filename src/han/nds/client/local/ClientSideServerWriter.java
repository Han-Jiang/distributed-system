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
package han.nds.client.local;

import han.nds.Timer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

public class ClientSideServerWriter implements Runnable
{

    private String name;
    ConcurrentSkipListMap concurrentSkipListMap;

    public ClientSideServerWriter(ConcurrentSkipListMap<Integer,String> concurrentSkipListMap)
    {
        this.name = "ClientSideWriter";
        this.concurrentSkipListMap =  concurrentSkipListMap;
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
			fileWriter = new FileWriter("D:\\" + name + "-result.txt",false);
//			fileWriter = new FileWriter("/home/students/hxj393/data/" + name + "-result.txt",false);
//			fileWriter = new FileWriter("/data/private/nds/temp/" + name + "-result.txt",false);
			
			bufferedWriter = new BufferedWriter(fileWriter);
			
			System.out.println("Element in concurrentSkipListMap is"+concurrentSkipListMap.size());
			
			
			// returns the NavigableSet in reverse order
			NavigableSet ns = concurrentSkipListMap.keySet();
			System.out.println("Values in reverse order......");
			
			Iterator itr = ns.iterator();
			while (itr.hasNext()) {
				Integer key = (Integer) itr.next();
				String s =  ""+key + concurrentSkipListMap.get(key);
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
