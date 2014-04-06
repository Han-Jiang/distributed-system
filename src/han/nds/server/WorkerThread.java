package han.nds.server;

import han.nds.Debug;
import han.nds.TextLinePool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class WorkerThread extends Thread {
	
	private Socket socket = null;
	
	public static String hostName = null;

//	public static String FOLDER_PATH= "D:\\";
	public static String FOLDER_PATH ="/data/private/nds/temp/";
	public static String ORIGIN_FILE_PATH= "-ORIGIN_SERVER_FILE_";
	public static String REDUCED_FILE_PATH= "-REDUCED_SERVER_FILE_";
	
	public static int numOfFile = 4;
	
	public WorkerThread(Socket socket) {
		super("workerThread");
		this.socket = socket;
		 hostName = null;
		 FOLDER_PATH ="/data/private/nds/temp/";
		 ORIGIN_FILE_PATH= "-ORIGIN_SERVER_FILE_";
		 REDUCED_FILE_PATH= "-REDUCED_SERVER_FILE_";
	}

	public void run() {
		
		try {
			TextLinePool[] textLinePoolArray = new TextLinePool[numOfFile];
			Future[] futureArray = new Future[numOfFile];
			
			//get channel to read from client
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());


			ExecutorService threadPool = Executors.newFixedThreadPool(numOfFile+2);
			Future producerStatus = threadPool.submit(new ServerProducer(inFromClient, textLinePoolArray,numOfFile));
			
			 //initialize every client
			for(int i = 0; i<numOfFile; i++){
				textLinePoolArray[i] = new TextLinePool(); 
			}
			
			for(int i = 0; i<numOfFile; i++){
				futureArray[i] = threadPool.submit(new ServerFileSaver(i, textLinePoolArray[i]));
			}
			
			// this will wait for the producer to finish its execution.
			producerStatus.get();
			Debug.show("producer thread finished");
			
			//wait for all the file saver to finish
			for(int i = 0; i<numOfFile; i++){
				futureArray[i].get();
			}
			Debug.show("file saver thread finished");
			
			for(int i = 0; i<numOfFile; i++){
				futureArray[i] = threadPool.submit(new ServerReducer(i));
			}
			//wait for all the file saver to finish
			for(int i = 0; i<numOfFile; i++){
				futureArray[i].get();
			}
			Debug.show("all worker thread finished");

			Debug.show("sender thread beigin");
			Future senderStatus = threadPool.submit(new ServerSender(inFromClient, outToClient,"Server Sender"));
			senderStatus.get();
			Debug.show("sender thread finished");
	
			
			threadPool.shutdown();
			
			//close the socket
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}