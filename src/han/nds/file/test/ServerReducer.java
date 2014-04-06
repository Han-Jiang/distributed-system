
package han.nds.file.test;

import han.nds.Debug;
import han.nds.StringPool;
import han.nds.TextLine;
import han.nds.Timer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;


/**
 * Server consumer, handle data
 */
public class ServerReducer implements Runnable
{

    private int index;

    private String REDUCED_FILE_PATH;
    private String ORIGIN_FILE_PATH;
    
    private LinkedHashSet<TextLine> hashSet;

    public ServerReducer( int index){
        this.index = index;
    }

	@Override
	public void run() {

		ORIGIN_FILE_PATH = "D:/localhostORIGIN_SERVER_FILE_";
	    REDUCED_FILE_PATH = REDUCED_FILE_PATH= "D:/localhostREDUCED_SERVER_FILE_";

		Timer timer = new Timer(getClass() + "");

//		try {
//			int counter = 0;
//			FileReader reader = new FileReader(ORIGIN_FILE_PATH + index
//					+ ".txt");
//			BufferedReader br = new BufferedReader(reader);
//			String s1 = null;
//
//			while ((s1 = br.readLine()) != null) {
//				hashSet.add(new TextLine(s1));
//				counter++;
//			}
//			
//			System.out.println("My counter is "+ counter);
//			System.out.println("My hashset size is "+ hashSet.size());
//
//			br.close();
//			reader.close();
//
//		} catch (IOException e) {
//			System.err.println("Error when try to open the file");
//			e.printStackTrace();
//		}
		
		
		try {
			int counter = 0;
			hashSet = new LinkedHashSet<TextLine>();
			FileReader reader = new FileReader(ORIGIN_FILE_PATH + index
					+ ".txt");
			BufferedReader br = new BufferedReader(reader);
			String s1 = null;
			int a = hashSet.size();
			while ((s1 = br.readLine()) != null) {
				hashSet.add(new TextLine(s1));
				counter++;
			}
			
			
			System.out.println("My counter is "+ counter);
			System.out.println("My hsahset size is "+ (hashSet.size()-a));
			br.close();
			reader.close();

		} catch (IOException e) {
			System.err.println("Error when try to open the file");
			e.printStackTrace();
		}

//		Debug.show("data handle finished, total size is" + hashSet.size());
//
//		try {
//			FileWriter fileWriter = null;
//			BufferedWriter bufferedWriter = null;
//			fileWriter = new FileWriter(REDUCED_FILE_PATH + index + ".txt",
//					false);
//			bufferedWriter = new BufferedWriter(fileWriter);
//			for (TextLine textLine : hashSet) {
//				bufferedWriter.write(textLine.toString() + '\n');
//			}
//			bufferedWriter.close();
//			fileWriter.close();
//		} catch (IOException e) {
//			System.err.println("Error when try to open the file");
//			e.printStackTrace();
//		}

		timer.end();

	}
}
