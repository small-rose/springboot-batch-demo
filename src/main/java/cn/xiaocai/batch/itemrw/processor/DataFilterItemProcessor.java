package cn.xiaocai.batch.itemrw.processor;

import cn.xiaocai.batch.VO.UserVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

/** 实现接口方式自定义数据过滤的Processor
 * 功能：数据过滤
 * @author Xiaocai.Zhang
 */
public class DataFilterItemProcessor implements ItemProcessor<UserVO, UserVO> {

    @Override
    public UserVO process(UserVO userVO) throws Exception {
        // 返回null，会过滤掉这条数据,条件是ID为空或者
        return ( !StringUtils.hasLength(userVO.getId()) || !StringUtils.hasLength(userVO.getName())) ? null : userVO;
    }
}
