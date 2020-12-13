package cn.xiaocai.batch.demojob.parallel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

/**
 务中的步骤除了可以串行执行（一个接着一个执行）外，还可以并行执行，并行执行在特定的业务需求下可以提供任务执行效率。

 将任务并行化只需两个简单步骤：
     (1)将步骤Step转换为Flow；
     (2)任务Job中指定并行Flow。
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class ParallelJobDemo {


    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parallelJob() {
        return jobBuilderFactory.get("splitJob")
                .start(flow1())
                .split(new SimpleAsyncTaskExecutor()).add(flow2())
                .end()
                .build();

    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {

                    log.info("--parallel demo--this is  1 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {

                    log.info("--parallel demo--this is  2 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("--parallel demo--this is  3 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    private Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("--parallel demo--this is  4 step job ....");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Flow flow1() {
        return new FlowBuilder<Flow>("flow1")
                .start(step1())
                .next(step3())
                .build();
    }

    private Flow flow2() {
        return new FlowBuilder<Flow>("flow2")
                .start(step2())
                .next(step4())
                .build();
    }
}
