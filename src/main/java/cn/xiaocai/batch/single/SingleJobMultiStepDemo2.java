package cn.xiaocai.batch.single;

import cn.xiaocai.batch.listener.MyJobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Zongyuan.Zhang
 */
@Slf4j
@Component
public class SingleJobMultiStepDemo2 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private MyJobListener myJobListener;
    @Bean
    public Job multiStepWithStatusJob() {
        return jobBuilderFactory.get("multiStepWithStatusJob")
                .start(step1())
                .listener(myJobListener)
                .on(ExitStatus.COMPLETED.getExitCode()).to(step2())
                .from(step2())
                .on(ExitStatus.COMPLETED.getExitCode()).to(step3())
                .from(step3()).end()
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("--multiStep with ExitStatus demo--this is  1 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("--multiStep with ExitStatus demo--this is  2 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("--multiStep with ExitStatus demo--this is  3 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
