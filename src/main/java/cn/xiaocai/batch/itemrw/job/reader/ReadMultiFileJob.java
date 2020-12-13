package cn.xiaocai.batch.itemrw.job.reader;

import cn.xiaocai.batch.VO.UserVO;
import cn.xiaocai.batch.itemrw.reader.AllReaderDemo;
import lombok.AllArgsConstructor;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.stereotype.Component;
/**
 * 读取 多个文件 的示例
 * @author Xiaocai.Zhang
 */
@Component
@AllArgsConstructor
public class ReadMultiFileJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final AllReaderDemo readerDemo;

    //@Bean
    public Job multiFileItemReaderJob() {
        return jobBuilderFactory.get("multiFileItemReaderJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<UserVO, UserVO>chunk(2)
                .reader(readerDemo.multiFileItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
