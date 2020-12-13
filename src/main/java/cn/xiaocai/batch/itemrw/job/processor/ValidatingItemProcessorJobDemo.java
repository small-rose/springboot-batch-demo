package cn.xiaocai.batch.itemrw.job.processor;

import cn.xiaocai.batch.VO.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Component;

/**
 * 内置的 ValidatingItemProcessor 验证处理器 使用示例
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class ValidatingItemProcessorJobDemo {


    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private ListItemReader<UserVO> simpleReader;

    //@Bean
    public Job validatingItemProcessorJob() {
        return jobBuilderFactory.get("validatingItemProcessorJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<UserVO, UserVO>chunk(2)
                .reader(simpleReader)
                .processor(validatingItemProcessor())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }

    private ValidatingItemProcessor<UserVO> validatingItemProcessor() {
        ValidatingItemProcessor<UserVO> processor = new ValidatingItemProcessor<>();
        processor.setValidator(value -> {
            // 对每一条数据进行校验
            if ("".equals(value.getName())) {
                // 如果名字的值为空串，则抛异常
                throw new ValidationException("用户名称的值不能为空");
            }
        });
        return processor;
    }
 
}
