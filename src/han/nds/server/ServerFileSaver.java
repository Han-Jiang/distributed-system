package han.nds.server;

import han.nds.Debug;
import han.nds.TextLine;
import han.nds.TextLinePool;
import han.nds.Timer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;


public class ServerFileSaver implements Runnable {
	
	private int index;
	private String FILE_PATH;
	private TextLinePool textLinePool;

	public ServerFileSaver( int index, TextLinePool textLinePool) {
		this.index = index;
		this.textLinePool = textLinePool;
	}

	@Override
	public void run() {
		try {
			while(WorkerThread.hostName == null){
    			System.err.println("Waiting for the host name! ");
    		}
			FILE_PATH = WorkerThread.ORIGIN_FILE_PATH;
			
			Timer timer = new Timer(getClass() + " "+index);

			FileWriter fileWriter = null;
			BufferedWriter bufferedWriter = null;
			Debug.show("file name is"+ FILE_PATH+index+".txt");
			fileWriter = new FileWriter(FILE_PATH+index+".txt",false);
			
			bufferedWriter = new BufferedWriter(fileWriter);

			TextLine textLine = textLinePool.get();
			while (textLine != null || textLinePool.continueProducing ) {
				if(textLine != null){
					bufferedWriter.write(textLine.toString() + '\n');
				}
				textLine = textLinePool.get();
			}
			
			bufferedWriter.close();
			fileWriter.close();
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
