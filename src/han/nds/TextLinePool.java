
package han.nds;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TextLinePool
{
    public ArrayBlockingQueue<TextLine> queue = new ArrayBlockingQueue<TextLine>(1000);
    public Boolean continueProducing = Boolean.TRUE;

    public void put(TextLine data) throws InterruptedException
    {
        this.queue.put(data);
    }
    
    public TextLine get() throws InterruptedException
    {
        return this.queue.poll(100, TimeUnit.MILLISECONDS);
    }
}
