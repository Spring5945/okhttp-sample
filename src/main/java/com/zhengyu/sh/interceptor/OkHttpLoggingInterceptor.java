package com.zhengyu.sh.interceptor;

import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by zhengyu.nie on 2018/8/29.
 */
@Component("okHttpLoggingInterceptor")
public class OkHttpLoggingInterceptor implements Interceptor {

    private static final Logger logger = LogManager.getLogger();
    public final static Charset UTF8 = Charset.forName("UTF-8");
    private static final String okHttpClogTitle = "okHttpClogTitle";

    @Override
    public Response intercept(Chain chain) throws IOException {
        long start = System.currentTimeMillis();
        Request request = chain.request();
        logger.info(okHttpClogTitle, String.format("%s on %s%n%s", request, chain.connection(), request.headers()));
        Response response = chain.proceed(request);
        try {
            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (contentLength == 0) {
                logger.info(okHttpClogTitle, String.format("%s %n%s %n%s", response,
                        "Time: " + (System.currentTimeMillis() - start) + "ms", response.headers()));
            } else {
                logger.info(okHttpClogTitle,
                        String.format("%s %n%s %n%s %n%s", response, "Time: " + (System.currentTimeMillis() - start) + "ms",
                                buffer.clone().readString(charset), response.headers()));
            }
        } catch (IOException e) {
            logger.warn(okHttpClogTitle, e);
        }

        return response;
    }
}
