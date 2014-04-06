package han.nds.client.local;

import han.nds.StringPool;
import han.nds.Timer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Client {
	
	public static String[] hosts = null;
	public static int numOfMachine = 1;
	
	public static boolean isProduceFinished = false;
	public static boolean isConsumerFinished = false;
	public static boolean isClientFinished = false;
	
	public static void main(String argv[]) throws Exception {

		readHostFile();
		if (hosts == null) {
			System.exit(0);
		}
		System.out.println("");
		System.out.println("Number of machines listed in the host.cfg is "
				+ hosts.length);

		if (argv.length != 1 || !isNumeric(argv[0])) {
			System.out.println("The system can only accept one integer as argument");
			System.out.println("to indicate how much machine you want to use.");
		} else {
			numOfMachine = Integer.parseInt(argv[0]);
			if (numOfMachine > hosts.length || numOfMachine < 1) {
				System.out.println("Number of machine use should between 1 to "
						+ hosts.length);
			}else{
				
				try {
					
					Timer timer = new Timer("NDS Work");
					
					StringPool[] stringPoolArray = new StringPool[numOfMachine];
					Future[] futureArray = new Future[numOfMachine];

					for(int i = 0; i<numOfMachine; i++){
						 stringPoolArray[i] = new StringPool();
					}

					ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();

					ExecutorService threadPool = Executors.newFixedThreadPool(numOfMachine+2);
					
					Future producerStatus = threadPool.submit(new ClientProducer(stringPoolArray, numOfMachine));
					
					for(int i = 0; i<numOfMachine; i++){
						futureArray[i] = threadPool.submit(new ClientLocalMerger(stringPoolArray[i], hosts[i], concurrentSkipListMap));
					}
//					Future consumerStatus = threadPool.submit(new ClientConsumerSender(stringPoolAry, hosts[0], concurrentSkipListMap));
//					Future consumer2Status = threadPool.submit(new ClientConsumerSender(stringPool2, hosts[1], concurrentSkipListMap));

//					threadPool.execute(new ClientConsumerSender(hosts[0], stringPool, hosts[0]));
//					threadPool.execute(new ClientConsumerSender(hosts[0], stringPool, hosts[0]));
//					threadPool.execute(new ClientConsumerSender(hosts[1], stringPool, hosts[1]));

					// this will wait for the producer to finish its execution.
					producerStatus.get();
					
					for(int i = 0; i<numOfMachine; i++){
						futureArray[i].get();
					}
//					consumerStatus.get();
//					consumer2Status.get();
					
					Future writerStatus = threadPool.submit(new ClientSideServerWriter(concurrentSkipListMap));
					writerStatus.get();
					
					System.out.println("All done");
					timer.end();
//					while(true){
//						if(isConsumerFinished){
//							
//						}
//					}
					threadPool.shutdown();
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
				int totalLine = getTotalLines(dir);
				hosts = new String[totalLine];
				
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
				System.out.println("Error when try to open the file hosts.cfg");
				e.printStackTrace();
			}
		}else{
			System.out.println("Error!: Can not find the hosts.cfg in the root directory of the program!");
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