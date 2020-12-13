package cn.xiaocai.batch.itemrw.reader;

import org.springframework.batch.item.ItemReader;
import java.util.Iterator;
import java.util.List;

/**
 *  定义自己的 ItemReader
 * @author Xiaocai.Zhang
 */
public class MyDemoItemReader implements ItemReader<String> {

    private Iterator<String> iterator;

    public MyDemoItemReader(List<String> data) {
        this.iterator = data.iterator();
    }

    @Override
    public String read()  {
        return iterator.hasNext() ? iterator.next() : null;
    }
}
