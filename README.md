# LsRpc

LsRpc的名字来源于本人的名字拼音Ls加上Rpc(Remote Procedure Calling)，是本人学习和开发的一个rpc通信框架，其设计参照于GRpc、Dubbo等，能够实现不同开发语言之间的过程调用。同时封装也是比较好的，对于业务开发而言，五分钟就能搭建起一个基础服务。



LsRpc的数据接口定义基于Google Protobuf实现。通过编写对应的Proto文件，生成对应语言的目标文件后，同时不同语言只需要处理好底层网络传输数据的编解码，就能实现跨语言通信。

LsRpc提供了一系列语言开发工具，供开发人员使用。比如，基于LsRpc-java实现的server端，可以被LsRpc-go实现的客户端调用。



由于本人能力和精力有限，目前只实现了LsRpc-java部分。后续本人将开发LsRpc-go。下面将主要开展LsRpc-java的介绍。

## LsRpc-java

#### 1、QuickStart

​	talk is cheap, show me the code!

​	快速开发一个Rpc-Provider和Rpc-Consumer应用，可以参考**lsRpc-server-demo**模块和**lsRpc-client-demo**模块。

##### 	1.1 编写Proto文件

​	首先我们编写一个echo.proto文件。如果你对Protobuf不太了解，可以参考Protobuf的相关学习教程：

[]: https://developers.google.cn/protocol-buffers/	" Protobuf学习"

```protobuf
syntax = "proto3";


message EchoReq {
        int32 id = 1;
        string str = 2;
        int32 opt = 3;
};


message EchoRsp {
        int32 id = 1;
        string desc = 2;
        int32 extra = 3;
};


service EchoService {
        rpc echo(EchoReq) returns (EchoRsp);
};
```

##### 1.2 proto生成对应语言的目标文件

这个需要下载proto组件,通过protoc指令生成对应语言的文件。

这里以java举例，通过执行命令：

```shell
java_out_dir=~/lsRpc/pb/java
pb_path=~/lsRpc/pb/echo.proto
protoc -Iprotos --java_out=${java_out_dir} --proto_path=${pb_path}
```

成功执行该命令后，我们可以看到java_out_dir中已经有了protoc生成的Echo.java文件。同理，对于其他语言的生成，以go语言举例，只需要**将--java_out改为--go_out**。

##### 1.3 设置注册中心

LsRpc-java的注册中心默认基于**Zookeeper**实现，因此只需要启动一个Zookeeper实例，并记录该节点的地址。加入在本地启动，那么注册chuangji中心Zookeeper的地址就是localhost:2181。

##### 1.4 启动Rpc-Provider

首先我们需要创建一个SpringBoot应用，具体Demo应用已经在模块**lsRpc-server-demo**中展示。

首先需要引入LsRpc-Provider的POM：

```xml
<!-- 引入lsRpc-server模块 -->
        <dependency>
            <groupId>org.ls.rpc</groupId>
            <artifactId>server</artifactId>
            <version>0.0.1</version>
        </dependency>
```

然后定义一个EchoService接口：

```java
public interface EchoService {
    // 这里的Echo就是protobuf生成的Echo类
    Echo.EchoRsp echo(Echo.EchoReq req);
}

```

然后定义一个EchoService的实现类：

```java
// Rpc实现类必须用@RpcProvider注解包裹，同时name属性写上对应的标识，注意：这里的标识必须与Rpc消费者
// 的标识一致
@RpcProvider(name = "org.test.echoService")
@Slf4j
public class EchoServiceImpl implements EchoService {
	
    // 接口方法添加上@RpcMethod注解，同时也要标注name标识，也要于Rpc消费者的标识一致
    @RpcMethod(name = "echo")
    @Override
    public Echo.EchoRsp echo(Echo.EchoReq req) {
        // 这里就是具体的逻辑实现了
        log.info("test rpc method echo for request : {}", req);
        Echo.EchoRsp rsp = Echo.EchoRsp.newBuilder()
                .setId(1).setDesc("ok from server :"+ NetUtils.getLocalIpv4()
                        + " with time :" + System.currentTimeMillis() / 1000L).setExtra(2).build();
        return rsp;
    }
}

```

然后编辑我们的application.yaml文件：

