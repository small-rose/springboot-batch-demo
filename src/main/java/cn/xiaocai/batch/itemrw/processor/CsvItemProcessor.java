package cn.xiaocai.batch.itemrw.processor;

import cn.xiaocai.batch.VO.UserVO;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Component;

/**
 * 继承的方式实现自定义 Processor
 * @author Xiaocai.Zhang
 */
@Component
public class CsvItemProcessor extends ValidatingItemProcessor<UserVO> {

    @Override
    public UserVO process(UserVO item) throws ValidationException {
        // 需执行super.proces:(item)才会调用自定义校验器。
        super.process(item);
        // 对数据做简单的处理，若性别为男，则数据转换成1，其余转换成2.
        if(item.getGender().equals("男")) {
            item.setGender("M");
        }else {
            item.setGender("W");
        }
        return item;
    }
}
