package com.routdoo.dailyroutine.auth.member.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsResponse;
import com.routdoo.dailyroutine.auth.member.dto.MemberMypageSummaryResponse;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.dto.PlaceLikeDefaultDto;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineLikeDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineSummaryResponse;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import com.routdoo.dailyroutine.module.routine.service.RoutineRangeConfigType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.web
 * fileName       : MemberMypageController
 * author         : rhkdg
 * date           : 2023-11-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-26        rhkdg       최초 생성
 */
@Tag(name = "마이페이지 컨트롤러")
//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class MemberMypageController extends BaseModuleController {


    private final MemberSession memberSession;

    private final PlaceService placeService;

    private final DailyRoutineService dailyRoutineService;

    private final FriendListService friendListService;

    /**
     * 마이페이지 요약 정보
     * @return
     * @throws Exception
     */
    @Operation(summary = "마이페이지 최상단 회원 요약 정보")
    @GetMapping(API_URL+"/mypage/app/summary")
    public MemberMypageSummaryResponse selectMypageAppSummary() throws Exception {

        //공개설정 되어있는 일정 조회
        DailyRoutineDefaultDto drDto = new DailyRoutineDefaultDto();
        drDto.setRangeType(RoutineRangeConfigType.PUBLIC.name());
        drDto.setMemberId(memberSession.getMemberSession().getId());
        List<DailyRoutineDto> drList = dailyRoutineService.selectDailyRoutineList(drDto);

        //친구목록 조회
        MemberFriendsDto friendsDto = new MemberFriendsDto();
        friendsDto.setMemberId(memberSession.getMemberSession().getId());
        friendsDto.setBlockYn("N");
        int friendsCnt = friendListService.selectFriendListResultList(friendsDto).size();

        return new MemberMypageSummaryResponse(drList.size(),friendsCnt,0);
    }

    /**
     * 마이페이지 일정 보관 목록
     * @return
     * @throws Exception
     */
    @Operation(summary = "마이페이지 일정 보관 목록")
    @Parameter(name = "page", description = "페이지 번호", required = false)
    @GetMapping(API_URL+"/mypage/daily/like/list")
    public Page<DailyRoutineSummaryResponse> selectMypageDailyLikeList(@Parameter(hidden = true) DailyRoutineLikeDefaultDto searchDto) throws Exception {
        searchDto.setMemberId(memberSession.getMemberSession().getId());
        return dailyRoutineService.selectDailyRoutineLikePageList(searchDto);
    }

    /**
     * 마이페이지 장소 보관 목록
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "마이페이지 장소 보관 목록")
    @Parameter(name = "page", description = "페이지 번호", required = false)
    @GetMapping(API_URL+"/mypage/place/like/list")
    public Page<Map<String,Object>> selectMypagePlaceLikeList(@Parameter(hidden = true) PlaceLikeDefaultDto searchDto) throws Exception {
        searchDto.setMemberId(memberSession.getMemberSession().getId());
        return placeService.selectMypagePlaceLikePageList(searchDto);
    }

    /**
     * 마이페이지 친구 목록( 차단 상태 조회)
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "마이페이지 친구 차단상태별 목록")
    @Parameters({
            @Parameter(name = "blockYn",description = "차단여부 ex) Y, N", required = false),
            @Parameter(name = "page" , description = "페이지 번호", required = false)
    })
    @GetMapping(API_URL+"/mypage/friends/block/list")
    public Page<MemberFriendsResponse> selectMypageFriendsBlockList(@Parameter(hidden = true) MemberDefaultDto searchDto) throws Exception {
        searchDto.setMemberId(memberSession.getMemberSession().getId());
        return friendListService.selectMypageFriendsBlockPageList(searchDto);
    }

}
