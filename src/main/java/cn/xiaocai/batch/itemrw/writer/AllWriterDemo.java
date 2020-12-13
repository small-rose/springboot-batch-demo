package cn.xiaocai.batch.itemrw.writer;

import cn.xiaocai.batch.VO.UserVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;

import org.springframework.classify.Classifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个是 ItemWriter 相关示例，调用示例在 cn.xiaocai.batch.itemrw.job.reader 包路径下
 * 参考工程：https://github.com/small-rose/SpringAll/tree/master/69.spring-batch-itemwriter
 * @author Xiaocai.Zhang
 */
@Component
@AllArgsConstructor
public class AllWriterDemo {

    private final DataSource dataSource;

    /**
     *  使用数据源写入数据库
     * @return
     */
    public ItemWriter<UserVO> dataSourceItemWriter() {
        // ItemWriter的实现类之一，mysql数据库数据写入使用JdbcBatchItemWriter，
        // 其他实现：MongoItemWriter,Neo4jItemWriter等
        JdbcBatchItemWriter<UserVO> writer = new JdbcBatchItemWriter<>();
        // 设置数据源
        writer.setDataSource(dataSource);

        String sql = "insert into USER(id,name,age,gender) values (:id,:name,:age,:gender)";
        // 设置插入sql脚本
        writer.setSql(sql);

        // 映射TestData对象属性到占位符中的属性
        BeanPropertyItemSqlParameterSourceProvider<UserVO> provider = new BeanPropertyItemSqlParameterSourceProvider<>();
        writer.setItemSqlParameterSourceProvider(provider);

        writer.afterPropertiesSet(); // 设置一些额外属性
        return writer;
    }

    /**
     *  写文件，写成字符串
     * @return
     * @throws Exception
     */
    public FlatFileItemWriter<UserVO> fileItemWriter() throws Exception {
        FlatFileItemWriter<UserVO> writer = new FlatFileItemWriter<>();

        FileSystemResource file = new FileSystemResource("E:/spring-batch/user_info");
        Path path = Paths.get(file.getPath());
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        writer.setResource(file); // 设置目标文件路径

        // 把读到的每个UserVO对象转换为JSON字符串
        LineAggregator<UserVO> aggregator = item -> {
            try {
                // 这里可以自定义拼接规则
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(item);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return "";
        };

        writer.setLineAggregator(aggregator);
        writer.afterPropertiesSet();
        return writer;
    }

    /**
     * 写Json 文件
     * @return
     * @throws IOException
     */
    public JsonFileItemWriter<UserVO> jsonFileItemWriter() throws IOException {
        // 文件输出目标地址
        FileSystemResource file = new FileSystemResource("E:/spring-batch/user_file.json");
        Path path = Paths.get(file.getPath());
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        // 将对象转换为json
        JacksonJsonObjectMarshaller<UserVO> marshaller = new JacksonJsonObjectMarshaller<>();
        JsonFileItemWriter<UserVO> writer = new JsonFileItemWriter<>(file, marshaller);
        // 设置别名
        writer.setName("UserVOJasonFileItemWriter");
        return writer;
    }

    /**
     * 写多个文件对应的writer
     */
    private final ItemStreamWriter<UserVO> fileItemWriter;
    private final ItemStreamWriter<UserVO> xmlFileItemWriter;

    // 直接输出数据到多个文件
    public CompositeItemWriter<UserVO> multiFileItemWriter() {
        // 使用CompositeItemWriter代理
        CompositeItemWriter<UserVO> writer = new CompositeItemWriter<>();
        // 设置具体写代理，其实我们要使用的各种writer
        writer.setDelegates(Arrays.asList(fileItemWriter, xmlFileItemWriter));
        return writer;
    }

    // 将数据分类，然后分别输出到对应的文件(此时需要将writer注册到ioc容器，否则报
    // WriterNotOpenException: Writer must be open before it can be written to)
    public ClassifierCompositeItemWriter<UserVO> classifierMultiFileItemWriter() {
        ClassifierCompositeItemWriter<UserVO> writer = new ClassifierCompositeItemWriter<>();
        writer.setClassifier((Classifier<UserVO, ItemWriter<? super UserVO>>) testData -> {
            try {
                // id能被2整除则输出到普通文本，否则输出到xml文本
                return testData.getAge() > 30  ? fileItemWriter : xmlFileItemWriter;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        return writer;
    }



    /**
     *  写入xml  文件
     * @return
     * @throws IOException
     */
    public StaxEventItemWriter<UserVO> xmlFileItemWriter() throws IOException {
        StaxEventItemWriter<UserVO> writer = new StaxEventItemWriter<>();

        // 通过XStreamMarshaller将TestData转换为xml
        XStreamMarshaller marshaller = new XStreamMarshaller();

        Map<String,Class<UserVO>> map = new HashMap<>(1);
        map.put("user", UserVO.class);

        marshaller.setAliases(map); // 设置xml标签

        writer.setRootTagName("userList"); // 设置根标签
        writer.setMarshaller(marshaller);

        FileSystemResource file = new FileSystemResource("E:/spring-batch/user.xml");
        Path path = Paths.get(file.getPath());
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        writer.setResource(file); // 设置目标文件路径
        return writer;
    }
}
