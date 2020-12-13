package cn.xiaocai.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Xiaocai.Zhang
 */
@Slf4j
public class MyJobListener  implements JobExecutionListener {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = LocalDateTime.now();
        log.info("start time {}", startTime);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = LocalDateTime.now();
        log.info("start time {}", endTime);
        log.info("elapsed time: {} s", Duration.between(startTime, endTime).getSeconds());


    }
}
