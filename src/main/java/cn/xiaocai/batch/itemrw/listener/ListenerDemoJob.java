package cn.xiaocai.batch.itemrw.listener;

import cn.xiaocai.batch.itemrw.reader.MyDemoItemReader;
import cn.xiaocai.batch.listener.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义 监听器的 使用示例
 * @author Xiaocai.Zhang
 */
@Slf4j
public class ListenerDemoJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private MyJobExecutionListener myJobExecutionListener;
    @Autowired
    private MyStepExecutionListener myStepExecutionListener;
    @Autowired
    private MyChunkListener myChunkListener;
    @Autowired
    private MyItemReaderListener myItemReaderListener;
    @Autowired
    private MyItemProcessorListener myItemProcessorListener;
    @Autowired
    private MyItemWriterListener myItemWriterListener;

    @Bean
    public Job listenerTestJob() {
        return jobBuilderFactory.get("listenerTestJob")
                .start(step())
                .listener(myJobExecutionListener)
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .listener(myStepExecutionListener)
                .<String, String>chunk(2)
                .faultTolerant()
                .listener(myChunkListener)
                .reader(reader())
                .listener(myItemReaderListener)
                .processor(processor())
                .listener(myItemProcessorListener)
                .writer(list -> list.forEach(System.out::println))
                .listener(myItemWriterListener)
                .build();
    }

    /**
     * 模拟使用自定义的reader
     * @return
     */
    private ItemReader<String> reader() {
        List<String> data = Arrays.asList("java", "c++", "javascript", "python");
        return new MyDemoItemReader(data);
    }

    /**
     * 定义临时的处理器 processor
     * @return
     */
    private ItemProcessor<String, String> processor() {
        return item -> item + " language";
    }
}
