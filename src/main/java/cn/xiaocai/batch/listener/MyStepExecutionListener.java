package cn.xiaocai.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;

import javax.batch.runtime.context.StepContext;

/**
 * 自定义 Step执行 的监听器
 * 这个是注解的使用示例，也可以使用
 * MyStepExecutionListener implements StepExecutionListener
 * 的方式重写 beforeStep 和 afterStep方法
 * @author Xiaocai.Zhang
 */
@Slf4j
public class MyStepExecutionListener {

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        String stepName = stepExecution.getStepName();
        ExecutionContext stepExeContext = stepExecution.getExecutionContext();

        log.info("before step : stepName = {}, stepExeContext = {} ", stepName, stepExeContext);
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        System.out.println("after step execute: " + stepExecution.getStepName());
        if (ExitStatus.COMPLETED.equals(stepExecution.getExitStatus()) ) {
            //step success
            log.info("step success !");
        }
        else if (ExitStatus.FAILED.equals(stepExecution.getExitStatus())) {
            //step failure
            log.info("step failure !");
        }
    }
}
