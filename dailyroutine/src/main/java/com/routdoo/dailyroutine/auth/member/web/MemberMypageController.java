package com.routdoo.dailyroutine.auth.member.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.domain.PlaceLike;
import com.routdoo.dailyroutine.module.place.dto.PlaceLikeDefaultDto;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineLikeDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineLikeDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
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
    public Map<String,Object> selectMypageAppSummary() throws Exception {
        modelMap = new LinkedHashMap<>();

        //공개설정 되어있는 일정 조회
        DailyRoutineDefaultDto drDto = new DailyRoutineDefaultDto();
        drDto.setPublicYn("Y");
        drDto.setMemberId(memberSession.getMemberSession().getId());
        List<DailyRoutineDto> drList = dailyRoutineService.selectDailyRoutineList(drDto);

        //공개일정
        modelMap.put("publicCnt",drList.size());

        //친구목록 조회
        MemberFriendsDto friendsDto = new MemberFriendsDto();
        friendsDto.setMemberId(memberSession.getMemberSession().getId());
        friendsDto.setBlockYn("N");
        int friendsCnt = friendListService.selectFriendListResultList(friendsDto).size();

        //친구 수
        modelMap.put("friendCnt",friendsCnt);

        //나만의 장소 수
        modelMap.put("placeCnt",0);

        return modelMap;
    }

    /**
     * 마이페이지 일정 보관 목록
     * @return
     * @throws Exception
     */
    @Operation(summary = "마이페이지 일정 보관 목록")
    @Parameter(name = "page", description = "페이지 번호")
    @GetMapping(API_URL+"/mypage/daily/like/list")
    public Map<String,Object> selectMypageDailyLikeList(@RequestParam("page") int page) throws Exception {

        modelMap = new LinkedHashMap<>();

        DailyRoutineLikeDefaultDto searchDto = new DailyRoutineLikeDefaultDto();
        searchDto.setPage(page);
        searchDto.setMemberId(memberSession.getMemberSession().getId());

        Page<Map<String,Object>> resultList = dailyRoutineService.selectDailyRoutineLikePageList(searchDto);

        modelMap.put("dailyList",resultList);

        return modelMap;
    }

    /**
     * 마이페이지 장소 보관 목록
     * @param page
     * @return
     * @throws Exception
     */
    @Operation(summary = "마이페이지 장소 보관 목록")
    @Parameter(name = "page", description = "페이지 번호")
    @GetMapping(API_URL+"/mypage/place/like/list")
    public Map<String,Object> selectMypagePlaceLikeList(@RequestParam("page") int page) throws Exception {

        modelMap = new LinkedHashMap<>();

        PlaceLikeDefaultDto searchDto = new PlaceLikeDefaultDto();
        searchDto.setMemberId(memberSession.getMemberSession().getId());
        searchDto.setPage(page);

        Page<Map<String,Object>> placeList = placeService.selectMypagePlaceLikePageList(searchDto);
        modelMap.put("placeList",placeList);

        return modelMap;
    }

    /**
     * 마이페이지 친구 목록( 차단 상태 조회)
     * @param blockYn
     * @param page
     * @return
     * @throws Exception
     */
    @Operation(summary = "마이페이지 친구 차단상태별 목록")
    @Parameters({
            @Parameter(name = "blockYn",description = "차단여부 ex) Y, N"),
            @Parameter(name = "page" , description = "페이지 번호")
    })
    @GetMapping(API_URL+"/mypage/friends/block/list")
    public Map<String,Object> selectMypageFriendsBlockList(
                                            @RequestParam("blockYn") String blockYn,
                                            @RequestParam("page") int page) throws Exception {
        modelMap = new LinkedHashMap<>();

        MemberDefaultDto searchDto = new MemberDefaultDto();
        searchDto.setBlockYn(blockYn);
        searchDto.setPage(page);

        Page<Map<String,Object>> resultList = friendListService.selectMypageFriendsBlockPageList(searchDto);
        modelMap.put("blockList",resultList);
        modelMap.put("blockYn", blockYn);

        return modelMap;
    }

}
