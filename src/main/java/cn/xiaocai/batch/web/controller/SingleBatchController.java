package cn.xiaocai.batch.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * single 模式 web调用入口
 * @author Xiaocai.Zhang
 */
@AllArgsConstructor
@RestController
@RequestMapping("/job")
@Api(tags = "job-batch", produces = "提供批量处理相关的 Rest API")
public class SingleBatchController {

    private final Job singleStepJobDemo;

    private final Job multiStepJob ;

    private final Job multiStepWithStatusJob;

    private final JobLauncher jobLauncher;

    @ApiOperation("不变参数重复调用测试接口")
    @GetMapping("/single/noParameters")
    public String noParameters() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addString("testKey","testValue")
                .toJobParameters();
        jobLauncher.run(singleStepJobDemo, jobParameters);
        return "The noParameters job is proceed.";
    }

    @ApiOperation("单个Step测试接口")
    @GetMapping("/single/singleStep")
    public String singleStep() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(singleStepJobDemo, jobParameters);
        return "The single step job is proceed.";
    }

    @ApiOperation("多个Step测试接口")
    @GetMapping("/single/multiStep")
    public String multiStepJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(multiStepJob, jobParameters);
        return "The multi bound steps job is proceed.";
    }

    @ApiOperation("带状态条件的多个Step测试接口")
    @GetMapping("/single/multiStepWithJob")
    public String multiStepWithJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(multiStepWithStatusJob, jobParameters);
        return "The multi Step With status job is proceed.";
    }


}
