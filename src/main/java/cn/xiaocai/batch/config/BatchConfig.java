package cn.xiaocai.batch.config;

import cn.xiaocai.batch.listener.MyJobExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xiaocai.Zhang
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    /**
     * 注册监听器
     * @return
     */
    @Bean
    public MyJobExecutionListener myJobListener(){
        return new MyJobExecutionListener();
    }
}
