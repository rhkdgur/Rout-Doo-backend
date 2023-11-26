package com.routdoo.dailyroutine.auth.member.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
@RestController
@RequiredArgsConstructor
public class MemberMypageController extends BaseModuleController {


    private final MemberSession memberSession;

    private final PlaceService placeService;

    private final DailyRoutineService dailyRoutineService;

    private final FriendListService friendListService;


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
    @GetMapping(API_URL+"/mypage/daily/routine/list")
    public Map<String,Object> selectMypageDailyRoutineList() throws Exception {


        return null;
    }

}
