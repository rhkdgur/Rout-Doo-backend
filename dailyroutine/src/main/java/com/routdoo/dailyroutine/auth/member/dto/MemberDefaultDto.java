package com.routdoo.dailyroutine.auth.member.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDefaultDto extends BaseVo{
	
	private static final long serialVersionUID = 1L;

	private String blockYn = "";

	private String memberId = "";

	private String nickname = "";

	private boolean isExclude = false;

	private String excludeType = "";

}
