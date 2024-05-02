package com.routdoo.dailyroutine.module.routine.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.routdoo.dailyroutine.auth.member.domain.QMember;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.routine.domain.*;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentResponse;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineReplyCommentDto;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineCommentRepository;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.repository.impl
 * fileName       : DailyRoutineCommentRepositoryImpl
 * author         : rhkdg
 * date           : 2023-12-05
 * description    : 댓글, 답글 custom repository impl
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-05        rhkdg       최초 생성
 */
@Repository
public class DailyRoutineCommentRepositoryImpl extends BaseAbstractRepositoryImpl implements DailyRoutineCommentRepository {

    @Override
    public Page<DailyRoutineCommentResponse> selectDailyRoutineCommentPageList(DailyRoutineCommentDefaultDto dto) throws Exception {
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT  " +
                " idx," +
                " id as memberId," +
                " daily_idx as dailyIdx," +
                " nickname," +
                " content," +
                " mbti," +
                " IFNULL(replyCnt,0) as replyCnt" +
                " FROM (");
        sql.append(" SELECT drc.*,m.*,replyCnt FROM daily_routine_comment drc ");
        sql.append(" JOIN ( SELECT id,nickname,mbti FROM member ) m on m.id = drc.member_id ");
        sql.append(" JOIN ( SELECT idx daily_idx  FROM daily_routine ) dr on dr.daily_idx = drc.daily_idx ");
        sql.append(" LEFT JOIN ( SELECT comment_idx , count(*) replyCnt  FROM daily_routine_comment_reply group by comment_idx ) drcr on drc.idx = drcr.comment_idx ");
        sql.append(" ) T where daily_idx = ").append(dto.getDailyIdx())
                .append(" LIMIT ").append(dto.getPageable().getOffset()).append(",").append(dto.getPageable().getPageSize());

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DailyRoutineCommentResponse> resultList = jpaResultMapper.list(entityManager.createNativeQuery(sql.toString()),DailyRoutineCommentResponse.class);

        QMember qMember = QMember.member;
        QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;
        QDailyRoutineComment qDailyRoutineComment = QDailyRoutineComment.dailyRoutineComment;

        //개수
        Long cnt = jpaQueryFactory.select(qDailyRoutineComment.count())
                .from(qDailyRoutineComment)
                .join(qDailyRoutine).on(qDailyRoutine.idx.eq(qDailyRoutineComment.dailyRoutine.idx)).fetchJoin()
                .join(qMember).on(qMember.id.eq(qDailyRoutineComment.member.id)).fetchJoin()
                .where(new BooleanBuilder().and(qDailyRoutineComment.dailyRoutine.idx.eq(dto.getDailyIdx())))
                .fetchFirst();

