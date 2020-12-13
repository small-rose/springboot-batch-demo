package cn.xiaocai.batch.itemrw.job.processor;

import cn.xiaocai.batch.VO.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 内置的 BeanValidatingItemProcessor 验证处理器 使用示例
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class BeanValidatingItemProcessorJobDemo {
 
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    
    private ListItemReader<UserVO> simpleReader;

    //@Bean
    public Job beanValidatingItemProcessorJob() throws Exception {
        return jobBuilderFactory.get("beanValidatingItemProcessorJob")
                .start(step())
                .build();
    }

    private Step step() throws Exception {
        return stepBuilderFactory.get("step")
                .<UserVO, UserVO>chunk(2)
                .reader(simpleReader)
                .processor(beanValidatingItemProcessor())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    /**
     *  内置的验证处理器  BeanValidatingItemProcessor
     * @return
     * @throws Exception
     */
    private BeanValidatingItemProcessor<UserVO> beanValidatingItemProcessor() throws Exception {
        BeanValidatingItemProcessor<UserVO> beanValidatingItemProcessor = new BeanValidatingItemProcessor<>();

        // 开启过滤，不符合规则的数据被过滤掉；
        beanValidatingItemProcessor.setFilter(true);
        beanValidatingItemProcessor.afterPropertiesSet();
        return beanValidatingItemProcessor;
    }
}
