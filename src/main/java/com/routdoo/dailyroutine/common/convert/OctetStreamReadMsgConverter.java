package com.routdoo.dailyroutine.common.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

/**
 * packageName    : com.routdoo.dailyroutine.common.convert
 * fileName       : OctetStreamReadMsgConverter
 * author         : rhkdg
 * date           : 2024-06-18
 * description    : mulltipart convert
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        rhkdg       최초 생성
 */
//@Component
public class OctetStreamReadMsgConverter extends AbstractJackson2HttpMessageConverter {
    protected OctetStreamReadMsgConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }
}
