package com.qiantang.partybuilding.config;

/**
 * 事件总线 事件编码
 */
public interface Event {
    int RELOAD = 2000;
    int GO_HOME = 2002;
    int FINISH = 2003;
    int RELOAD_WEB = 2004;
    int WEB_BACK = 2007;
    int KILL_WEB = 2010;
    int GO_MALL = 2013;
    int LOGOUT = 2021;
    int RELOAD_STUDY = 2026;

    //搜索跳转类型
    int SEARCH_NEWS=8;  //新闻快报
    int SEARCH_MEETING=6;  //会议纪要
    int SEARCH_MIEN=4;  //党建风采
    int SEARCH_STUDY_STATE=9; //学习动态
    int SEARCH_CHARACTER=5; //人物表彰

    int SEARCH_SPEECH_STUDY = 10;//系列讲话
    int SEARCH_VIDEO_STUDY = 11;//视频学习
    int SEARCH_GOOD_BOOK = 12;//好书推荐
    int SEARCH_LEARNING = 13;//专题学习
    int SEARCH_ONLINE = 14;//理论在线
    int SEARCH_PARAGON = 15;//先进典范
    int SEARCH_PARTY = 16;//党建活动
    int SEARCH_MSG = 17;//通知公告
}
