package com.routdoo.dailyroutine.auth.member.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import com.routdoo.dailyroutine.auth.member.dto.action.friend.MemberFriendsBlockCreateRequest;
import com.routdoo.dailyroutine.auth.member.dto.action.friend.MemberFriendsCreateRequest;
import com.routdoo.dailyroutine.auth.member.dto.action.friend.MemberFriendsDeleteRequest;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.common.vo.CommonResponse;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "친구 리스트 (친구 목록)")
    @Parameters({
            @Parameter(name = "sstring", description = "검색어", required = false),
            @Parameter(name = "stype", description = "검색타입 ex) title, nickname ", required = false)
    })
    @GetMapping(API_URL+"/member/nickname/friends/list")
    public Page<MemberDto>  selectMemberFriendsList(@Parameter(hidden = true) MemberDefaultDto searchDto) throws Exception {
        searchDto.setSize(20);
        return friendListService.selectMemberFriendsPageList(searchDto);
    }

    /**
     * 친구 추가
     * @param memberFriendsCreateRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "친구 추가")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "422", description = "에러발생"),
            }
    )
    @PostMapping(API_URL+"/member/friends/ins")
    public ResponseEntity<?> insertMemberFriends(final @Valid @RequestBody MemberFriendsCreateRequest memberFriendsCreateRequest) throws  Exception {

        try{
            MemberFriendsDto memberFriendsDto = MemberFriendsDto.createOf(memberFriendsCreateRequest);
            friendListService.insertFriendList(memberFriendsDto);
        }catch (Exception e) {
            logger.error("### insert member friends error  : {}",e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("친구추가시 오류가 발생했습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("친구추가 되었습니다."),HttpStatus.OK);
    }

    /**
     * 친구 삭제
     * @param memberFriendsDeleteRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "친구 삭제")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "422", description = "에러발생"),
            }
    )
    @DeleteMapping(API_URL+"/member/friends/del")
    public ResponseEntity<?> deleteMemberFriends(final @Valid @RequestBody MemberFriendsDeleteRequest memberFriendsDeleteRequest) throws  Exception {

        try{
            MemberFriendsDto friendsDto = new MemberFriendsDto();
            friendsDto.setIdx(memberFriendsDeleteRequest.getIdx());
            friendListService.deleteFriendList(friendsDto);
        }catch (Exception e){
            logger.error("delete member friends error : {}",e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("친구삭제시 오류가 발생했습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("친구삭제 되었습니다."),HttpStatus.OK);
    }

    /**
     * 친구 차단 업데이트
     * @param memberFriendsBlockCreateRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "친구 차단상태 업데이트")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "422", description = "에러발생"),
            }
    )
    @PutMapping(API_URL+"/member/friends/block")
    public ResponseEntity<?> updateFriendsBlock(final @Valid @RequestBody MemberFriendsBlockCreateRequest memberFriendsBlockCreateRequest) throws Exception {

        try{
            MemberFriendsDto friendsDto = MemberFriendsDto.blockOf(memberFriendsBlockCreateRequest);
            friendsDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = friendListService.updateFriendsBlockYn(friendsDto);
            if(!result){
                return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("친구 차단상태 업데이트에 실패하였습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### update friends blockYn error : {}",e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("친구 차단상태 업데이트시 오류가 발생하였습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String text = memberFriendsBlockCreateRequest.getBlockYn().equals("Y") ? "차단되었습니다." : "차단해제되었습니다.";

        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(text),HttpStatus.OK);
    }
}
