package cn.xiaocai.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
/**
 * 自定义 Skip 执行 的监听器
 *
 * @author Xiaocai.Zhang
 */
@Slf4j
public class MySkipListener implements SkipListener<String, String> {
    @Override
    public void onSkipInRead(Throwable throwable) {
        log.info("在读取数据的时候遇到异常并跳过，异常：" + throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(String s, Throwable throwable) {
        log.info("在回写数据的时候遇到异常并跳过，异常：" + throwable.getMessage());
    }

    @Override
    public void onSkipInProcess(String s, Throwable throwable) {
        log.info("在处理数据的时候遇到异常并跳过，异常：" + throwable.getMessage());
    }
}
