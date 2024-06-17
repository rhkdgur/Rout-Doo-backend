package com.routdoo.dailyroutine.config;

import com.routdoo.dailyroutine.cms.file.CmsFileThreadLocalInterceptor;
import com.routdoo.dailyroutine.common.convert.OctetStreamReadMsgConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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

    private final OctetStreamReadMsgConverter octetStreamReadMsgConverter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cmsFileThreadLocalInterceptor);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(octetStreamReadMsgConverter);
    }
}
