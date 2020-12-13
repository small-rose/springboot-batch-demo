package cn.xiaocai.batch.itemrw.job;

import cn.xiaocai.batch.VO.UserVO;
import cn.xiaocai.batch.itemrw.processor.CsvItemProcessor;
import cn.xiaocai.batch.listener.MyJobExecutionListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import org.springframework.batch.item.validator.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileNotFoundException;

/**
 * 一个完整读写的例子
 * @author Xiaocai.Zhang
 */
@Slf4j
@Component
@AllArgsConstructor
public class ItemRwJobDemo {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvItemProcessor csvBeanValidator;
    private final MyJobExecutionListener myJobExecutionListener;

    private final DataSource dataSource;

    /**
     *  以Bean的形式放入ioc，工程启动就会执行，仅demo使用
     * @return
     */
    @Bean
    public Job rwDemoJob() throws Exception {

        return jobBuilderFactory.get("rwDemoJob")
                .start(rwStep())
                .build();
    }

    /**
     *  这里是把读写处理都放一个步骤
     * @return
     */
    private Step rwStep() throws Exception {
        return stepBuilderFactory.get("step")
                .listener(myJobExecutionListener)
                .<UserVO,UserVO>chunk(2)
                .reader(fileReader())
                .processor(processor())
                .writer(dataSourceItemWriter())
                .faultTolerant()
                .skipLimit(1)
                .skip(Exception.class)
                .noSkip(FileNotFoundException.class)
                .build();
    }

    /**
     * 读取数据
     * @return
     */
    public ItemReader<UserVO> fileReader(){
        // 使用FlatFileItemReader去读cvs文件，一行即一条数据
        FlatFileItemReader<UserVO> reader = new FlatFileItemReader<>();
        // 设置文件处在路径
        reader.setResource(new ClassPathResource("user.csv"));
        // entity与csv数据做映射
        reader.setLineMapper(new DefaultLineMapper<UserVO>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        // 列映射
                        setNames(new String[]{"id", "name", "age", "gender"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<UserVO>() {
                    {
                        setTargetType(UserVO.class);
                    }
                });
            }
        });
        return reader;
    }

    /**
     *  处理过程
     * @return
     */
    private ItemProcessor<UserVO, UserVO> processor(){
        CsvItemProcessor processor = new CsvItemProcessor();
        processor.setValidator((Validator<UserVO>) csvBeanValidator);
        return processor;
    }

    /**
     * 写入数据库,需要自己建表
     * @return
     */
    private ItemWriter<UserVO> dataSourceItemWriter() {
        // 写入数据库
        JdbcBatchItemWriter<UserVO> writer = new JdbcBatchItemWriter<>();
        // 设置写入的数据源
        writer.setDataSource(dataSource);

        String sql = "insert into USER(id,name,age,gender) values (:id,:name,:age,:gender)";
        // 设置插入sql脚本
        writer.setSql(sql);

        // 映射UserVO对象属性到占位符中的属性
        BeanPropertyItemSqlParameterSourceProvider<UserVO> provider = new BeanPropertyItemSqlParameterSourceProvider<>();
        writer.setItemSqlParameterSourceProvider(provider);

        writer.afterPropertiesSet(); // 设置一些额外属性
        return writer;
    }


}