```yaml
# spring启动端口
server:
  port: 8013

# 这里就是lsRpc-java的全部配置了
ls:
  rpc:
    server:
      # lsRpc Provider启动的端口
      port: 8991
      env: "dev"
      services:
        #这里定义的services的name,需要与你的@RpcProvider的name保持一致
        - name: "org.test.echoService"
      registry:
        # 注册中心
        type: "zookeeper"
        # 这里需要改成你的注册中心地址
        address: "localhost:2181"
```

最后，直接启动SpringBoot项目，就完成了LsRpc-Provider的启动，流程非常简洁。

##### 1.5 启动Rpc-Consumer

首先我们需要创建一个SpringBoot应用，具体Demo应用已经在模块**lsRpc-client-demo**中展示。

首先需要引入LsRpc-Provider的POM：

```xml
        <!-- 引入lsRpc-java-client -->
        <dependency>
            <groupId>org.ls.rpc</groupId>
            <artifactId>client</artifactId>
            <version>0.0.1</version>
        </dependency>
```

然后定义一个EchoService接口：

```java
// 为该接口添加上@RpcConsumer注解，该注解的name标识需要与@RpcProvider保持一致
@RpcConsumer(name = "org.test.echoService")
public interface EchoService {
    // 方法添加上@RpcMethod注解，name标签也需要一致。还可以选择性配置timeout、loadBalance等
    @RpcMethod(name = "echo", timeout = 5000, loadBalance = LoadBalanceStrategy.ROUND_ROBIN, retryStrategy = RetryStrategy.FAIL_FAST)
    Echo.EchoRsp echo(Echo.EchoReq req);
}
```

之后，该接口**EchoService就可以像其他Spring组件一样进行依赖注入、使用等。没错，不需要实现类也可以使用。**

再者，在你的业务逻辑中使用该接口进行Rpc调用，举例如下：

```java
@Service
@Slf4j
public class TestServiceImpl implements TestService {

	
    // 注入EchoService
    @Resource
    private EchoService echoService;
    
    
    @Override
    @Scheduled(cron = "0/2 * * * * ? ")
    public void test() {
        log.info("start to execute rpc method echo");
        Echo.EchoReq echoReq =
                Echo.EchoReq.newBuilder().setId(1).setStr("echo helloworld").setOpt(4).build();
        // 与本地调用逻辑一样进行Rpc调用
        Echo.EchoRsp echoRsp = echoService.echo(echoReq);

        log.info("echo rsp is : {}", echoRsp);
    }
}

```

然后编辑我们的application.yaml文件：

```yaml
# spring启动端口
server:
  port: 8083

# 这里就是lsRpc-java-client的全部配置了
ls:
  rpc:
    client:
      # lsRpc Consumer启动的端口
      port: 8990
      env: "dev"
      services:
        #这里定义的services的name,需要与你的@RpcConsumer的name保持一致
        - name: "org.test.echoService"
          # 这里需要将@RpcConsumer的全类名写进去(用于反射加载、构造代理类)
          classPath: "com.example.client.service.service.rpc.EchoService"
      registry:
        # 注册中心
        type: "zookeeper"
        # 这里需要改成你的注册中心地址
        address: "localhost:2181"
```

最后一步，在SpringBoot的启动类中添加一个注解**@EnableLsRpcClient(非常重要！！！)**，就可以通过SpringBoot愉快地跑起来LsRpc-Consumer服务了。

```java
// 一定要添加EnableLsRpcClient注解
@EnableLsRpcClient
@EnableScheduling
@SpringBootApplication
public class LsRpcClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LsRpcClientDemoApplication.class, args);
    }

}
```

#### 2、技术构成

LsRpc-java结合了当前几种比较火热的技术方案。

##### 2.1 SpringBoot

当前Java主流后台框架普遍采用SpringBoot，SpringBoot由其开发上手的便捷性。因此LsRpc-java的平台依托于SpringBoot实现，对于一个新的SpringBoot应用，只需要引入LsRpc-java的POM文件，就拥有了RPC通信的能力。

##### 2.2 Zookeeper

LsRpc-java采用的注册中心基于Zookeeper实现。Rpc生产者/消费者启动时都会在Zookeeper中进行注册。下线时，再将对应的Zookeeper节点删除。同时基于Zookeeper Watch的机制，消费者能够实时同步生产者的信息。

##### 2.3 Netty

LsRpc-java底层基于Netty做的TCP通信。Netty采用同步非阻塞的IO模式，同时采用IO多路复用的方式，最大限度地提升了网络通讯的速度与资源节省。底层Rpc请求/响应数据都通过Netty进行编解码,并保证可靠传输。







