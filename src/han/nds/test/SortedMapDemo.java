package han.nds.test;
import java.util.SortedSet;
import java.util.TreeSet;
  
public class SortedMapDemo{  
    public static void main(String[] args){
    	java.util.SortedMap sm = new java.util.TreeMap(); //treemap 是 SortedMap 接口的基于红黑树的实现，此类保证了映射按照升序顺序排列关键字
    	//放入键-值对
    	sm.put("1", "测试1");
    	sm.put("3", "测试3");
    	sm.put("5", "测试5");
    	sm.put("2", "测试2");
    	sm.put("4", "测试4");

    	Object keys[] = sm.keySet().toArray(); //获得排好序的key
    	Object values[] = sm.values().toArray(); //获得对应的value
    	
    	//当然也可以通过keySet等集合的方式来操作
    	for(int i = 0 ; i < keys.length; i++)
    	{
    	    System.out.println("键：" + keys[i] + " 值：" + values[i]);
    	}
       
    }  
}  