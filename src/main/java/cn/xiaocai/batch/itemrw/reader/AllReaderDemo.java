package cn.xiaocai.batch.itemrw.reader;

import cn.xiaocai.batch.VO.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 这个是 ItemReader 相关示例，调用示例在 cn.xiaocai.batch.itemrw.job.write 包路径下
 * 参考工程：https://github.com/small-rose/SpringAll/tree/master/69.spring-batch-itemreader
 * @author Xiaocai.Zhang
 */
@Component
@AllArgsConstructor
public class AllReaderDemo {

    private final DataSource dataSource ;

    /**
     * 从数据库读取数据
     * @return
     * @throws Exception
     */
    public ItemReader<UserVO> dataSourceItemReader() throws Exception {
        JdbcPagingItemReader<UserVO> reader = new JdbcPagingItemReader<>();
        // 设置数据源
        reader.setDataSource(dataSource);
        // 每次取多少条记录
        reader.setFetchSize(5);
        // 设置每页数据量
        reader.setPageSize(5);

        // 指定sql查询语句 select id,name,age,gender from user
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setSelectClause("id,name,age,gender"); //设置查询字段
        provider.setFromClause("from user"); // 设置从哪张表查询

        // 将读取到的数据转换为UserVO对象
        reader.setRowMapper((resultSet, rowNum) -> {
            UserVO data = new UserVO();
            data.setId(resultSet.getString(1));
            data.setName(resultSet.getString(2)); // 读取第一个字段，类型为String
            data.setAge(resultSet.getInt(3));
            data.setGender(resultSet.getString(4));
            return data;
        });

        Map<String, Order> sort = new HashMap<>(1);
        sort.put("id", Order.ASCENDING);
        provider.setSortKeys(sort); // 设置排序,通过id 升序

        reader.setQueryProvider(provider);

        // 设置namedParameterJdbcTemplate等属性
        reader.afterPropertiesSet();
        return reader;
    }

    /**
     * 从文件读取数据
     * @return
     */
    public ItemReader<UserVO> fileItemReader() {
        FlatFileItemReader<UserVO> reader = new FlatFileItemReader<>();
        // 设置文件资源地址
        reader.setResource(new ClassPathResource("file"));
        // 忽略第一行
        reader.setLinesToSkip(1);

        // AbstractLineTokenizer的三个实现类之一，以固定分隔符处理行数据读取,
        // 使用默认构造器的时候，使用逗号作为分隔符，也可以通过有参构造器来指定分隔符
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        // 设置属性名，类似于表头
        tokenizer.setNames("id", "name", "age", "gender");
        // 将每行数据转换为UserVO对象
        DefaultLineMapper<UserVO> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        // 设置映射方式
        mapper.setFieldSetMapper(fieldSet -> {
            UserVO data = new UserVO();
            data.setId(fieldSet.readString("id"));
            data.setName(fieldSet.readString("name"));
            data.setAge(fieldSet.readInt("age"));
            data.setGender(fieldSet.readString("gender"));
            return data;
        });

        reader.setLineMapper(mapper);
        return reader;
    }

    /**
     *  读取json文件
     * @return
     */
    public ItemReader<UserVO> jsonItemReader() {
        // 设置json文件地址
        ClassPathResource resource = new ClassPathResource("file.json");
        // 设置json文件转换的目标对象类型
        JacksonJsonObjectReader<UserVO> jacksonJsonObjectReader = new JacksonJsonObjectReader<>(UserVO.class);
        JsonItemReader<UserVO> reader = new JsonItemReader<>(resource, jacksonJsonObjectReader);
        // 给reader设置一个别名
        reader.setName("UserVOJsonItemReader");
        return reader;
    }


    /**
     * 多个文件
     * @return
     */
    public ItemReader<UserVO> multiFileItemReader() {
        MultiResourceItemReader<UserVO> reader = new MultiResourceItemReader<>();
        // 设置文件读取代理，方法可以使用上面文件读取中的例子，要读各种文件均可
        reader.setDelegate(fileItemReaderForMulti());

        Resource[] resources = new Resource[]{
                new ClassPathResource("file1"),
                new ClassPathResource("file2")
        };
        // 设置多文件源
        reader.setResources(resources);
        return reader;
    }
    private FlatFileItemReader<UserVO> fileItemReaderForMulti() {
        FlatFileItemReader<UserVO> reader = new FlatFileItemReader<>();
        // 读文件的时候忽略第一行
        reader.setLinesToSkip(1);

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        // 设置属姓名，类似于表头
        tokenizer.setNames("id", "name", "age", "gender");
        // 将每行数据转换为UserVO对象
        DefaultLineMapper<UserVO> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        // 设置映射方式
        mapper.setFieldSetMapper(fieldSet -> {
            UserVO data = new UserVO();
            data.setId(fieldSet.readString("id"));
            data.setName(fieldSet.readString("name"));
            data.setAge(fieldSet.readInt("age"));
            data.setGender(fieldSet.readString("gender"));
            return data;
        });

        reader.setLineMapper(mapper);
        return reader;
    }

    /**
     * 读取XML 文件
     * @return
     */
    public ItemReader<UserVO> xmlFileItemReader() {
        StaxEventItemReader<UserVO> reader = new StaxEventItemReader<>();
        // 设置xml文件源
        reader.setResource(new FileSystemResource("e:/springboot-batch/user.xml"));
        //reader.setResource(new ClassPathResource("file.xml"));

        // 指定xml文件的根标签
        reader.setFragmentRootElementName("userList");
        // 将xml数据转换为UserVO对象
        XStreamMarshaller marshaller = new XStreamMarshaller();
        // 指定需要转换的目标数据类型
        Map<String, Class<UserVO>> map = new HashMap<>(1);
        map.put("user", UserVO.class);
        marshaller.setAliases(map);

        reader.setUnmarshaller(marshaller);
        return reader;
    }

    /**
     * 指定自定义读取方式
     * @return
     */
    public ItemReader<String> mySimpleDemoItemReader() {
        List<String> data = Arrays.asList("java", "c++", "javascript", "python");
        return new MyDemoItemReader(data);
    }
}
