package cn.xiaocai.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.scope.context.JobContext;
import org.springframework.batch.core.scope.context.StepContext;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 自定义 Job作业执行 的监听器
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
public class MyJobExecutionListener implements JobExecutionListener {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private JobContext jobContext;
    StepContext stepContext;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = LocalDateTime.now();
        log.info("start time {}", startTime);
        JobParameters jobParameters = jobExecution.getJobParameters();
        log.info("jobParameters {}", jobParameters);

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = LocalDateTime.now();
        log.info("start time {}", endTime);
        log.info("elapsed time: {} s", Duration.between(startTime, endTime).getSeconds());
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus()) ) {
            //job success
            log.info("job success !");
        }
        else if (BatchStatus.FAILED.equals(jobExecution.getStatus())) {
            //job failure
            log.info("job failure !");
        }

    }
}
