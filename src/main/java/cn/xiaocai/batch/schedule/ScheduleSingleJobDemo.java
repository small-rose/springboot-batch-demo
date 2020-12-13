package cn.xiaocai.batch.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 配合定时调度的示例
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class ScheduleSingleJobDemo {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     *  以Bean的形式放入ioc，配合scheduled 调度器执行
     * @return
     */
    @Bean
    public Job scheduleJobDemo() {
        return jobBuilderFactory.get("scheduleJobDemo")
                .start(singleStep())
                .build();
    }

    private Step singleStep() {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    log.info("--this is schedule job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
