package com.test;

public enum ApiReturnCode {
    GRM_501("GRM_501", "방생성 요청에 실패하였습니다.")
    ,GRM_502("GRM_502", "방상태 확인 요청에 실패하였습니다.")
    ,GRM_400("GRM_400", "방생성 정보가 부족합니다.")
    ,GRM_401("GRM_401", "인증 정보가 불일치 합니다.")
    ,GRM_500("GRM_500", "방생성에 실패 하였습니다.")
    ,GRM_701("GRM_701", "방생성 정보가 이미 존재합니다.[1]")
    ,GRM_702("GRM_702", "방생성 정보가 이미 존재합니다.[2]")
    ,GRM_700("GRM_700", "존재하지 않는 방입니다.")
    ,PLY_500("PLY_500", "생성된 방정보 저장에 실패하였습니다.")
    ,PLY_401("PLY_401", "이미 종료된 방입니다.")
    ,C2C_403("C2C_403", "튜터닷컴 에러")    // 직접 전달 하는 메시지를 가져옴
    ,B2C_401("B2C_401", "일간대치동 에러")  // 직접 전달 하는 메시지를 가져옴
    ,B2C_400("B2C_400", "일간대치동 에러")  // 직접 전달 하는 메시지를 가져옴
    ,B2C_403("B2C_403", "일간대치동 에러"); // 직접 전달 하는 메시지를 가져옴

    private String code;
    private String message;

    ApiReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
