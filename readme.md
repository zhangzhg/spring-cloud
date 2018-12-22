## 本框架搭建时间为12月1日，所有的包都是最新的。技术为目前最主流的技术。
### spring boot admin 监控
* 正常情况下，由server端和被监听的client端组成。
* spring cloud下其实是监听Eureka，所以client端为Eureka。
* 这两种配置不同，第一种需要一个@EnableClient
* 注册中心：@EnableAdminServer作用是监听所有服务
* 注册中心：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。作用都是将本身注册到注册中心。
### Eureka 的使用
* 启动注册中心，@EnableEurekaServer即可。
* 注册服务到注册中心，引入spring-cloud-starter-netflix-eureka-server或者spring-cloud-starter-eureka。
* 新版的将使用第一个，第二个包已经不包含在spring-cloud-dependencies里面了。
### Consul 的使用  
* 安装 
>    1.下载路径：https://www.consul.io/downloads.html
     2.window 下设置环境变量%PATH%

* 运行开发模型

     
     1.consul agent -dev
     2.consul members #查看集群节点
*  引入使用 @EnableDiscoveryClient
```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
```
### spring cloud 基础知识
* @RestController和@Controller的区别

>     @RestController注解相当于@ResponseBody ＋ @Controller，适合ajax调用。
* @GetMapping、@RequestMapping，@PostMapping的区别
>     @GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写. 例如：@GetMapping("/hello/{user}")
>     @PostMapping是一个组合注解，是@RequestMapping(method = RequestMethod.POST)的缩写。
### spring cloud 服务的注册与调用
* Controller 注册服务到注册中心。
* Feign从注册中心获取服务。 类似于HttpClient。   
### Feign 的使用
* 使用场景： 远程调用的时候， 比如当前服务要调用eureka上已注册的服务。
* 安装：@EnableFeignClients 加入注解。
* 使用方式：
```
 @FeignClient(name = "${feign.name}", url = "${feign.url}")
 public interface StoreClient {
     @RequestMapping(method = RequestMethod.GET, value = "/hello")
     Hello iFailSometimes();
 }
```
* name 是注册微服务名称， url是指rest接口地址。
* 微服务名称的来源是根据配置：spring.application.name
### Feign 请求超时问题
* Hystrix默认的超时时间是1秒，如果超过这个时间尚未响应，将会进入fallback代码。而首次请求往往会比较慢（因为Spring的懒加载机制，要实例化一些类），这个响应时间可能就大于1秒了
* 解决方案有三种，以feign为例。
1. 方法一
> hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds: 5000
该配置是让Hystrix的超时时间改为5秒
2. 方法二
> hystrix.command.default.execution.timeout.enabled: false
该配置，用于禁用Hystrix的超时时间
3. 方法三
> feign.hystrix.enabled: false
该配置，用于索性禁用feign的hystrix。该做法除非一些特殊场景，不推荐使用。
### mybatis 和 mybatis 分页的引入
    使用mybatis要配置mapper文件的所在位置:@MapperScan("com.**.mapper")
### spring jpa的引入
    使用jpa能够非常方便的使用简单的查询，比如单表查询等。极大的提高了开发效率。
    相关包为：spring-boot-starter-data-jpa

###json 序列化
    采用fastJson， 性能相对来说较好。
