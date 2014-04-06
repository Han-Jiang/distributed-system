package han.nds.test;

import java.io.*;

public class RandomAccessFileDemoToLine {

   public static void main(String[] args) {
      try {
         // create a new RandomAccessFile with filename test
         RandomAccessFile raf = new RandomAccessFile("/home/students/hxj393/test2.txt", "rw");

         // write something in the file
         raf.writeBytes("Hello World".toString());

         // set the file pointer at 0 position
         raf.seek(0);

         // print the line
         System.out.println("" + raf.readLine());

         // set the file pointer at 0 position
         raf.seek(0);

         // write something in the file
         raf.writeBytes("This is an example: \n Hello World".toString());

         raf.seek(0);
         // print the line
         System.out.println("" + raf.readLine());
      } catch (IOException ex) {
         ex.printStackTrace();
      }

   }
}