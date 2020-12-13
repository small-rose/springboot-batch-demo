package cn.xiaocai.batch.itemrw.job.reader;

import cn.xiaocai.batch.itemrw.reader.AllReaderDemo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.stereotype.Component;
/**
 * 读取 自定义ItemReader 的示例
 * @author Xiaocai.Zhang
 */
@Component
@AllArgsConstructor
public class ReadSimpleDemoJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final AllReaderDemo readerDemo;

    //@Bean
    public Job mySimpleItemReaderJob() {
        return jobBuilderFactory.get("mySimpleItemReaderJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<String, String>chunk(2)
                .reader(readerDemo.mySimpleDemoItemReader())
                .writer(list -> list.forEach(System.out::println))  // 简单输出，后面再详细介绍writer
                .build();
    }



}
