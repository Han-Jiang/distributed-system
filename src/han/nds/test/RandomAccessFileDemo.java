package han.nds.test;

import java.io.*;

public class RandomAccessFileDemo {

   public static void main(String[] args) {
      try {
         // create a new RandomAccessFile with filename test
         RandomAccessFile raf = new RandomAccessFile("/home/students/hxj393/data/wordlist-10MB-0.2.txt", "r");

         // set the file pointer at 0 position
         raf.seek(0);

         int totalLine = 0;
         long totalLength =0;
         String s =raf.readLine();
         while(s!=null){
//        	 System.out.println("" + s);
        	 
        	 totalLine++;
        	 totalLength += s.length();
        	 s = raf.readLine();
         }
         // print the line
         System.out.println("totalLength is" + totalLength);
         System.out.println("totalLine is" + totalLine);
         System.out.println("Average length is" + totalLength/totalLine);


//         raf.seek(2);
//         // print the line
//         System.out.println("" + raf.readLine());
      } catch (IOException ex) {
         ex.printStackTrace();
      }

   }
}