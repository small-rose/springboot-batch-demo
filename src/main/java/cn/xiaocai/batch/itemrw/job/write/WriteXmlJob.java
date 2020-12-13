package cn.xiaocai.batch.itemrw.job.write;

import cn.xiaocai.batch.VO.UserVO;
import cn.xiaocai.batch.itemrw.writer.AllWriterDemo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
/**
 * 将数据输出（写入）到 XML文件 的示例
 * @author Xiaocai.Zhang
 */
@Component
@AllArgsConstructor
public class WriteXmlJob {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ListItemReader<UserVO> simpleReader;

    private final AllWriterDemo writerDemo;

    @Bean
    public Job writeXmlFileDemo() throws Exception {

        return jobBuilderFactory.get("writeXmlFileDemo")
                .start(writeXmlFileStep())
                .build();
    }

    private Step writeXmlFileStep() throws Exception {
        return stepBuilderFactory.get("writeXmlFileStep")
                .<UserVO, UserVO>chunk(2)
                .reader(simpleReader)
                .writer(writerDemo.xmlFileItemWriter())
                .build();
    }
}
