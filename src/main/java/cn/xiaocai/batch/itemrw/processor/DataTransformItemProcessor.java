package cn.xiaocai.batch.itemrw.processor;

import cn.xiaocai.batch.VO.UserVO;
import org.springframework.batch.item.ItemProcessor;

/** 实现接口方式自定义数据过滤的Processor
 * 功能：数据过滤
 * @author Xiaocai.Zhang
 */
public class DataTransformItemProcessor implements ItemProcessor<UserVO, UserVO> {
    @Override
    public UserVO process(UserVO userVO) throws Exception {
        // 在这里可以进行一些数据拼接、转换等处理操作
        userVO.setName("Test:"+userVO.getName());
        return userVO;
    }
}
