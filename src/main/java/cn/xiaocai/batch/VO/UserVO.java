package cn.xiaocai.batch.VO;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * @author Xiaocai.Zhang
 */
@ToString
@Data
public class UserVO {

    private String id ;
    private String name ;

    @Size(max=100, min=18)
    private Integer age;
    private String gender ;
}
