## springboot-batch-demo

集成spring batch 框架使用的各种DEMO

对应的[spring batch 博客文章](https://zhangxiaocai.cn/posts/7e9b1e27.html)

### 工程代码保函示例：

#### ItemReader 相关示例

- 读数据库数据的Job示例
- 读文件数据的Job示例
- 读多个文件数据的Job示例
- 读XML文件数据的Job示例
- 读Json文件数据的Job示例
- 按自定义规则读取数据的Job示例

#### Processor 处理器相关示例

- 现接口方式自定义数据过滤的Processor
- 继承的方式实现自定义的Processor

#### ItemWriter 相关示例

- 写入数据到数据库的Job示例
- 写入数据到文件的Job示例
- 写入数据到Json文件的Job示例
- 写入数据到多个文件的Job示例
- 写入数据到XML文件的Job示例

#### Job 相关示例

- 单个Step的Job示例
- 多个Step的Job示例
- 带状态判断的多个Step的Job示例
- 带决策器的Job示例
- 带监听器的Job示例
- 使用Flow组装Step的Job示例
- 并行执行Job使用的Job示例
- 嵌套执行Job使用的Job示例
- 配合Scheduled调度器启动Job的示例
- Restful接口调用启动Job的示例


#### 参考工程：

- [spring-batch-itemreader](https://github.com/small-rose/SpringAll/tree/master/68.spring-batch-itemreader)
- [spring-batch-itemwriter](https://github.com/small-rose/SpringAll/tree/master/69.spring-batch-itemwriter)
- [spring-batch-itemprocessor](https://github.com/small-rose/SpringAll/tree/master/70.spring-batch-itemprocessor)
- [spring-batch-listener](https://github.com/small-rose/SpringAll/tree/master/71.spring-batch-listener)
- [spring-batch-exception](https://github.com/small-rose/SpringAll/tree/master/72.spring-batch-exception)
