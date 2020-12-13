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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 内置的 CompositeItemProcessor 组合多种中间处理器 使用示例
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class CompositeItemProcessorJobDemo {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ListItemReader<UserVO> simpleReader;

    /**
     * 使用自定义处理器
     */
    private final DataFilterItemProcessor dataFilterItemProcessor;
    private final DataTransformItemProcessor dataTransformItemProcessor;

    @Bean
    public Job compositeItemProcessorJob() {
        return jobBuilderFactory.get("compositeItemProcessorJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<UserVO, UserVO>chunk(2)
                .reader(simpleReader)
                .processor(compositeItemProcessor())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    /**
     * CompositeItemProcessor 组合多种中间处理器
     * @return
     */
    private CompositeItemProcessor<UserVO, UserVO> compositeItemProcessor() {
        CompositeItemProcessor<UserVO, UserVO> processor = new CompositeItemProcessor<>();
        // 将自定义多个处理器组合使用
        List<ItemProcessor<UserVO, UserVO>> processors = Arrays.asList(dataFilterItemProcessor, dataTransformItemProcessor);
        // 代理两个processor
        processor.setDelegates(processors);
        return processor;
    }
}
