package com.qiantang.partybuilding.network;


import com.qiantang.partybuilding.config.Config;

/**
 * 用于HMTL 地址管理
 */
public class URLs {
    public static final String RESPONSE_OK = "0";
    public static final int PAGE_SIZE = 8;
    //版本检测
    public static final String GET_VERSION = Config.SERVER_HOST + "app/version/compare?versionId=0";
    public static final String NOTICE_DETIAL = Config.SERVER_HOST + "app/partyBuild/share?contentId=";
    public static final String SPECIALORTHEORY=Config.SERVER_HOST+"app/content/specialOrTheoryShare?contentId=";
    public static final String MESSAGE_DETIAL=Config.SERVER_HOST+"app/partyBuild/shareTZ?noticeId=";
    public static final String USER_PROTOCOL=Config.SERVER_HOST+"app/agreement/agreement?type=";
}

