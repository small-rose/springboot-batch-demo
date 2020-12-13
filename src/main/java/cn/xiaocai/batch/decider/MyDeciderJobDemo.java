package cn.xiaocai.batch.decider;

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
 * 使用自定义决策器
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class MyDeciderJobDemo {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final MyDecider myDecider;

    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get("deciderJob")
                .start(step1())
                .next(myDecider)
                // 如果当前日期是周末执行 step2
                .from(myDecider).on("weekend").to(stepWeekEnd())
                // 如果当前日期不是周末执行 step
                .from(myDecider).on("workingDay").to(stepWorkingDay())
                //如果执行了step3，那么无论step3的结果是什么，都将执行step4。
                .from(stepWorkingDay()).on("*").to(step4())
                .end()
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("--execute step 1 --");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step stepWeekEnd() {
        return stepBuilderFactory.get("stepWeekEnd")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("--execute step WeekEnd work --");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step stepWorkingDay() {
        return stepBuilderFactory.get("stepWorkingDay")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("--execute step WorkingDay work --");
                    return RepeatStatus.FINISHED;
                }).build();
    }


    private Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("--execute step 4 --");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
