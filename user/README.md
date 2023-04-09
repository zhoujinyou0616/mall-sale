<h1>用户系统</h1>

## 技术栈

- Mysql
- Redis
- RabbitMQ
- Guava
- Sharding JDBC

## 启动命令：

`-Xms7g -Xmx7g -Xmn2g -Xss256K -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:MaxTenuringThreshold=6 -XX:+ExplicitGCInvokesConcurrent -XX:+ParallelRefProcEnabled -XX:CMSFullGCsBeforeCompaction=10`