        return new PageImpl<>(resultList,dto.getPageable(),cnt);
    }

    @Override
    public List<DailyRoutineCommentDto> selectDailyRoutineCommentList(DailyRoutineCommentDefaultDto dto) throws Exception {

        StringBuffer sql = new StringBuffer();

        sql.append("SELECT  " +
                " drc.idx as idx," +
                " m.id as memberId," +
                " drc.daily_idx as dailyIdx," +
                " m.nickname as nickname," +
                " drc.content as content," +
                " m.mbti as mbti," +
                " IFNULL(drcr.replyCnt,0) as replyCnt" +
                " FROM (");
        sql.append(" SELECT * FROM daily_routine_comment drc ");
        sql.append(" JOIN ( SELECT id,nickname,mbti FROM member ) m on m.id = drc.member_id ");
        sql.append(" JOIN ( SELECT idx daily_idx  FROM daily_routine ) dr on dr.daily_idx = drc.daily_idx ");
        sql.append(" LEFT JOIN ( SELECT comment_idx , count(*) replyCnt  FROM daily_routine_comment_reply group by comment_idx ) drcr on drc.idx = drcr.comment_idx ");
        sql.append(" ) T where daily_idx = ").append(dto.getDailyIdx());

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        return jpaResultMapper.list(entityManager.createNamedQuery(sql.toString()),DailyRoutineCommentDto.class);
    }

    @Override
    public long selectDailyRoutineCommentTotalCount(DailyRoutineCommentDefaultDto dto) throws Exception {
        QDailyRoutineComment qDailyRoutineComment = QDailyRoutineComment.dailyRoutineComment;
        QMember qMember = QMember.member;
        QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;

        //개수
        Long cnt = jpaQueryFactory.select(qDailyRoutineComment.count())
                .from(qDailyRoutineComment)
                .join(qDailyRoutine).fetchJoin()
                .join(qMember).fetchJoin()
                .where(new BooleanBuilder().and(qDailyRoutineComment.dailyRoutine.idx.eq(dto.getDailyIdx())))
                .fetchFirst();

        return cnt;
    }

    @Override
    public DailyRoutineCommentDto selectDailyRoutineComment(DailyRoutineCommentDto dto) throws Exception {

        QDailyRoutineComment qDailyRoutineComment  = QDailyRoutineComment.dailyRoutineComment;
        QMember qMember = QMember.member;
        QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;

        DailyRoutineComment comment = jpaQueryFactory.selectFrom(qDailyRoutineComment)
                .join(qDailyRoutine).fetchJoin()
                .join(qMember).fetchJoin()
                .where(new BooleanBuilder().and(qDailyRoutineComment.idx.eq(dto.getIdx())))
                .fetchOne();

        if(comment != null){
            return new DailyRoutineCommentDto(comment);
        }

        return null;
    }

    @Override
    public boolean insertDailyRoutineComment(DailyRoutineCommentDto dto) throws Exception {
        return entityManager.createNativeQuery("insert into daily_routine_comment (member_id,daily_idx,content,create_date,modify_date) values (?,?,?,?,?)")
                .setParameter(1,dto.getMemberId())
                .setParameter(2,dto.getDailyIdx())
                .setParameter(3,dto.getContent())
                .setParameter(4,LocalDateTime.now())
                .setParameter(5,LocalDateTime.now())
                .executeUpdate() > 0;
    }

    @Override
    public boolean updateDailyRoutineComment(DailyRoutineCommentDto dto) throws Exception {

        QDailyRoutineComment qDailyRoutineComment = QDailyRoutineComment.dailyRoutineComment;

        return jpaQueryFactory.update(qDailyRoutineComment)
                .set(qDailyRoutineComment.content,dto.getContent())
                .where(new BooleanBuilder().and(qDailyRoutineComment.idx.eq(dto.getIdx()))).execute() > 0;
    }

    @Override
    public boolean deleteDailyRoutineComment(DailyRoutineCommentDto dto) throws Exception {
        QDailyRoutineComment qDailyRoutineComment = QDailyRoutineComment.dailyRoutineComment;
        return jpaQueryFactory.delete(qDailyRoutineComment)
                .where(new BooleanBuilder().and(qDailyRoutineComment.idx.eq(dto.getIdx())))
                .execute() > 0;
    }

    @Override
    public Page<DailyRoutineReplyCommentDto> selectDailyRoutineReplyCommentPageList(DailyRoutineReplyCommentDto dto) throws Exception {

        QDailyRoutineComment qDailyRoutineComment = QDailyRoutineComment.dailyRoutineComment;
        QDailyRoutineReplyComment qDailyRoutineReplyComment = QDailyRoutineReplyComment.dailyRoutineReplyComment;
        QMember qMember = QMember.member;

        long cnt = jpaQueryFactory.select(qDailyRoutineReplyComment.count())
                .from(qDailyRoutineReplyComment)
                .join(qDailyRoutineComment).on(qDailyRoutineComment.idx.eq(qDailyRoutineReplyComment.dailyRoutineComment.idx)).fetchJoin()
                .join(qMember).on(qMember.id.eq(qDailyRoutineReplyComment.member.id)).fetchJoin()
                .where(new BooleanBuilder().and(qDailyRoutineReplyComment.dailyRoutineComment.idx.eq(dto.getCommentIdx())))
                .fetchFirst();

        List<Tuple> list = jpaQueryFactory.select(
                        qDailyRoutineReplyComment.idx,
                        qDailyRoutineReplyComment.dailyRoutineComment.idx,
                        qMember.id,
                        qMember.nickname,
                        qMember.mbti,
                        qDailyRoutineReplyComment.createDate,
                        qDailyRoutineReplyComment.modifyDate
                )
                .from(qDailyRoutineReplyComment)
                .join(qDailyRoutineComment).on(qDailyRoutineComment.idx.eq(qDailyRoutineReplyComment.dailyRoutineComment.idx)).fetchJoin()
                .join(qMember).on(qMember.id.eq(qDailyRoutineReplyComment.member.id)).fetchJoin()
                .where(new BooleanBuilder().and(qDailyRoutineReplyComment.dailyRoutineComment.idx.eq(dto.getCommentIdx())))
                .offset(dto.getPageable().getOffset())
                .limit(dto.getPageable().getPageSize())
                .fetch();

        List<DailyRoutineReplyCommentDto> resultList = new ArrayList<>();

        for(Tuple tp : list){
            DailyRoutineReplyCommentDto commentDto = new DailyRoutineReplyCommentDto();
            commentDto.setIdx(tp.get(qDailyRoutineReplyComment.idx));
            commentDto.setCommentIdx(tp.get(qDailyRoutineReplyComment.dailyRoutineComment.idx));
            commentDto.setMemberId(tp.get(qMember.id));
            commentDto.getMemberDto().setNickname(tp.get(qMember.nickname));
            commentDto.getMemberDto().setMbti(tp.get(qMember.mbti));
            commentDto.setCreateDate(tp.get(qDailyRoutineReplyComment.createDate));
            commentDto.setModifyDate(tp.get(qDailyRoutineReplyComment.modifyDate));
            resultList.add(commentDto);
        }

        return new PageImpl<>(resultList,dto.getPageable(),cnt);
    }

    @Override
    public List<DailyRoutineReplyCommentDto> selectDailyRoutineReplyCommentList(DailyRoutineReplyCommentDto dto) throws Exception {
        QDailyRoutineComment qDailyRoutineComment = QDailyRoutineComment.dailyRoutineComment;
        QDailyRoutineReplyComment qDailyRoutineReplyComment = QDailyRoutineReplyComment.dailyRoutineReplyComment;
        QMember qMember = QMember.member;

        List<Tuple> list = jpaQueryFactory.select(
                        qDailyRoutineReplyComment.idx,
                        qDailyRoutineReplyComment.dailyRoutineComment.idx,
                        qMember.id,
                        qMember.nickname,
                        qMember.mbti,
                        qDailyRoutineReplyComment.createDate,
                        qDailyRoutineReplyComment.modifyDate
                )
                .from(qDailyRoutineReplyComment)
                .join(qDailyRoutineComment).fetchJoin()
                .join(qMember).fetchJoin()
                .where(new BooleanBuilder().and(qDailyRoutineReplyComment.dailyRoutineComment.idx.eq(dto.getCommentIdx())))
                .fetch();

        List<DailyRoutineReplyCommentDto> resultList = new ArrayList<>();

        for(Tuple tp : list){
            DailyRoutineReplyCommentDto commentDto = new DailyRoutineReplyCommentDto();
            commentDto.setIdx(tp.get(qDailyRoutineReplyComment.idx));
            commentDto.setCommentIdx(tp.get(qDailyRoutineReplyComment.dailyRoutineComment.idx));
            commentDto.setMemberId(tp.get(qMember.id));
            commentDto.getMemberDto().setNickname(tp.get(qMember.nickname));
            commentDto.getMemberDto().setMbti(tp.get(qMember.mbti));
            commentDto.setCreateDate(tp.get(qDailyRoutineReplyComment.createDate));
            commentDto.setModifyDate(tp.get(qDailyRoutineReplyComment.modifyDate));
            resultList.add(commentDto);
        }

        return resultList;
    }

    @Override
    public long selectDailyRoutineReplyCommentTotalCount(DailyRoutineReplyCommentDto dto) throws Exception {

        QDailyRoutineComment qDailyRoutineComment = QDailyRoutineComment.dailyRoutineComment;
        QDailyRoutineReplyComment qDailyRoutineReplyComment = QDailyRoutineReplyComment.dailyRoutineReplyComment;
        QMember qMember = QMember.member;

        long cnt = jpaQueryFactory.select(qDailyRoutineReplyComment.count())
                .from(qDailyRoutineReplyComment)
                .join(qDailyRoutineComment).fetchJoin()
                .join(qMember).fetchJoin()
                .where(new BooleanBuilder().and(qDailyRoutineReplyComment.dailyRoutineComment.idx.eq(dto.getCommentIdx())))
                .fetchFirst();

        return cnt;
    }

    @Override
    public DailyRoutineReplyCommentDto selectDailyRoutineReplyComment(DailyRoutineReplyCommentDto dto) throws Exception {

        QDailyRoutineComment qDailyRoutineComment = QDailyRoutineComment.dailyRoutineComment;
        QDailyRoutineReplyComment qDailyRoutineReplyComment = QDailyRoutineReplyComment.dailyRoutineReplyComment;
        QMember qMember = QMember.member;

        DailyRoutineReplyComment replyComment = jpaQueryFactory.selectFrom(qDailyRoutineReplyComment)
                .join(qDailyRoutineComment).fetchJoin()
                .join(qMember).fetchJoin()
                .where(new BooleanBuilder().and(qDailyRoutineReplyComment.idx.eq(dto.getIdx())))
                .fetchOne();

        if(replyComment != null){
            return new DailyRoutineReplyCommentDto(replyComment);
        }

        return null;
    }

    @Override
    public boolean insertDailyRoutineReplyComment(DailyRoutineReplyCommentDto dto) throws Exception {

        return entityManager.createNativeQuery("insert into daily_routine_reply_comment(member_id,comment_idx,content,create_date,modify_date) values (?,?,?,?,?)")
                .setParameter(1,dto.getMemberId())
                .setParameter(2,dto.getCommentIdx())
                .setParameter(3,dto.getContent())
                .setParameter(4,LocalDateTime.now())
                .setParameter(5,LocalDateTime.now())
                .executeUpdate() > 0;

    }

    @Override
    public boolean updateDailyRoutineReplyComment(DailyRoutineReplyCommentDto dto) throws Exception {
        QDailyRoutineReplyComment qDailyRoutineReplyComment = QDailyRoutineReplyComment.dailyRoutineReplyComment;

        return jpaQueryFactory.update(qDailyRoutineReplyComment)
                .set(qDailyRoutineReplyComment.content,dto.getContent())
                .where(new BooleanBuilder().and(qDailyRoutineReplyComment.idx.eq(dto.getIdx())))
                .execute() > 0;
    }

    @Override
    public boolean deleteDailyRoutineReplyComment(DailyRoutineReplyCommentDto dto) throws Exception {
        QDailyRoutineReplyComment qDailyRoutineReplyComment = QDailyRoutineReplyComment.dailyRoutineReplyComment;

        return jpaQueryFactory.delete(qDailyRoutineReplyComment)
                .where(new BooleanBuilder().and(qDailyRoutineReplyComment.idx.eq(dto.getIdx())))
                .execute() > 0;
    }

    @Override
    public Page<Map> selectCommentPageList(Map<String, String> paramUtil) throws Exception {

        int cpage = paramUtil.get("page") == null ? 1 : Integer.parseInt(paramUtil.get("page"));
        int totalPage = 10;

        StringBuffer sql = new StringBuffer();
        sql.append(" select idx ,content, parentIdx, member_id as memberId , nickname, create_date as createDate , gubun from (");
        sql.append(" select idx, content, place_num as parentIdx, member_id , nickname , create_date , '놀거리' as gubun ");
        sql.append(" from place_comment ");
        sql.append(" left join ( select id,nickname from member ) b ON place_comment.member_id = b.id ");
        sql.append(" union all ");
        sql.append(" select idx, content, daily_idx as parentIdx, member_id , nickname , create_date , '일정' as gubun ");
        sql.append(" from daily_routine_comment ");
        sql.append(" left join ( select id,nickname from member ) c ON daily_routine_comment.member_id = c.id ");
        sql.append(" ) T ");
        sql.append(" limit "+(cpage-1)+","+totalPage);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<Map> resultList = jpaResultMapper.list(entityManager.createNativeQuery(sql.toString()),Map.class);

        sql = new StringBuffer();
        sql.append(" select count(*) cnt (");
        sql.append(" select idx, content, place_num as parentIdx, member_id , create_date , '놀거리' as gubun ");
        sql.append(" from place_comment ");
        sql.append(" union all ");
        sql.append(" select idx, content, daily_idx as parentIdx, member_id , create_date , '일정' as gubun ");
        sql.append(" from daily_routine_comment ");
        sql.append(" ) T ");

        int totCnt = entityManager.createNativeQuery(sql.toString()).getFirstResult();

        Pageable pageable = PageRequest.of((cpage - 1),totalPage);

        return new PageImpl<>(resultList,pageable,totCnt);
    }

}
