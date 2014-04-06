package han.nds.file.test;

import han.nds.TextLine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;

public class PureHashSetTest {
	
	
	 private String REDUCED_FILE_PATH;
	 private static String ORIGIN_FILE_PATH = "D:/localhostORIGIN_SERVER_FILE_";
//	 private static String ORIGIN_FILE_PATH = "D:/localhostREDUCED_SERVER_FILE_";
	 
	 
	 private static LinkedHashSet<TextLine> hashSet = new LinkedHashSet<TextLine>();
	 
	 private static LinkedHashSet<String> hashSetOrigin = new LinkedHashSet<String>();
	    
	public static void main(String[] args){
//		int loopNumber = 4;
//		for(int i = 0; i< loopNumber ;i++){
//			String fileName = ORIGIN_FILE_PATH + i+ ".txt";
//			testFileWithCounter(fileName);
////			fileName = REDUCED_FILE_PATH + index + ".txt";
//		}
//		System.out.println("Total element is "+ hashSet.size());
		
//		testFile("D:/wordlist-10MB-0.2.txt");
		
//		testFile("D:/ClientSideWriter0.txt");
		testFile("D:/wordlist-224000.txt");
		
	}
	
	public static void testFileWithCounter(String fileName){
		try {
			int counter = 0;
			FileReader reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);
			String s1 = null;
			
			hashSet = new LinkedHashSet<TextLine>();
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
	}
	
	public static void testFile(String fileName){
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);
			String s1 = null;

			while ((s1 = br.readLine()) != null) {
				hashSetOrigin.add(s1);
			}
			System.out.println("Total element is "+ hashSetOrigin.size());
			br.close();
			reader.close();

		} catch (IOException e) {
			System.err.println("Error when try to open the file");
			e.printStackTrace();
		}
	}
	
	
	public static void savefile(String fileName){
		try {
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		fileWriter = new FileWriter(fileName,
				false);
		bufferedWriter = new BufferedWriter(fileWriter);
		for (TextLine textLine : hashSet) {
			bufferedWriter.write(textLine.toString() + '\n');
		}
		bufferedWriter.close();
		fileWriter.close();
	} catch (IOException e) {
		System.err.println("Error when try to open the file");
		e.printStackTrace();
	}
	}
}

