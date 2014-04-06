package han.nds.test;

import han.nds.Debug;
import han.nds.TextLine;

import java.util.Comparator;
public class SortById implements Comparator<Object>{  
  
    @Override  
    public int compare(Object o1, Object o2) {  
        TextLine line1=(TextLine)o1;  
        TextLine line2=(TextLine)o2;
        
        //line1 is the new element
        if (line1.line.equals(line2.line)){
         	Debug.show("equal");
        	Debug.show(line1);
        	Debug.show(line2);
        	line2.id = Math.min(line1.id, line2.id);
         	Debug.show("after");
        	Debug.show(line1);
        	Debug.show(line2);
        	
        	return 0;
        	
        }else{
        	
        	if (line1.id-line2.id<0){
        		return -1;
        	}
        	if(line1.id-line2.id>0){
        		return 1;
        	}
        }
		return 0;

    }  
}  