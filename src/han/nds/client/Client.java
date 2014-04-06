package han.nds.client;

import han.nds.Debug;
import han.nds.StringPool;
import han.nds.Timer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Client {
	
	public static String[] hosts = null;
	public static int numOfMachine = 1;
	public static int totalHostNumber = 0;
	
	private static int startLineNum = 0;
    private static int endLineNum;
    public static int totalLineNumber;
    private static int loopNumber = 1;
    public static int currentLoopNumber = 0;
    
    private static int FILE_SIZE_LINE_NUMBER = 1500000; //about 10MB
    
    public static String FOLDER_PATH ="/data/private/nds/temp/";
//    public static String FOLDER_PATH ="D:\\";
    public static String FILE_NAME = "ClientSideWriter-";
   
	public static boolean isProduceFinished = false;
	public static boolean isConsumerFinished = false;
	public static boolean isClientFinished = false;
	
	public static void main(String argv[]) throws Exception {
		//read host file
		readHostFile();
		if (hosts == null) {
			System.exit(0);
		}
		Debug.show("");
		Debug.show("Number of machines listed in the host.cfg is "
				+ hosts.
				length);
		//check the input
		if (argv.length != 1 || !isNumeric(argv[0])) {
			System.err.println("The system can only accept one integer as argument");
			System.err.println("to indicate how much machine you want to use.");
		} else {
			//parse the number of integer
			numOfMachine = Integer.parseInt(argv[0]);
			if (numOfMachine > hosts.length || numOfMachine < 1) {
				System.err.println("Number of machine use should between 1 to "
						+ hosts.length);
			}else{
				
				try {
					
					Timer timer = new Timer("NDS Work");
					
					StringPool[] stringPoolArray = new StringPool[numOfMachine];
					Future[] futureArray = new Future[numOfMachine];
					Socket[] clientSocketArray = new Socket[numOfMachine];
					BufferedReader[] inFromServerArray = new BufferedReader[numOfMachine];
				    DataOutputStream[] outToServerArray = new DataOutputStream[numOfMachine];
				    
				    ConcurrentSkipListMap<Integer, String> concurrentSkipListMap = new ConcurrentSkipListMap<Integer, String>();
				    
				    //initialize every client
					for(int i = 0; i<numOfMachine; i++){
						 stringPoolArray[i] = new StringPool();
						 clientSocketArray[i] =  new Socket(hosts[i], 50100);
						 outToServerArray[i] = new DataOutputStream(
								 clientSocketArray[i].getOutputStream());
						 inFromServerArray[i] = new BufferedReader(
								 new InputStreamReader(clientSocketArray[i].getInputStream()));
					}

					ExecutorService threadPool = Executors.newFixedThreadPool(numOfMachine+2);
					
					Future producerStatus = threadPool.submit(new ClientProducer(stringPoolArray, numOfMachine));
					for(int i = 0; i<numOfMachine; i++){
						futureArray[i] = threadPool.submit(new ClientConsumer(stringPoolArray[i], hosts[i],
								clientSocketArray[i],inFromServerArray[i],outToServerArray[i]));
					}
					
					//wait for the producer to finishe it's job
					producerStatus.get();
					
					//wait for all the consumer to finish
					for(int i = 0; i<numOfMachine; i++){
						futureArray[i].get();
					}
					
					//begin to get subfile
					Debug.show("totalLineNumber is "+totalLineNumber);
						loopNumber = totalLineNumber / FILE_SIZE_LINE_NUMBER + 1;
						System.err.println("loopNumber is "+loopNumber);
						for (int k = 0 ; k<loopNumber;k++){
							Debug.show("I start my round"+k);
							
							currentLoopNumber =k;
							startLineNum = 0 + k*FILE_SIZE_LINE_NUMBER;
							endLineNum = 0 + (k+1)*FILE_SIZE_LINE_NUMBER;
							if(endLineNum>totalLineNumber){
								endLineNum = totalLineNumber+1;
							}
							concurrentSkipListMap.clear();
							
							futureArray = new Future[numOfMachine];
							
							//receive data
							for(int i = 0; i<numOfMachine; i++){
								futureArray[i] = threadPool.submit(new ClientReceiver(hosts[i],
										clientSocketArray[i],inFromServerArray[i],outToServerArray[i],
										concurrentSkipListMap,startLineNum,endLineNum));
							}
							
							Debug.show("All has started in round "+k);
							//wait for the file to finish
							for(int i = 0; i<numOfMachine; i++){
								futureArray[i].get();
							}

							Debug.show("All finished waiting "+k);
							//Writer beginhas started in round
							Future writerStatus = threadPool.submit(new ClientFileSaver(concurrentSkipListMap, k));
							writerStatus.get();
							
							Debug.show("I finished my round"+k);
						}

					//tell the server to exit
					for(int i = 0; i<numOfMachine; i++){
						outToServerArray[i].writeBytes("#exitAll\n");
						clientSocketArray[i].close();
					}
					
					//out put the result
					Future resultStatus = threadPool.submit(new ClientFileReader(loopNumber));
					resultStatus.get();
					
					threadPool.shutdown();		
					
					Debug.show("All done");
					
					timer.end();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}

		
		
	}
	
	
	/**
	 * load data from the file into small map matrix
	 */
	public static void readHostFile() {
		String  HOST_FILE_PATH ="../hosts.cfg";

		File dir = new File(HOST_FILE_PATH);
		if (dir.exists()) {

			try {
				//get the total line of the host.cfg(number of available machine)
				int totalHostNumber = getTotalLines(dir);
				hosts = new String[totalHostNumber];
				
				FileReader reader = new FileReader(HOST_FILE_PATH);
				BufferedReader br = new BufferedReader(reader);
				String s1 = null;
				int i = 0;
				while ((s1 = br.readLine()) != null) {
					hosts[i] = s1;
					i++;
				}
				br.close();
				reader.close();
				
			} catch (IOException e) {
				System.err.println("Error when try to open the file hosts.cfg");
				e.printStackTrace();
			}
		}else{
			System.err.println("Error!: Can not find the hosts.cfg in the root directory of the program!");
		}

	}
	
	/**
	 * get the total line of the host.cfg(number of available machine)
	 */
    public static int getTotalLines(File file) throws IOException {  
        FileReader in = new FileReader(file);  
        LineNumberReader reader = new LineNumberReader(in);  
        String s = reader.readLine();  
        int lines = 0;  
        while (s != null) {  
            lines++;  
            s = reader.readLine();  
        }  
        reader.close();  
        in.close();  
        return lines;  
    }
    
	/**
	 * judge whether a string is numeric
	 */
    public static boolean isNumeric(String str){
    	  for (int i = str.length();--i>=0;){   
    	   if (!Character.isDigit(str.charAt(i))){
    	    return false;
    	   }
    	  }
    	  return true;
    }
}