### 关于zuul，没有引入
1. Zuul是Netflix出品的一个基于JVM路由和服务端的负载均衡器。
2. zuul能实现动态路由、监控、弹性与安全性。是所有请求的入口
3. The proxy uses Ribbon to locate an instance to forward to via discovery, and all requests are executed in a hystrix command, so failures will show up in Hystrix metrics, and once the circuit is open the proxy will not try to contact the service.
4. 没法重定向
5. 超高并发会造成阻塞
6. 文件上传编码问题。请求中文编码问题。
7. zuul重定向后header会变掉。
8. 总之有些问题可能会影响开发，最好不用。
### 使用Spring Cloud Gateway代替zuul
    1.因为单点登录，权限验证需要经过网关，所以为了减少启动应用的数量， 直接将网关直接放到权限模块中。
    2.Route Predicate Factories。可以设置都某个时间将请求跳转到其他地方。比如讲今天12点后的请求都跳转到新的服务器。这个功能便于代码的升级与迁移。
    3.可以根据 host name pattern 进行不同的跳转，这个功能可以做静态页面处理等。
    4.可以根据 Header或者Cookie 进行不同的跳转， 这个功能可以做单点登录
    5.可以根据Method=GET/POST 做不同的跳转， 这个功能能够在应用上实现读写分离。
    6.可以根据某些请求参数做跳转。
### Spring Cloud Gateway 配置
    1.引入启动包：spring-cloud-starter-gateway
    2.设置注册中心，会自动监听：@EnableDiscoveryClient
    
### String Auth2 使用
    Authorization:Basic d2ViYXBwOnNlY3JldA==
    获取token： http://localhost:8080/uaa/oauth/token?grant_type=password&username=zzg&password=admin
    请求： http://localhost:9000/hello?name=xx&access_token=94d0ef5e-cea8-4f32-96b0-5417d8cad911
    
### zuul 动态路由配置
    1、达到动态刷新的目的
    2、存储在db（可以db、zk、redis）
    3、刷新路由：http://localhost:8080/refreshRoute
    
### Hystrix 熔断器，包含在包netflix中
    1、首先检查缓存中是否有结果。如果有则直接返回缓存结果。
    2、判断断路器是否开启，如果断路器闭合，执行降级业务逻辑并返回降级结果。
    3、判断信号量/线程池资源是否饱和，如饱和则执行降级业务逻辑并返回降级结果。
    4、调用实际服务，如发生异常，执行降级业务逻辑并返回降级结果，并调整断路器阈值。
    5、判断实际业务是否超时，超时则返回超时响应结果，并调整断路器阈值。
* 引入
```
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-hystrix</artifactId>
 </dependency>
 <!--界面-->
 <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-hystrix-dashboard             </artifactId>
 </dependency>
```
* 增加注解@EnableCircuitBreaker

* 单独使用
```
// HystrixCommand里面可以设置各种属性， 包含请求时间，失败率，请求数等
@HystrixCommand(fallbackMethod = "helloServiceFallBack")
public String helloService(String name) {
}
```
* Feign结合
```
@FeignClient(name="order", fallback = HelloServiceFallback.class)
public interface HelloService {
  @RequestMapping(value = "/user/timeout", method = RequestMethod.GET)
  String helloService(String name);
}
```
### Hystrix Turbine聚合Hystrix stream信息
* 新建项目引入依赖
```
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-turbine</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-netflix-turbine</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
* 增加注解：@EnableTurbine
```
    # 指定聚合哪些集群，多个使用","分割，默认为default。可使用http://.../turbine.stream?cluster={clusterConfig之一}访问
    turbine.aggregator.clusterConfig=default
    # 配置Eureka中的serviceId列表，表明监控哪些服务
    turbine.appConfig=hystrixClient1,hystrixClient2,hystrixClient3
    turbine.clusterNameExpression=new String("default")
    # 1. clusterNameExpression指定集群名称，默认表达式appName；此时：turbine.aggregator.clusterConfig需要配置想要监控的应用名称
    # 2. 当clusterNameExpression: default时，turbine.aggregator.clusterConfig可以不写，因为默认就是default
    # 3. 当clusterNameExpression: metadata['cluster']时，假设想要监控的应用配置了eureka.instance.metadata-map.cluster: ABC，则需要配置，同时turbine.aggregator.clusterConfig: ABC
```

* 以上所有的熔断功能以及被集成到spring-cloud-starter-netflix-zuul中。
启动测试：http://localhost:8080//hystrix.stream

