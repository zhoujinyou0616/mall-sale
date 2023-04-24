# mall-sale
## 项目介绍
该项目是一个电商业务微服务实现，使用Spring Cloud 相关技术实现。

## 本项目区别于市面上其他项目的独特亮点
**1、分表数据迁移实战，多线程迁移代码优化方案**

**2、不停机双写上线方案实战，使用网关路由到新服务对上游系统无感知上线**

**3、RocketMQ改造支持任意时间延时队列**

## 项目亮点
1.使用Spring Cloud Alibaba技术栈，包括Nacos、RocketMQ、openfeign、gateway等，实现了服务注册与发现、服务调用、服务网关等功能。

2.使用Sharding JDBC实现了分库分表，解决了大数据量下的存储、性能等问题，同时保证了数据的一致性。

3.使用Redis+Lua脚本的方式保障抢券时券库存超卖/超抢的问题，券核销原子性问题等，同时保证了数据的一致性。

4.使用RocketMQ实现了消息的异步处理，解决了业务与消息的解耦问题，同时保证了消息的可靠性，提高了系统的吞吐量，降低了系统的延迟，提高了系统的可用性，提高了系统的可扩展性。

...

## 项目结构
mall-sale

├── user -- 用户服务

├── coupon -- 优惠券服务

├── couponNew -- 新优惠券服务

├── gateway -- 网关gateway服务

...

## 涉及技术栈
- Spring Cloud Alibaba
- Spring Cloud Gateway
- Spring Cloud OpenFeign
- Redis
- Mysql
- Sharding JDBC
- RocketMQ
- Nacos

## 项目启动
- 启动nacos
- 启动mysql
- 启动redis
- 启动rocketmq
- 启动对应服务

