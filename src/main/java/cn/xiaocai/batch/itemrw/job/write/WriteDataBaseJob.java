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
 * 将数据输出（写入）到 数据源 的示例
 * @author Xiaocai.Zhang
 */
@Component
@AllArgsConstructor
public class WriteDataBaseJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ListItemReader<UserVO> simpleReader;

    private final AllWriterDemo writerDemo;

    @Bean
    public Job writeDatasourceDemo() throws Exception {

        return jobBuilderFactory.get("writeDatasourceDemo")
                .start(writeDatasourceStep())
                .build();
    }

    private Step writeDatasourceStep() throws Exception {
        return stepBuilderFactory.get("writeDatasourceStep")
                .<UserVO, UserVO>chunk(2)
                .reader(simpleReader)
                .writer(writerDemo.dataSourceItemWriter())
                .build();
    }
}
