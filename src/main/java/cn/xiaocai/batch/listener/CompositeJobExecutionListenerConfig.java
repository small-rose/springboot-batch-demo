package cn.xiaocai.batch.listener;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 组合 Job执行的 监听器
 * 这里只是单独写了配置，也可以使用匿名方式注册,匿名方式参考 CompositeJobExecutionListenerJobDemo 的写法
 * @author Xiaocai.Zhang
 */
@Configuration
@AllArgsConstructor
public class CompositeJobExecutionListenerConfig {



    private final MyJobExecutionListener myJobExecutionListener1;
    private final MyJobExecutionListener myJobExecutionListener2;

    /**
     * Bean 的方式注册,然后全局使用
     */
    @Bean
    public CompositeJobExecutionListener compositeJobExecutionListener() {
        CompositeJobExecutionListener listener = new CompositeJobExecutionListener();
        listener.register(myJobExecutionListener1);
        listener.register(myJobExecutionListener2);
        return listener;
    }

}
