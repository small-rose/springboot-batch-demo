package cn.xiaocai.batch.demojob.single;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 单个step示例
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class SingleJobSingleStepDemo {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     *  以Bean的形式放入ioc，本实例仅作demo使用，实际请放到单独的config中，其他同理，不再单独说明
     * @return
     */
    @Bean
    public Job singleStepJobDemo() {

        return jobBuilderFactory.get("singleStepJobDemo")
                .start(singleStep())
                .build();
    }

    private Step singleStep() {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----this is my first job ....");
                    StepExecution stepExe = contribution.getStepExecution();
                    ExecutionContext stepContext = stepExe.getExecutionContext();
                    Object line = stepContext.get("lineNum");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
