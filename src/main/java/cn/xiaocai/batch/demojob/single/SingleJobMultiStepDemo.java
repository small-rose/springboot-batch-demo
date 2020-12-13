package cn.xiaocai.batch.demojob.single;

import lombok.AllArgsConstructor;
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
 * 多个step的Job示例
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class SingleJobMultiStepDemo {


    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job multiStepJob() {
        return jobBuilderFactory.get("multiStepJob")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("--multiStep demo--this is  1 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("--multiStep demo--this is  2 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("--multiStep demo--this is  3 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
