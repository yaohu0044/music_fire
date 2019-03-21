//package com.musicfire.common.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import com.musicfire.common.handler.LoginUserInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
////@Configuration
//public class WebMvcConfigBack implements WebMvcConfigurer {
//
//    @Resource
//    private LoginUserInterceptor loginInterceptor;
//
//    @Bean
//    public LoginUserInterceptor loginUserInterceptor(){
//        return new LoginUserInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //排除地址
//        List<String> excludeList = new ArrayList<>();
//        excludeList.add("/alipay/*");
//        excludeList.add("/wechat/*");
//        excludeList.add("/wxpay/*");
//        excludeList.add("/mobilePay/*");
//        excludeList.add("/mobile/*");
//        excludeList.add("/mobile");
//        excludeList.add("/mobile_register/*");
//        excludeList.add("/mobile_register/*");
//        excludeList.add("/static/css/*");
//        excludeList.add("/goodsList.html");
//        excludeList.add("/api/order/saveOrder/*");
////        excludeList.add("/api/order/saveOrder/*");
////        excludeList.add("/api/mobilePay/*");
////        excludeList.add("/api/machinePosition/purchaseErrOpenPosition/*");
////
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(excludeList);
//    }
//
//
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        /**
//         * 序列换成json时,将所有的long变成string
//         * 因为js中得数字类型不能包含所有的java long值
//         */
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//        objectMapper.registerModule(simpleModule);
//        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        converters.add(jackson2HttpMessageConverter);
//    }
//
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
//        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
//        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); // 4
//        return new CorsFilter(source);
//    }
//
//}
