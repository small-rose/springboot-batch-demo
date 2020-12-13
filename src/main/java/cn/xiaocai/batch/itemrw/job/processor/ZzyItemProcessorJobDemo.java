package cn.xiaocai.batch.itemrw.job.processor;

import cn.xiaocai.batch.VO.UserVO;
import cn.xiaocai.batch.itemrw.processor.DataFilterItemProcessor;
import cn.xiaocai.batch.itemrw.processor.DataTransformItemProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 自定义的 处理器 使用示例
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class ZzyItemProcessorJobDemo {
     
    private final JobBuilderFactory jobBuilderFactory;
     
    private StepBuilderFactory stepBuilderFactory;
    
    private ListItemReader<UserVO> simpleReader;

    /**
     * 直接使用自定义处理器
     */
    private DataFilterItemProcessor dataFilterItemProcessor;
    private DataTransformItemProcessor dataTransformItemProcessor;

    @Bean
    public Job zzyItemProcessorJob() {
        return jobBuilderFactory.get("zzyItemProcessorJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<UserVO, UserVO>chunk(2)
                .reader(simpleReader)
                .processor(dataFilterItemProcessor)
                .processor(dataTransformItemProcessor)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
