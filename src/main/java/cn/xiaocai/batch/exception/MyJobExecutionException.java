package cn.xiaocai.batch.exception;

/**
 * @author Xiaocai.Zhang
 */
public class MyJobExecutionException extends  RuntimeException{

    private static final long serialVersionUID = 7168487913507656106L;

    public MyJobExecutionException(String message) {
        super(message);
    }
}
