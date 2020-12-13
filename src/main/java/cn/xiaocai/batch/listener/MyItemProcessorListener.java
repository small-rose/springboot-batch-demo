package cn.xiaocai.batch.listener;

import cn.xiaocai.batch.constants.JobContextConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.scope.context.JobContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 自定义 ItemProcessor 的监听器
 * @author Xiaocai.Zhang
 */
@Slf4j
public class MyItemProcessorListener implements ItemProcessListener<String, String> {

    @Autowired
    private StepContext stepContext;
    @Autowired
    private JobContext jobContext;


    @Override
    public void beforeProcess(String item) {
        // 这里可以获取Job执行的上下文
        Map<String, Object> jobExecutionContext = stepContext.getJobExecutionContext();
        Integer lineNum1 = (Integer)jobExecutionContext.get(JobContextConstants.LINE_NUM);


        String jobName = jobContext.getJobName();
        String stepName = stepContext.getStepName();

        log.info("job name is {} ,step name is {} ,before process item {} ",jobName, stepName, item);
    }

    @Override
    public void afterProcess(String item, String result) {
        log.info("after process: " + item + " result: " + result);
    }

    @Override
    public void onProcessError(String item, Exception e) {
        log.info("on process error: " + item + " , error message: " + e.getMessage());
    }
}
