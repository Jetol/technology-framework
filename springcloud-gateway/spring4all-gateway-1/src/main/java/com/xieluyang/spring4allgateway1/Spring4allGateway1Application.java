package com.xieluyang.spring4allgateway1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@RestController
@SpringBootApplication
public class Spring4allGateway1Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring4allGateway1Application.class, args);
    }

    /**
     * 路由断言工厂
     * 路由断言工厂有多种类型，根据请求的时间、host、路径、方法等等。
     * 如下定义的是一个基于路径的路由断言匹配（后续文章会按照官方文档进行继续详细讲解）
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> testFunRouterFunction() {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.path("/testfun"),
                request -> ServerResponse.ok().body(BodyInserters.fromObject("hello")));
        return route;
    }
    /**
     * 过滤器工厂（访问思路有所不同）
     * 网关经常对路由进行过滤，进行一些操作，如鉴定权限之后，构造头部之类的，过滤的种类比较多，如：
     * 增加请求头，增加参数，增加响应头和断路器等等功能。
     */
    @Bean
    public RouteLocator customRouteLocator2(RouteLocatorBuilder builder) {
        //@formatter:off
        return builder.routes()
                .route(r -> r.path("/get")
                        .filters(f ->
                                f.addResponseHeader("X-AnotherHeader", "baz"))
//                        .uri("http://httpbin.org/get1")
                        .uri("http://localhost:8080/")
                )
                .build();
        //@formatter:on
    }

   /* @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        ZonedDateTime minusTime = LocalDateTime
                .now()
                .minusDays(1)
                .atZone(ZoneId.systemDefault());
        return builder.routes()
                .route("after_route", r -> r.after(minusTime)
                        .uri("/hello"))
                .build();
    }*/
   @Bean
   public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
       return builder.routes()
               .route("path_route", r -> r.path("/get")
                       .uri("http://httpbin.org"))
               .route("host_route", r -> r.host("*.myhost.org")
                       .uri("http://httpbin.org"))
               .route("rewrite_route", r -> r.host("*.rewrite.org")
                       .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
                       .uri("http://httpbin.org"))
               .route("hystrix_route", r -> r.host("*.hystrix.org")
                       .filters(f -> f.hystrix(c -> c.setName("slowcmd")))
                       .uri("http://httpbin.org"))
               .route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
                       .filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
                       .uri("http://httpbin.org"))
//               .route("limit_route", r -> r
//                       .host("*.limited.org").and().path("/anything/**")
//                       .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
//                       .uri("http://httpbin.org"))
               .build();
   }

    @RequestMapping("get")
    public String get(){
        return "xieluyang";
    }
    @RequestMapping("hello")
    public String hello(){
        return "hello，xieluyang";
    }



}
