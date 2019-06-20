# Spring Boot Use Apollo Demo

日志配置动态修改

### 一、初始化器
####`1. com/demo/config/ApolloEnvInitializer.java`

根据`application.yml`
```
apollo:
  env: pro   
```
设置`apollo`需要的的系统环境变量`env`，可用启动参数`-Denv=pro`代替
<br/><br/> 
    
####`2. com/demo/config/LoggingReInitializer.java`

拉取`apollo`配置后, 重新初始化日志

原因: `spring-boot`日志初始化优先于从拉取`apollo`配置, 导致`logback.xml`中变量没有替换
<br/> <br/> 

###  二、配置监听器
####`com/demo/component/ApolloChangeListener.java`
    
监听`apollo`发布的配置变化，刷新bean，根据关键字`log.file`进行日志重新初始化
<br/> <br/> 

###  三、注册初始化器
```
public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.addInitializers(new ApolloEnvInitializer());
    application.addInitializers(new LoggingReInitializer());
    application.run(args);
}
```
    