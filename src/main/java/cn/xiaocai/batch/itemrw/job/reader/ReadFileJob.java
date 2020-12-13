package cn.xiaocai.batch.itemrw.job.reader;

import cn.xiaocai.batch.VO.UserVO;
import cn.xiaocai.batch.itemrw.reader.AllReaderDemo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 读取 普通文件 的示例
 * @author Xiaocai.Zhang
 */
@AllArgsConstructor
@Component
public class ReadFileJob {
 
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final AllReaderDemo readerDemo;

    //@Bean
    public Job fileItemReaderJob() {
        return jobBuilderFactory.get("fileItemReaderJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<UserVO, UserVO>chunk(2)
                .reader(readerDemo.fileItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
