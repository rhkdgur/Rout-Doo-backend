package com.routdoo.dailyroutine.config;

import com.routdoo.dailyroutine.cms.file.CmsFileThreadLocalInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName    : com.routdoo.dailyroutine.config
 * fileName       : WebMvcConfig
 * author         : rhkdg
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        rhkdg       최초 생성
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final CmsFileThreadLocalInterceptor cmsFileThreadLocalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cmsFileThreadLocalInterceptor);
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(octetStreamReadMsgConverter);
//    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000",
                        "http://localhost:8080",
                        "http://routidoo.store",
                        "https://routidoo.store",
                        "http://routidoo001.cafe24.com",
                        "https://routidoo001.cafe24.com")
                .allowedMethods(
                        HttpMethod.POST.name(),
                        HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
