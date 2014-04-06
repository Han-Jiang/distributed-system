package han.nds.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

class ClientChecker {
	
    public static int totalLineNumber=0;
    private static int loopNumber = 4;
    public static int currentLoopNumber = 0;
//    static String PATH_PREFIX = "/home/students/hxj393/data/";
    static String PATH_PREFIX = "/data/private/nds/temp/";
    
    private static String FILE_NAME = "localhostORIGIN_SERVER_FILE_";

//    static String PATH_PREFIX = "D:/";

	public static void main(String argv[]) throws Exception {

		for(int i = 0; i< loopNumber ;i++){
			String fileName =PATH_PREFIX+FILE_NAME+i+".txt";
			if(readFile(fileName)){
				System.out.println(fileName+" is correct");
			}else{
				System.out.println(fileName+" is wrong");
			}
		}

		
		
		
//		String filePrefix ="cca-lg36-0811ORIGIN_SERVER_FILE_";
		String file1 ="cca-lg36-0810";
		String file2 ="cca-lg36-0811";
		String file3 ="cca-lg36-0819";

		
//		String filePrefix  = "REDUCED_SERVER_FILE_";

//		String filePrefix  = "ORIGIN_SERVER_FILE_";
		
//		for(int i = 0; i< 4 ;i++){
//			String fileName ="/home/students/hxj393/NDS/bin/"+file1+filePrefix+i+".txt";
//			if(readFile(fileName)){
//				System.out.println(fileName+" is correct");
//			}else{
//				System.out.println(fileName+" is wrong");
//			}
//		}
		
		
//		for(int i = 0; i< 4 ;i++){
//			String fileName ="/home/students/hxj393/NDS/bin/"+file2+filePrefix+i+".txt";
//			if(readFile(fileName)){
//				System.out.println(fileName+" is correct");
//			}else{
//				System.out.println(fileName+" is wrong");
//			}
//		}
//		for(int i = 0; i< 4 ;i++){
//			String fileName ="/home/students/hxj393/NDS/bin/"+file3+filePrefix+i+".txt";
//			if(readFile(fileName)){
//				System.out.println(fileName+" is correct");
//			}else{
//				System.out.println(fileName+" is wrong");
//			}
//		}

		System.out.println("totalLineNumber is "+totalLineNumber);
	}
	
	
	/**
	 * load data from the file into small map matrix
	 */
	public static boolean readFile(String filePath) {

		File dir = new File(filePath);
		if (dir.exists()) {

			try {
				//get the total line of the host.cfg(number of available machine)
				int lineNumber = getTotalLines(dir);
				
//				System.out.println("LineNumber is "+lineNumber);
				totalLineNumber += lineNumber;
				
				FileReader reader = new FileReader(filePath);
				BufferedReader br = new BufferedReader(reader);
				String s1 = null;
				int i =1;
				int oldNum = -1;
				int newNum = 0;
				while ((s1 = br.readLine()) != null) {
					
					int  index = s1.indexOf(32);
					newNum = Integer.parseInt(s1.substring(0,index));
					if(oldNum >= newNum){
						System.err.println("Error at line" + i);
						return false;
					}else{
						oldNum = newNum;
					}
					i++;

				}
				
				br.close();
				reader.close();
				
			} catch (IOException e) {
				System.out.println("Error when try to open the file hosts.cfg");
				e.printStackTrace();
			}
			
			return true;
		}else{
			System.out.println("Error!: Can not find the hosts.cfg in the root directory of the program!");
		}
		return false;

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