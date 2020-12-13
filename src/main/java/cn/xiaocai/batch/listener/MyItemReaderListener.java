package cn.xiaocai.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.scope.context.JobContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.batch.api.chunk.listener.ItemReadListener;
import java.util.Map;

/**
 * 自定义 ItemRead 的监听器
 * @author Xiaocai.Zhang
 */
@Slf4j
public class MyItemReaderListener implements ItemReadListener {

    @Autowired
    private StepContext stepContext;
    @Autowired
    private JobContext jobContext;
    
    @Override
    public void beforeRead() throws Exception {
        Map<String, Object> stepExeContext = stepContext.getStepExecutionContext();
        Map<String, Object> jobExeContext = jobContext.getJobExecutionContext();
        log.info("before item read, stepExecutionContext : {}, stepExecutionContext : {}", jobExeContext, stepExeContext);
    }

    @Override
    public void afterRead(Object o) throws Exception {
        log.info("after item read , o is the item data ");
    }

    @Override
    public void onReadError(Exception e) throws Exception {
        log.info("when item read error, this method will execute ");
    }
}
