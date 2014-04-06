
package han.nds.server;

import han.nds.Debug;
import han.nds.TextLine;
import han.nds.TextLinePool;
import han.nds.Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Server producer recieve info form the client 
 */
public class ServerProducer implements Runnable {

	private TextLinePool[] textLinePoolArray;
	private ExecutorService executorService;
	BufferedReader inFromClient;
	int numOfFile = 1;
	
	
	String clientSentence;
	
	// file size
	int fileSize = 100000;
	// counter for lines
	
	int fileIndex = 0;

	public ServerProducer(BufferedReader inFromClient, TextLinePool[] textLinePoolArray,
			int numOfFile) {
		this.inFromClient = inFromClient;
		this.textLinePoolArray= textLinePoolArray;
		this.numOfFile = numOfFile;

	}

	@Override
	public void run() {

		try {
			Timer timer = new Timer(getClass()+"");

			//get the job information
			String hostName = inFromClient.readLine();
			WorkerThread.ORIGIN_FILE_PATH = WorkerThread.FOLDER_PATH + hostName+ WorkerThread.ORIGIN_FILE_PATH;
			WorkerThread.REDUCED_FILE_PATH = WorkerThread.FOLDER_PATH + hostName+ WorkerThread.REDUCED_FILE_PATH;
			Debug.show("ORIGIN_FILE_PATH is "+ WorkerThread.ORIGIN_FILE_PATH);
			Debug.show("REDUCED_FILE_PATH is "+ WorkerThread.REDUCED_FILE_PATH);
			WorkerThread.hostName = hostName;
			
			Debug.show(hostName);

			//start to work
			clientSentence = inFromClient.readLine();
			
			while (!clientSentence.equals("#ODF")) {
				
				if(clientSentence.startsWith("*")){
					 clientSentence = clientSentence.substring(1);
				}else if(clientSentence.startsWith("#")){

				}else if(clientSentence!=null){

//					if(clientSentence.length()==1){
//						stringPoolArray[0].put(clientSentence);
//					}else{
						TextLine textLine = new TextLine(clientSentence);
						textLinePoolArray[forward(textLine.line.charAt(1))].put(textLine);
//					}				
//					Debug.show("produce!" + clientSentence);
					clientSentence = inFromClient.readLine();	
				}

			}
			
			Debug.show("stop producing");
			for(int i = 0; i < numOfFile ;i++){
				this.textLinePoolArray[i].continueProducing = Boolean.FALSE;
			}
		
			timer.end();
				
		} catch (InterruptedException ex) {

			ex.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	public int forward(char c){
		return c % numOfFile;
	}
	
}
