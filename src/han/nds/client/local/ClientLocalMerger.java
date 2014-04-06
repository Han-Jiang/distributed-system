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

import han.nds.Debug;
import han.nds.StringPool;
import han.nds.TextLine;
import han.nds.Timer;

import java.net.Socket;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentSkipListMap;

public class ClientLocalMerger implements Runnable
{

    private StringPool stringPool;
    
    private String hostName;
    
    private ConcurrentSkipListMap concurrentSkipListMap;
    
    LinkedHashSet<TextLine> hashSet = new LinkedHashSet<TextLine>();
    
    private Socket clientSocket;

    public ClientLocalMerger( StringPool stringPool, String hostName,ConcurrentSkipListMap concurrentSkipListMap)
    {

        this.stringPool = stringPool;
        this.hostName = hostName;
        this.concurrentSkipListMap = concurrentSkipListMap;
        
        Debug.show(hostName + ": Recieve size of ConcurrentSkipListMap is "+concurrentSkipListMap.size());

//		TextLine textLine = new TextLine("12329 HanJiang");
//		concurrentSkipListMap.put(textLine.id,textLine.line);
//		D.show("after is "+concurrentSkipListMap.size());
    }


	@Override
	public void run() {
		try {
			Timer timer = new Timer(getClass() + " " + hostName);

			 String data = stringPool.get();
			
			 while(data != null ||  stringPool.continueProducing ){
				
				 if(data!=null){
					 TextLine textLine = new TextLine(data);
					 hashSet.add(new TextLine(data));
//					 
				 }
				 data = stringPool.get();
//				 D.show(hostName + ": I get data" + data);
			 }
			 
			 for(TextLine textLine:hashSet){
				 concurrentSkipListMap.put(textLine.id,textLine.line);
	         }

			timer.end();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}
	


}
