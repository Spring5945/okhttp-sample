package com.zhengyu.sh.configuration;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhengyu.nie on 2018/9/1.
 */
@Configuration
@ComponentScan("com.zhengyu.sh")
public class OkHttpConfiguration {
    @Bean
    public OkHttpClient okHttpClient(@Qualifier("okHttpLoggingInterceptor") Interceptor interceptor) {

        return new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .build();
    }
}
