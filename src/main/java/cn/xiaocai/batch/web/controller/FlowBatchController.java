package cn.xiaocai.batch.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Flow 模式 web调用入口
 * @author Xiaocai.Zhang
 */
@RestController
@RequestMapping("/job")
@AllArgsConstructor
@Api(tags = "job-batch-flow", produces = "提供组装任务批量处理相关的 Rest API")
public class FlowBatchController {

    private final Job flowJob;

    private final JobLauncher jobLauncher;

    @ApiOperation("flow组装任务批量处理")
    @GetMapping("/flowJob")
    public String singleStep() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(flowJob, jobParameters);
        return "The flow job is proceed.";
    }
}
