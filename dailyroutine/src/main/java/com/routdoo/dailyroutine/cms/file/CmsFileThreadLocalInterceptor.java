package com.routdoo.dailyroutine.cms.file;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * packageName    : com.routdoo.dailyroutine.cms.file
 * fileName       : CmsFileThreadLocalIntercepter
 * author         : GAMJA
 * date           : 2023/12/11
 * description    : cms file multipartrequest interceptor
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/11        GAMJA       최초 생성
 */
@Component
public class CmsFileThreadLocalInterceptor implements HandlerInterceptor {

    // preHandle -> Controller -> postHandle -> 화면처리 -> afterCompletion

    // 컨트롤러 가기 전 실행
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        CmsFileThreadLocalHolder.setRequest(request);
        return true;
    }

    // 화면 처리가 끝난 뒤 실행
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CmsFileThreadLocalHolder.clear();
    }

}
