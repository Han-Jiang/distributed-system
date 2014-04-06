
package han.nds.client;

import han.nds.TextLine;
import han.nds.Timer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

public class ClientFileReader implements Runnable
{
    private int loopNumber;

    public ClientFileReader(int loopNumber)
    {
        this.loopNumber = loopNumber;
    }


	@Override
	public void run() {
		
			Timer timer = new Timer(getClass()+"");
			
//			for(int i = 0;i<loopNumber;i++){
				outputResult(0);
//			}
			
			timer.end();
	}
	
	public void outputResult(int loopNumber){
		try {
			
//			FileReader reader = new FileReader( Client.FOLDER_PATH + Client.FILE_NAME +loopNumber +".txt" );
			FileReader reader = new FileReader( Client.FOLDER_PATH + Client.FILE_NAME  +".txt" );
			
			BufferedReader br = new BufferedReader(reader);
			String s1 = null;

			while ((s1 = br.readLine()) != null) {
	        	 System.out.println(s1);
			}
			
			br.close();
			reader.close();
			
		} catch (IOException e) {
			System.err.println("Error when try to open the file hosts.cfg");
			e.printStackTrace();
		}
	}

}
