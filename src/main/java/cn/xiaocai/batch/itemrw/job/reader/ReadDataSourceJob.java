package cn.xiaocai.batch.itemrw.job.reader;

import cn.xiaocai.batch.VO.UserVO;
import cn.xiaocai.batch.itemrw.reader.AllReaderDemo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;

/**
 * 读取数据源数据示例
 * @author Xiaocai.Zhang
 */
@AllArgsConstructor
public class ReadDataSourceJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final AllReaderDemo readerDemo;

    //@Bean
    public Job dataSourceItemReaderJob() throws Exception {
        return jobBuilderFactory.get("dataSourceItemReaderJob")
                .start(step())
                .build();
    }

    private Step step() throws Exception {
        return stepBuilderFactory.get("read_datasource_step")
                .<UserVO, UserVO>chunk(2)
                .reader(readerDemo.dataSourceItemReader())
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
