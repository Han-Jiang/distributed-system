/*******************************************************************************
 * Copyright 2011 @ Kapil Viren Ahuja
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package han.nds.client.local;

import han.nds.Debug;
import han.nds.StringPool;
import han.nds.Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientProducer implements Runnable {
	private StringPool[] stringPoolArray;
	private int numOfMachine;
	
	private String[][] stringPackageArray= new String[numOfMachine][20];
	private int[] counterArray = new int[numOfMachine];

	// file size
	int fileSize = 100000;
	// counter for lines
	int counter = 0;
	int fileIndex = 0;

	public ClientProducer(StringPool[] stringPoolArray, int numOfMachine) {
		this.stringPoolArray = new StringPool[numOfMachine];
		
		this.stringPoolArray = stringPoolArray;
		this.numOfMachine = numOfMachine;
	}

	@Override
	public void run() {

		try {
			Timer timer = new Timer(getClass()+" ");
			timer.start();
			
			//read data form system.in
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(System.in));
			
			String strText;
			while ((strText = bufferedReader.readLine()) != null) {

//				if (counter == 0) {
//					fileIndex++;
//				}
//				System.out.println("Producer produced: " + counter + " " + strText);
//				Thread.sleep(100);
				
//				if(strText.charAt(0)>96){
//					D.show("put to stringPool "+strText);
//				System.out.println(forward(strText.charAt(0)));
				stringPoolArray[forward(strText.charAt(0))].put(counter + " " + strText);
			
//				D.show(counter+" " + strText);
				// counter is from 0 , so when counter equals fileSize
				// there is #fileSize line saved
				counter++;
//				if (counter == fileSize) {
//					counter = 0;
//				}
			}
			Debug.show("stop producing");
			for(int i = 0; i < numOfMachine ;i++){
				this.stringPoolArray[i].continueProducing = Boolean.FALSE;
			}
			System.out.println("Counter is " +counter);

			timer.end();

		} catch (InterruptedException ex) {

			ex.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	public void stringPackageHandeler(String strText){
		int index = forward(strText.charAt(0));
		if(counterArray[index]!= 10){
			stringPackageArray[index][counterArray[index]]= strText;
			counterArray[index]++;
		}else{
			counterArray[index] = 0;
			
			for(int i = 0; i< 10; i++){
				
			}
//			stringPoolArray[index].put(counter + " " + strText);
		}
		
	}
	
	
	public int forward(char c){
		return c%numOfMachine;
	}
}
