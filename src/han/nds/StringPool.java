
package han.nds;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class StringPool
{
    public ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(1000);
    public Boolean continueProducing = Boolean.TRUE;

    public void put(String data) throws InterruptedException
    {
        this.queue.put(data);
    }
    
    public String get() throws InterruptedException
    {
        return this.queue.poll(100, TimeUnit.MILLISECONDS);
    }
}
