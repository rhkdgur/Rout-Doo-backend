package com.routdoo.dailyroutine.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.common.vo
 * fileName       : CommonResponse
 * author         : GAMJA
 * date           : 2024/05/05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/05        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    /**메세지*/
    private String message = "";

    /**특정 object */
    private T element;

    public CommonResponse(String message) {
        this.message = message;
    }

    public static CommonResponse<?> resOnlyMessageOf(String message) {
        CommonResponse<?> commonResponse = new CommonResponse<>();
        commonResponse.setMessage(message);
        return commonResponse;
    }

    public static CommonResponse<?> resAllOf(String message, Object element) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.setElement(element);
        commonResponse.setMessage(message);
        return commonResponse;
    }

}
