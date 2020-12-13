package cn.xiaocai.batch.itemrw.job.reader;

import cn.xiaocai.batch.VO.UserVO;
import cn.xiaocai.batch.itemrw.reader.AllReaderDemo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.stereotype.Component;
/**
 * 读取 XMl文件 的示例
 * @author Xiaocai.Zhang
 */
@Component
@AllArgsConstructor
public class ReadXmlJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final AllReaderDemo readerDemo;

    public Job xmlFileItemReaderJob() {
        return jobBuilderFactory.get("xmlFileItemReaderJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<UserVO, UserVO>chunk(2)
                .reader(readerDemo.xmlFileItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
