package cn.xiaocai.batch.nested;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
public class NestedJobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    /**
     * 父任务
     * @return
     */
    @Bean
    public Job nestedJob() {
        return jobBuilderFactory.get("nestedJob")
                .start(childJobOneStep())
                .next(childJobTwoStep())
                .build();
    }


    /**
     * 将任务转换为特殊的步骤
      * @return
     */
    private Step childJobOneStep() {
        return new JobStepBuilder(new StepBuilder("childJobOneStep"))
                .job(childJobOne())
                .job(childJobThree())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    /**
     * 将任务转换为特殊的步骤
     * @return
     */
    private Step childJobTwoStep() {
        return new JobStepBuilder(new StepBuilder("childJobTwoStep"))
                .job(childJobTwo())
                .job(childJobFour())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    /**
     * 子任务一
     * @return
     */
    private Job childJobOne() {
        return jobBuilderFactory.get("childJobOne")
                .start(
                        stepBuilderFactory.get("childJobOneStep")
                                .tasklet((stepContribution, chunkContext) -> {
                                    System.out.println("子任务一执行步骤。。。");
                                    return RepeatStatus.FINISHED;
                                }).build()
                ).build();
    }

    /**
     * 子任务二
     * @return
     */
    private Job childJobTwo() {
        return jobBuilderFactory.get("childJobTwo")
                .start(
                        stepBuilderFactory.get("childJobTwoStep")
                                .tasklet((stepContribution, chunkContext) -> {
                                    System.out.println("子任务二执行步骤。。。");
                                    return RepeatStatus.FINISHED;
                                }).build()
                ).build();
    }

    /**
     * 子任务三
     * @return
     */
    private Job childJobThree() {
        return jobBuilderFactory.get("childJobThree")
                .start(
                        stepBuilderFactory.get("childJobThreeStep")
                                .tasklet((stepContribution, chunkContext) -> {
                                    System.out.println("子任务三执行步骤。。。");
                                    return RepeatStatus.FINISHED;
                                }).build()
                ).build();
    }

    /**
     * 子任务四
     * @return
     */
    private Job childJobFour() {
        return jobBuilderFactory.get("childJobFour")
                .start(
                        stepBuilderFactory.get("childJobFourStep")
                                .tasklet((stepContribution, chunkContext) -> {
                                    System.out.println("子任务四执行步骤。。。");
                                    return RepeatStatus.FINISHED;
                                }).build()
                ).build();
    }
}
