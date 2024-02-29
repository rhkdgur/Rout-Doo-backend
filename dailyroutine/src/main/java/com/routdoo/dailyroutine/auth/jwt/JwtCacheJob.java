package com.routdoo.dailyroutine.auth.jwt;

import com.routdoo.dailyroutine.auth.jwt.domain.JwtTokenEntity;
import com.routdoo.dailyroutine.auth.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.auth.jwt
 * fileName       : JwtCacheJob
 * author         : rhkdg
 * date           : 2024-03-01
 * description    : jwt 토큰 만료 제거 스케줄링
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-01        rhkdg       최초 생성
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtCacheJob {

    private final JwtTokenService jwtTokenService;

    private final JwtProvider jwtProvider;

    @Scheduled(cron ="0 0/5 * * * ?")
    public void schedule(){

        log.debug("jwt cache check start");
        try{
            List<JwtTokenEntity> list = jwtTokenService.findList();
            for(JwtTokenEntity entity : list){
                if(jwtProvider.getValidateToken(entity.getAccessToken()).getCodeType().name().equals(JwtResultCodeType.TOKEN_EXPIRE.name())){
                   jwtTokenService.delete(entity.getId());
                }
            }
        }catch (Exception e){
            log.error("jwt cache check error : {}",e.getMessage());
        }
    }
}
