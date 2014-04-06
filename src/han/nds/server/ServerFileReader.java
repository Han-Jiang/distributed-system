package han.nds.server;

import han.nds.Debug;
import han.nds.TextLine;
import han.nds.Timer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ServerFileReader implements Runnable {

	private int index;
	private int startLine;
	private int endLine;
	private ConcurrentSkipListMap<Integer,String> concurrentSkipListMap;
	
	private String REDUCED_FILE_PATH;

	public ServerFileReader(int index, int startLine,int endLine, 
			ConcurrentSkipListMap<Integer,String> concurrentSkipListMap) {
		this.index = index;
		this.startLine = startLine;
		this.endLine = endLine;
		this.concurrentSkipListMap = concurrentSkipListMap;
		
	}

	@Override
	public void run() {
			while(WorkerThread.hostName == null){
				System.err.println("Waiting for the host name! ");
			}
			REDUCED_FILE_PATH = WorkerThread.REDUCED_FILE_PATH;

	
			Timer timer = new Timer(getClass() + "");
			
			/**
			 * Using the OptimizedRandomAccessFile
			 */
//			try {
//				OptimizedRandomAccessFile raf = new OptimizedRandomAccessFile(FILE_PATH+index+".txt", "r");
//				raf.seek(0);
//				
//				 String s1 =raf.readLine();
//		         while(s1!=null){
//
//		        	 TextLine textLine = new TextLine(s1);
//		        	 if(textLine.id>startLine && textLine.id<endLine){
//		        		 concurrentSkipListMap.put(textLine.id,textLine.line);
//		        	 }
//		        	 
//		        	 if(textLine.id>endLine){
//		        		 break;
//		        	 }
//		         }
//		         
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			Debug.show("I am going to read "+REDUCED_FILE_PATH+index+".txt");

			try {
				FileReader reader = new FileReader(REDUCED_FILE_PATH+index+".txt");
				BufferedReader br = new BufferedReader(reader);
				String s1 = null;

				while ((s1 = br.readLine()) != null) {
					 TextLine textLine = new TextLine(s1);
		        	 if(textLine.id>=startLine && textLine.id<endLine){
		        		 concurrentSkipListMap.put(textLine.id,textLine.line);
		        	 }
		        	 
		        	 if(textLine.id>endLine){
		        		 break;
		        	 }
				}
				
				br.close();
				reader.close();
				
			} catch (IOException e) {
				System.err.println("Error when try to open the file hosts.cfg");
				e.printStackTrace();
			}
			
			
			timer.end();
	}
	


}
