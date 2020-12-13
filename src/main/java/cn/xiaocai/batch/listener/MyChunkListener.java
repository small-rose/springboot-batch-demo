package cn.xiaocai.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;

import java.util.Map;

/**
 * 自定义 chunk 的监听器
 * @author Xiaocai.Zhang
 */
@Slf4j
public class MyChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        // 可以获取 Step 执行上下文： stepContext
        StepContext stepContext = chunkContext.getStepContext();
        chunkContext.getStepContext();

        // 可以获取 Step 执行上下文 ：stepExecContext
        Map<String, Object> stepExecContext = stepContext.getStepExecutionContext();
        // 可以获取 Job执行上下文 ：jobContext
        Map<String, Object> jobExeContext = stepContext.getJobExecutionContext();

        log.info("before chunk: " + stepContext.getStepName());
     }

    @Override
    public void afterChunk(ChunkContext chunkContext) {

        log.info("after chunk : " + chunkContext.getStepContext().getStepName());
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        log.info("after chunk error: " + chunkContext.getStepContext().getStepName());
    }
}
