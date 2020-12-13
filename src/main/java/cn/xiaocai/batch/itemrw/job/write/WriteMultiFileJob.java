package cn.xiaocai.batch.itemrw.job.write;

import cn.xiaocai.batch.VO.UserVO;
import cn.xiaocai.batch.itemrw.writer.AllWriterDemo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
/**
 * 将数据输出（写入）到 多个文件 的示例
 * @author Xiaocai.Zhang
 */
@Component
@AllArgsConstructor
public class WriteMultiFileJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ListItemReader<UserVO> simpleReader;

    private final AllWriterDemo writerDemo;


    private final ItemStreamWriter<UserVO> xmlFileItemWriter;

    @Bean
    public Job writeMultiFileDemo() throws Exception {

        return jobBuilderFactory.get("writeMultiFileDemo")
                .start(writeMultiFileStep())
                .build();
    }

    private Step writeMultiFileStep() throws Exception {
        return stepBuilderFactory.get("writeMultiFileStep")
                .<UserVO, UserVO>chunk(2)
                .reader(simpleReader)

                //.writer(writerDemo.multiFileItemWriter()) // 直接写到不同文件
                .writer(writerDemo.classifierMultiFileItemWriter()) // 将数据分类，然后分别输出到对应的文件
                // 分别写入不同的文件
                .stream(writerDemo.fileItemWriter())
                .stream(writerDemo.xmlFileItemWriter())
                .build();
    }
}
