package cn.xiaocai.batch.itemrw.job.write;

import cn.xiaocai.batch.VO.UserVO;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * 为了演示 ItemWrite 进行的配置
 * @author Xiaocai.Zhang
 */
@Configuration
public class WriteConfig {
    /**
     *  测试各种 ItemWrite 用的模拟 Reader
     * @return
     */
    @Bean
    public ListItemReader<UserVO> simpleReader() {
        List<UserVO> data = new ArrayList<>();
        UserVO userVO01 = new UserVO();
        userVO01.setId("100021");
        userVO01.setName("Jack");
        userVO01.setAge(23);
        userVO01.setGender("男");
        data.add(userVO01);

        UserVO userVO02 = new UserVO();
        userVO02.setId("100022");
        userVO02.setName("Marry");
        userVO02.setAge(25);
        userVO02.setGender("女");
        data.add(userVO02);

        UserVO userVO03 = new UserVO();
        userVO03.setId("100023");
        userVO03.setName("Tom");
        userVO03.setAge(28);
        userVO03.setGender("男");
        data.add(userVO03);

        UserVO userVO04 = new UserVO();
        userVO04.setId("100022");
        userVO04.setName("Kate");
        userVO04.setAge(30);
        userVO04.setGender("女");
        data.add(userVO04);

        UserVO userVO05 = new UserVO();
        userVO05.setId("100022");
        userVO05.setName("Mike");
        userVO05.setAge(35);
        userVO05.setGender("男");
        data.add(userVO05);

        return new ListItemReader<>(data);
    }
}
