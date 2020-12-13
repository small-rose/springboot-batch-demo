package cn.xiaocai.batch.schedule;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 以任务调度器方式启动作业，并在配置文件里加个开关
 * @author Xiaocai.Zhang
 */

@ConditionalOnProperty(prefix = "scheduled",name ="enabled" ,havingValue = "true")
@Component
@AllArgsConstructor
public class SpringScheduleService {

    private final Job scheduleJobDemo;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0/5 * * * * ?")
    public void demoScheduled() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(scheduleJobDemo, jobParameters);
    }
}
