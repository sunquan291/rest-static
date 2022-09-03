# REST调用明细辅助记录Starter

微服务架构下，服务间调用多是通过rest接口完成，所以rest调用信息的纪录对于故障定界定位则变得格外重要。作为架构师有必要从技术上解决该痛点问题。

## 加依赖

```xml
<dependency>
    <groupId>com.zte.sdn.oscp</groupId>
    <artifactId>rest-statics-spring-boot-stater</artifactId>
    <version>2.4.4</version>
</dependency>
```

## 设配置

```yaml
rest:
  statics:
    skip-urls: #设置需要过滤不进行信息统计的URL
      - /bechmark/*
    count: 9 #设置滚动记录信息的条目数,会自动转为2指数数目,默认1024
    long-rest-time: 10000 #慢REST调用，执行耗时超过该值(10s)的rest会记录日志,包com.zte.sdn.oscp.statics.filter
```

## 查数据

内置两个REST接口，可以查看调用信息

- http://ip:port/rest-statics/list



  ```json
  {
  "index": 2,  # 索引，对应下边list-by的入参
  "invokeTime": "22-08-31 17:04:06", #调用时间
  "from": "0:0:0:0:0:0:0:1:56435", #调用来源地址端口
  "method": "GET", #调用方法类型
  "path": "/banner/error", #调用路径
  "queryString": null, #参数
  "resultCode": 200, #调用结果码
  "extInfo": "Request processing failed; nested exception is java.lang.ArithmeticException: / by zero", #附加信息,用于显示异常信息
  "costInMs": 137 #调用耗时，单位毫秒
  }
  ```

  

- http://ip:port/rest-statics/list-by/2

 通过下标查询指定记录，会给出更详细信息，方便问题定位定界

```json
{
"index": 2,
"invokeTime": "22-08-31 17:04:09",
"from": "0:0:0:0:0:0:0:1:56435",
"method": "GET",
"path": "/banner/error",
"queryString": null,
"resultCode": 200,
"extInfo": "org.springframework.web.util.NestedServletException: Request processing failed; nested exception is java.lang.ArithmeticException: / by zero\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:626)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:733)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat com.zte.sdn.oscp.statics.filter.RestLogFilter.doRestLogFilter(RestLogFilter.java:64)\r\n\tat com.zte.sdn.oscp.statics.filter.RestLogFilter.doFilter(RestLogFilter.java:57)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:143)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:357)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:374)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:893)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1707)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\r\n\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.lang.Thread.run(Thread.java:748)\r\nCaused by: java.lang.ArithmeticException: / by zero\r\n\tat com.zte.sdn.oscp.h2.controller.BannerController.getError(BannerController.java:63)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.lang.reflect.Method.invoke(Method.java:498)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:197)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:141)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:894)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)\r\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1060)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:962)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n\t... 41 more\r\n",
"costInMs": 2
}
```

- http://ip:port/rest-statics/list-error

  查询REST调用报错的所有记录
