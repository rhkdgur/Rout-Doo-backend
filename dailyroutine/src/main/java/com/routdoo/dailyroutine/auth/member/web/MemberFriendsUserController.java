package com.routdoo.dailyroutine.auth.member.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.web
 * fileName       : MemberFriendsUserController
 * author         : rhkdg
 * date           : 2023-12-07
 * description    : 친구 처리 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-07        rhkdg       최초 생성
 */
@Tag(name = "친구 초대 및 차단 컨트롤러")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class MemberFriendsUserController extends BaseModuleController {

    private final FriendListService friendListService;

    private final MemberSession memberSession;

    /**
     * 친구 리스트
     * @param sstring
     * @return
     * @throws Exception
     */
    @Operation(summary = "친구 리스트 (친구 목록)")
    @Parameter(name="sstring", description = "검색어")
    @GetMapping(API_URL+"/member/nickname/friends/list")
    public Map<String,Object> selectMemberFriendsList(@RequestParam("sstring") String sstring) throws Exception {

        modelMap = new LinkedHashMap<>();

        MemberDefaultDto searchDto = new MemberDefaultDto();
        searchDto.setSstring(sstring);
        searchDto.setStype("nickname");
        searchDto.setSize(20);

        Page<Map<String,Object>> friendsList  = friendListService.selectMemberFriendsPageList(searchDto);
        modelMap.put("friendsList",friendsList);

        return modelMap;
    }

    /**
     * 친구 추가
     * @param memberFriendsDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "친구 추가")
    @PostMapping(API_URL+"/member/friends/ins")
    public ResponseEntity<String> insertMemberFriends(final @Valid @RequestBody MemberFriendsDto memberFriendsDto) throws  Exception {

        try{
            friendListService.insertFriendList(memberFriendsDto);
        }catch (Exception e) {
            logger.error("### insert member friends error  : {}",e.getMessage());
            return new ResponseEntity<>("친구추가시 오류가 발생했습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        return new ResponseEntity<>("친구추가 되었습니다.",HttpStatus.OK);
    }

    /**
     * 친구 삭제
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "친구 삭제")
    @Parameter(name="idx", description = "친구정보 일련번호")
    @DeleteMapping(API_URL+"/member/friends/del")
    public ResponseEntity<String> deleteMemberFriends(@RequestParam("idx") Long idx) throws  Exception {

        try{
            MemberFriendsDto friendsDto = new MemberFriendsDto();
            friendsDto.setIdx(idx);
            friendListService.deleteFriendList(friendsDto);
        }catch (Exception e){
            logger.error("delete member friends error : {}",e.getMessage());
            return new ResponseEntity<>("친구삭제시 오류가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>("친구삭제 되었습니다.",HttpStatus.OK);
    }

    /**
     * 친구 차단 업데이트
     * @param blockYn
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "친구 차단상태 업데이트")
    @Parameters({
            @Parameter(name = "blockYn", description = "차단상태 업데이트"),
            @Parameter(name = "idx", description = "친구정보 일련번호")
    })
    @PutMapping(API_URL+"/member/friends/block")
    public ResponseEntity<String> updateFriendsBlock(@RequestParam("blockYn") String blockYn,
                                                     @RequestParam("idx") Long idx) throws Exception {

        try{
            MemberFriendsDto friendsDto = new MemberFriendsDto();
            friendsDto.setIdx(idx);
            friendsDto.setBlockYn(blockYn);
            friendsDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = friendListService.updateFriendsBlockYn(friendsDto);
            if(!result){
                return new ResponseEntity<>("친구 차단상태 업데이트에 실패하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### update friends blockYn error : {}",e.getMessage());
            return new ResponseEntity<>("친구 차단상태 업데이트시 오류가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String text = blockYn.equals("Y") ? "차단되었습니다." : "차단해제되었습니다.";

        return new ResponseEntity<>(text,HttpStatus.OK);
    }
}
