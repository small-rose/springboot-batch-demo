package cn.xiaocai.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;


/**
 * 自定义 ItemWrite 的监听器
 * @author Xiaocai.Zhang
 */
@Slf4j
public class MyItemWriterListener implements ItemWriteListener {

    @Override
    public void beforeWrite(List list) {
        log.info("before write data  ");
    }

    @Override
    public void afterWrite(List list) {
        log.info("after write data ");
    }

    @Override
    public void onWriteError(Exception e, List list) {
        log.info("when data write error, this method will execute ");
    }
}
