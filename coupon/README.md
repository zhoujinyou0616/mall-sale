<h1>优惠券系统</h1>
接口文档地址：http://127.0.0.1:8081/coupon/doc.html

## 简历项目描述

- 1、使用多级缓存提高Redis性能，券模版使用Guava缓存，用户券列表使用Redis的List缓存
-     （节省Redis服务器成本，提高性能）
- 2、分布式锁Redisson实际使用经验
-     （unlock释放锁的注意事项）
- 3、使用Sharding进行用户券分表，解决用户券大数据量下的存储、性能等问题，Mysql索引优化经验，分页查询
-     （存储问题、key表冲突问题）
- 4、使用Redis+Lua脚本的方式保障抢券时券库存超卖/超抢的问题，券核销原子性问题等
-     （redis+lua解决了redis命令原子性问题）
- 5、使用Redis的expire实现券过期，计算一下当前时间和券过期时间的时间差，使用expire设置。
-      (引申到Redis的过期策略，实现原理，如何保障大量券同时过期对性能的影响？
-      （主动）1、每100ms扫描一小部分过期Key进行删除； 
-      （被动/懒加载）2、调用get方法时，会判断如果Key过期，直接删除)
- 6、熟悉双写上线方案，使用nacos config 实时更新配置信息，结合对比、验证、修复等保障上线数据稳定正确。
- 7、主动提出使用xxjob定时任务进行券清理，清理已过期（一周、一个月、三个月）的优惠券信息。
-      （这里一定要说是 *技术需求*，是技术自主驱动的，体现你对系统的责任心，onwer意识。
-        因为产品不会想到要不要清理废弃数据）
- 8、todo：压测结果：Guava能达到 12000 QPS,Redis能达到 4000 QPS; Mysql的性能 800？
- 9、todo：数据同步问题：
- 1、MQ异步处理，保障消息不丢失就行。（好处：可控、可重复消费、自定义）
- 2、canel数据同步工具，Mysql的数据同步到ES,同步到其他存储中。（好处：简便开发）
- 10、不仅仅是可以使用Redis、也可以针对不同场景下使用ES、MongoDB等等。
- 11、threadPool的实际使用案例、分表数据迁移案例、线程池调优
- 12、有从0到1的上线经验、有压测调优的经验。JVM调优、启动参数等等

## 功能

- 券分表
- 领券中心
- 抢券
- 券核销
- 券过期
- 双写上线
- 券清理

## 技术栈

- Mysql
- Redis
- Guava
- Sharding JDBC
- RabbitMQ
- threadPool

## 启动命令：

```
-Xms4g -Xmx4g -Xmn1g -Xss256K -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:MaxTenuringThreshold=6 -XX:+ExplicitGCInvokesConcurrent -XX:+ParallelRefProcEnabled -XX:CMSFullGCsBeforeCompaction=10
```


