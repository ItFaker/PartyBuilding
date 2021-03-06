package com.qiantang.partybuilding.network;


import com.qiantang.partybuilding.modle.HttpResult;
import com.qiantang.partybuilding.modle.RxActivity;
import com.qiantang.partybuilding.modle.RxActivityDetial;
import com.qiantang.partybuilding.modle.RxAds;
import com.qiantang.partybuilding.modle.RxAdviseRecord;
import com.qiantang.partybuilding.modle.RxApplyDetial;
import com.qiantang.partybuilding.modle.RxAssientHome;
import com.qiantang.partybuilding.modle.RxAuthorizeUserInfo;
import com.qiantang.partybuilding.modle.RxBookDetial;
import com.qiantang.partybuilding.modle.RxBookRecommend;
import com.qiantang.partybuilding.modle.RxCharacterDetial;
import com.qiantang.partybuilding.modle.RxDeptName;
import com.qiantang.partybuilding.modle.RxFeeRecord;
import com.qiantang.partybuilding.modle.RxIndex;
import com.qiantang.partybuilding.modle.RxIndexCommon;
import com.qiantang.partybuilding.modle.RxIndexSpeak;
import com.qiantang.partybuilding.modle.RxIsApplyFor;
import com.qiantang.partybuilding.modle.RxLearningClass;
import com.qiantang.partybuilding.modle.RxLearningList;
import com.qiantang.partybuilding.modle.RxMonthScore;
import com.qiantang.partybuilding.modle.RxMsg;
import com.qiantang.partybuilding.modle.RxMyStudy;
import com.qiantang.partybuilding.modle.RxMyUserInfo;
import com.qiantang.partybuilding.modle.RxOnline;
import com.qiantang.partybuilding.modle.RxParagonDetial;
import com.qiantang.partybuilding.modle.RxPartyFee;
import com.qiantang.partybuilding.modle.RxPartyFeeDetial;
import com.qiantang.partybuilding.modle.RxPersonalCenter;
import com.qiantang.partybuilding.modle.RxRankBranch;
import com.qiantang.partybuilding.modle.RxRankPersonal;
import com.qiantang.partybuilding.modle.RxRecordDetial;
import com.qiantang.partybuilding.modle.RxSetting;
import com.qiantang.partybuilding.modle.RxSign;
import com.qiantang.partybuilding.modle.RxSignList;
import com.qiantang.partybuilding.modle.RxSignResult;
import com.qiantang.partybuilding.modle.RxSpecialDetial;
import com.qiantang.partybuilding.modle.RxSpeechDetial;
import com.qiantang.partybuilding.modle.RxStructureLevelOne;
import com.qiantang.partybuilding.modle.RxStructureLevelTwo;
import com.qiantang.partybuilding.modle.RxStructurePerson;
import com.qiantang.partybuilding.modle.RxStudy;
import com.qiantang.partybuilding.modle.RxStudyUnreadMsg;
import com.qiantang.partybuilding.modle.RxTest;
import com.qiantang.partybuilding.modle.RxTestDetial;
import com.qiantang.partybuilding.modle.RxTestDoneInfo;
import com.qiantang.partybuilding.modle.RxTestInfo;
import com.qiantang.partybuilding.modle.RxThinkDetial;
import com.qiantang.partybuilding.modle.RxTotalScore;
import com.qiantang.partybuilding.modle.RxUploadUrl;
import com.qiantang.partybuilding.modle.RxVideoDetial;
import com.qiantang.partybuilding.modle.RxVideoStudy;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {
    //多文件流上传
    @Multipart
    @POST("app/userCenter/upload")
    Observable<HttpResult<List<RxUploadUrl>>> upload(@PartMap Map<String, RequestBody> params);

    //多文件流上传
    @Multipart
    @POST("app/userCenter/upload")
    Observable<HttpResult<HttpResult>> upload(@Part MultipartBody.Part files);

    //上传用户头像
    @Multipart
    @POST("app/userCenter/uploadUrl")
    Observable<HttpResult<HttpResult>> uploadUrl(@Part MultipartBody.Part file);

    //个人排行
    @FormUrlEncoded
    @POST("app/learningability/peopleRanking")
    Observable<HttpResult<RxRankPersonal>> getRankPersonal(@Field("userId") String userId,
                                                           @Field("time") String time);

    //支部排行
    @FormUrlEncoded
    @POST("app/learningability/branchRanking")
    Observable<HttpResult<RxRankBranch>> getRankBranch(@Field("userId") String userId,
                                                       @Field("time") String time);

    //学习感悟
    @FormUrlEncoded
    @POST("app/comment/commenList")
    Observable<HttpResult<RxStudy>> getStudyList(@Field("userId") String userId,
                                                 @Field("pageNum") int pageNum);

    //我的学习感悟
    @FormUrlEncoded
    @POST("app/comment/myCommentAppList")
    Observable<HttpResult<RxMyStudy>> getMyStudyList(@Field("userId") String userId,
                                                     @Field("pageNum") int pageNum);

    //删除学习感悟
    @FormUrlEncoded
    @POST("app/comment/deleteCommentApp")
    Observable<HttpResult<String>> deleteComment(@Field("commentId") String commentId);

    //学习感悟未读消息
    @FormUrlEncoded
    @POST("app/comment/unLookMessage")
    Observable<HttpResult<List<RxStudyUnreadMsg>>> getUnreadMsg(@Field("userId") String userId);

    //点赞 评论
    @FormUrlEncoded
    @POST("app/comment/zanCommentAppOp")
    Observable<HttpResult<HttpResult>> commentLike(@Field("userId") String userId,
                                                   @Field("type") int type,
                                                   @Field("commentId") String commentId,
                                                   @Field("content") String content);

    //取消赞
    @FormUrlEncoded
    @POST("app/comment/deleteZanApp")
    Observable<HttpResult<HttpResult>> cancelLike(@Field("userId") String userId,
                                                  @Field("commentId") String commentId);

    //发表感想
    @FormUrlEncoded
    @POST("app/comment/addCommentApp")
    Observable<HttpResult<HttpResult>> addCommentApp(@Field("userId") String userId,
                                                     @Field("content") String content,
                                                     @Field("image") String image);

    //党建助手首页
    @FormUrlEncoded
    @POST("app/partyBuild/homePage")
    Observable<HttpResult<RxAssientHome>> assientHome(@Field("userId") String userId);

    @FormUrlEncoded
    //消息列表
    @POST("app/partyBuild/tzNotice")
    Observable<HttpResult<List<RxMsg>>> tzNotice(@Field("pageNum") int page,
                                                 @Field("userId") String userId,
                                                 @Field("searchValue") String searchValue);

    @FormUrlEncoded
    //党建风采
    @POST("app/partyBuild/fcNotice")
    Observable<HttpResult<List<RxIndexCommon>>> fcNotice(@Field("pageNum") int page,
                                                         @Field("type") int type,
                                                         @Field("userId") String userId);

    @FormUrlEncoded
    //党建风采
    @POST("app/partyBuild/fcNotice")
    Observable<HttpResult<List<RxIndexCommon>>> fcNoticeSerach(@Field("pageNum") int page,
                                                               @Field("type") int type,
                                                               @Field("searchValue") String serach,
                                                               @Field("userId") String userId);
    @FormUrlEncoded
    //党建风采/会议纪要搜索
    @POST("app/partyBuild/fcNotice")
    Observable<HttpResult<List<RxIndexCommon>>> fcNoticeSerach1(@Field("pageNum") int page,
                                                                @Field("type") int type,
                                                                @Field("searchValue") String serach);
    @FormUrlEncoded
    //党建风采/会议纪要搜索
    @POST("app/partyBuild/fcNotice")
    Observable<HttpResult<List<RxIndexCommon>>> fcNoticeSerach2(@Field("pageNum") int page,
                                                                @Field("type") int type,
                                                                @Field("searchValue") String serach,
                                                                @Field("userId") String userId);
    @FormUrlEncoded
    //党建活动详情
    @POST("app/partyBuild/djActivityDetails")
//    Observable<HttpResult<RxActivityDetial>> djActivityDetails(@Field("pageNum") int page,
//                                                               @Field("activityId") String activityId,
//                                                               @Field("userId") String userId);
    Observable<HttpResult<RxActivityDetial>> djActivityDetails(
            @Field("activityId") String activityId,
            @Field("userId") String userId);

    @FormUrlEncoded
    //党建活动
    @POST("app/partyBuild/djActivity")
    Observable<HttpResult<List<RxActivity>>> djActivity(@Field("pageNum") int page,
                                                        @Field("userId") String userId,
                                                        @Field("searchValue") String searchValue);

    @FormUrlEncoded
    //我的党建活动
    @POST("app/partyBuild/user/djActivity")
    Observable<HttpResult<List<RxActivity>>> djActivityMine(@Field("pageNum") int page,
                                                            @Field("userId") String userId);

    @FormUrlEncoded
    //我的党建活动
    @POST("app/partyBuild/user/djActivityDelete")
    Observable<HttpResult<String>> djActivityDelete(@Field("activity_id") String activity_id);

    //评论
    @FormUrlEncoded
    @POST("app/partyBuild/comment")
    Observable<HttpResult<HttpResult>> comment(@Field("contentId") String contentId,
                                               @Field("content") String content,
                                               @Field("userId") String userId);

    //活动报名
    @FormUrlEncoded
    @POST("app/partyBuild/enroll")
    Observable<HttpResult<HttpResult>> enroll(@Field("activityNum") String activityNum,
                                              @Field("userId") String userId);

    //活动报名
    @FormUrlEncoded
    @POST("app/partyBuild/tzSign")
    Observable<HttpResult<List<RxSignList>>> tzSign(@Field("noticeId") String noticeId,
                                                    @Field("pageNum") int pageNum);

    //党建风采详情
    @FormUrlEncoded
    @POST("app/partyBuild/fcNoticeDetails")
//    Observable<HttpResult<RxActivityDetial>> fcNoticeDetails(@Field("pageNum") int page,
//                                                             @Field("contentId") String contentId,
//                                                             @Field("userId") String userId);
    Observable<HttpResult<RxActivityDetial>> fcNoticeDetails(
            @Field("contentId") String contentId,
            @Field("userId") String userId);

    //人物表彰详情
    @FormUrlEncoded
    @POST("app/partyBuild/rwNoticeDetails")
//    Observable<HttpResult<RxCharacterDetial>> rwNoticeDetails(@Field("pageNum") int page,
//                                                              @Field("printurl") String printurl,
//                                                              @Field("contentId") String contentId,
//                                                              @Field("userId") String userId);
    Observable<HttpResult<RxCharacterDetial>> rwNoticeDetails(
            @Field("printurl") String printurl,
            @Field("contentId") String contentId,
            @Field("userId") String userId);

    //思想汇报列表
    @FormUrlEncoded
    @POST("app/partyBuild/thinking")
    Observable<HttpResult<List<RxIndexCommon>>> thinking(@Field("pageNum") int page,
                                                         @Field("type") int type,
                                                         @Field("userId") String userId);

    //思想汇报详情
    @FormUrlEncoded
    @POST("app/partyBuild/thinkingDetails")
    Observable<HttpResult<RxThinkDetial>> thinkingDetails(@Field("contentId") String contentId);

    //思想汇报发表
    @FormUrlEncoded
    @POST("app/partyBuild/insertThinking")
    Observable<HttpResult<HttpResult>> insertThinking(@Field("title") String title,
                                                      @Field("content") String content,
                                                      @Field("userId") String userId);

    //反馈发表
    @FormUrlEncoded
    @POST("app/partyBuild/insertIdea")
    Observable<HttpResult<HttpResult>> insertIdea(@Field("content") String content,
                                                  @Field("userId") String userId);

    //反馈列表
    @FormUrlEncoded
    @POST("app/partyBuild/ideaList")
    Observable<HttpResult<List<RxAdviseRecord>>> ideaList(@Field("pageNum") int page,
                                                          @Field("userId") String userId);

    //反馈列表
    @FormUrlEncoded
    @POST("app/home/ShowHomePage")
    Observable<HttpResult<List<RxIndex>>> ShowHomePage(@Field("userId") String userId);

    //视频学习列表
    @FormUrlEncoded
    @POST("app/video/list")
    Observable<HttpResult<List<RxVideoStudy>>> videoList(@Field("pageNum") int page,@Field("count") String count);

    //视频学习列表（登录状态）
    @FormUrlEncoded
    @POST("app/video/list")
    Observable<HttpResult<List<RxVideoStudy>>> userVideoList(@Field("pageNum") int page,@Field("userId") String userId,
                                                             @Field("count") String count);

    //视频学习收藏列表
    @FormUrlEncoded
    @POST("app/video/userList")
    Observable<HttpResult<List<RxVideoStudy>>> videoUserList(@Field("pageNum") int page,
                                                             @Field("userId") String userId);

    //视频学习详情
    @FormUrlEncoded
    @POST("app/video/details")
//    Observable<HttpResult<RxVideoDetial>> videoDetails(@Field("pageNum") int page,
//                                                       @Field("videoId") String videoId,
//                                                       @Field("userId") String userId);
    Observable<HttpResult<RxVideoDetial>> videoDetails(@Field("videoId") String videoId,
                                                       @Field("userId") String userId);

    //系列讲话详情
    @FormUrlEncoded
    @POST("app/speak/details")
//    Observable<HttpResult<RxSpeechDetial>> speechDetails(@Field("pageNum") int page,
//                                                         @Field("speakId") String videoId,
//                                                         @Field("userId") String userId);
    Observable<HttpResult<RxSpeechDetial>> speechDetails(@Field("speakId") String videoId,
                                                         @Field("userId") String userId);

    //视频学习点赞
    @FormUrlEncoded
    @POST("app/video/like")
    Observable<HttpResult<HttpResult>> videoLike(@Field("userId") String userId,
                                                 @Field("comment_id") String comment_id);

    //视频学习点赞取消
    @FormUrlEncoded
    @POST("app/video/remove")
    Observable<HttpResult<HttpResult>> removeVideoLike(@Field("userId") String userId,
                                                       @Field("comment_id") String comment_id);


    //收藏
    @FormUrlEncoded
    @POST("app/questionnaire/collectSave")
    Observable<HttpResult<HttpResult>> collectSave(@Field("type") int type,
                                                   @Field("userId") String userId,
                                                   @Field("contentId") String contentId);

    //取消收藏
    @FormUrlEncoded
    @POST("app/questionnaire/collectAbolish")
    Observable<HttpResult<HttpResult>> collectAbolish(@Field("type") int type,
                                                      @Field("userId") String userId,
                                                      @Field("contentId") String contentId);

    //视频学习评论
    @FormUrlEncoded
    @POST("app/video/comment")
    Observable<HttpResult<HttpResult>> commentVideo(@Field("essay_id") String essay_id,
                                                    @Field("content") String content,
                                                    @Field("userId") String userId);

    //系列讲话列表
    @FormUrlEncoded
    @POST("app/speak/list")
    Observable<HttpResult<List<RxIndexSpeak>>> speakList(@Field("pageNum") int page);

    //系列讲话列表（登录状态）
    @FormUrlEncoded
    @POST("app/speak/list")
    Observable<HttpResult<List<RxIndexSpeak>>> userSpeakList(@Field("pageNum") int page,
                                                            @Field("userId") String userId);
    //系列讲话搜索列表
    @FormUrlEncoded
    @POST("app/speak/list")
    Observable<HttpResult<List<RxIndexSpeak>>> speakListSearch(@Field("pageNum") int page,
                                                               @Field("count") String count,
                                                               @Field("userId") String userId);
    //系列讲话收藏
    @FormUrlEncoded
    @POST("app/speak/userList")
    Observable<HttpResult<List<RxIndexSpeak>>> speakUserList(@Field("pageNum") int page,
                                                             @Field("userId") String userId);

    //专题学习类别
    @POST("app/content/specialClassify")
    Observable<HttpResult<List<RxLearningClass>>> specialClassify();

    //理论在线类别
    @POST("app/content/theoryClassify")
    Observable<HttpResult<List<RxLearningClass>>> theoryClassify();

    //理论在线列表
    @FormUrlEncoded
    @POST("app/content/theory")
    Observable<HttpResult<RxOnline>> theory(@Field("pageNum") int pageNum,
                                            @Field("classifyId") int classifyId,
                                            @Field("searchValue") String searchValue);

    //理论在线详情
    @FormUrlEncoded
    @POST("app/content/theoryDetails")
//    Observable<HttpResult<RxSpecialDetial>> theoryDetails(@Field("pageNum") int pageNum,
//                                                          @Field("contentId") String contentId,
//                                                          @Field("userId") String userId);
    Observable<HttpResult<RxSpecialDetial>> theoryDetails(@Field("contentId") String contentId,
                                                          @Field("userId") String userId);

    //专题学习列表 收藏
    @FormUrlEncoded
    @POST("app/content/userTheory")
    Observable<HttpResult<RxOnline>> userTheory(@Field("pageNum") int pageNum,
                                                @Field("userId") String userId);

    //专题学习详情
    @FormUrlEncoded
    @POST("app/content/specialDetails")
//    Observable<HttpResult<RxSpecialDetial>> specialDetails(@Field("pageNum") int pageNum,
//                                                           @Field("contentId") String contentId,
//                                                           @Field("userId") String userId);
    Observable<HttpResult<RxSpecialDetial>> specialDetails(
            @Field("contentId") String contentId,
            @Field("userId") String userId);

    //专题学习列表
    @FormUrlEncoded
    @POST("app/content/special")
    Observable<HttpResult<List<RxLearningList>>> special(@Field("pageNum") int pageNum,
                                                         @Field("classifyId") int classifyId,
                                                         @Field("userId") String userId,
                                                         @Field("count") String count);


    //专题学习列表 收藏
    @FormUrlEncoded
    @POST("app/content/userSpecial")
    Observable<HttpResult<List<RxLearningList>>> specialList(@Field("pageNum") int pageNum,
                                                             @Field("userId") String userId);

    //评测列表
    @FormUrlEncoded
    @POST("app/questionnaire/list")
    Observable<HttpResult<List<RxTest>>> testList(@Field("pageNum") int pageNum,
                                                  @Field("userId") String userId);

    //我的评测列表
    @FormUrlEncoded
    @POST("app/questionnaire/userList")
    Observable<HttpResult<List<RxTest>>> userList(@Field("pageNum") int pageNum,
                                                  @Field("userId") String userId);

    //评测详情
    @FormUrlEncoded
    @POST("app/questionnaire/questionnaireDetails")
    Observable<HttpResult<RxTestInfo>> questionnaireDetails(@Field("questionnaire_id") String questionnaire_id);

    //评测详情
    @FormUrlEncoded
    @POST("app/questionnaire/questionnaireRecord")
    Observable<HttpResult<RxTestDoneInfo>> questionnaireRecord(@Field("userquestionnaire_id") String userquestionnaire_id);

    //评测详情
    @FormUrlEncoded
    @POST("app/questionnaire/questionnairecheck")
    Observable<HttpResult<List<RxTestDetial>>> questionnairecheck(@Field("questionnaire_id") String questionnaire_id);

    //答题记录
    @FormUrlEncoded
    @POST("app/questionnaire/questionnaireStatistics")
    Observable<HttpResult<List<RxRecordDetial>>> questionnaireStatistics(@Field("userquestionnaire_id") String userquestionnaire_id);

    //评测详情
    @FormUrlEncoded
    @POST("app/questionnaire/questionnaireSave")
    Observable<HttpResult<HttpResult>> questionnaireSave(@Field("questionnaire_id") String questionnaire_id,
                                                         @Field("subject") String subject,
                                                         @Field("quizTime") int quizTime,
                                                         @Field("userId") String userId);

    //先进典范
    @FormUrlEncoded
    @POST("app/content/paragon")
    Observable<HttpResult<List<RxIndexCommon>>> paragonList(@Field("pageNum") int pageNum,
                                                            @Field("userId") String userId,
                                                            @Field("searchValue") String searchValue);

//    //先进典范
//    @FormUrlEncoded
//    @POST("app/content/userParagon")
//    Observable<HttpResult<List<RxIndexCommon>>> userParagon(@Field("pageNum") int pageNum,
//                                                            @Field("userId") String userId);

    //先进典范详情
    @FormUrlEncoded
    @POST("app/content/paragonDetails")
//    Observable<HttpResult<RxParagonDetial>> paragonDetails(@Field("pageNum") int page,
//                                                           @Field("printurl") String printurl,
//                                                           @Field("contentId") String contentId,
//                                                           @Field("userId") String userId);
    Observable<HttpResult<RxParagonDetial>> paragonDetails(
            @Field("printurl") String printurl,
            @Field("contentId") String contentId,
            @Field("userId") String userId);

    //好书推荐
    @FormUrlEncoded
    @POST("app/content/recommend")
    Observable<HttpResult<List<RxBookRecommend>>> recommend(@Field("pageNum") int pageNum,
                                                            @Field("userId") String userId);
    //好书推荐 搜索
    @FormUrlEncoded
    @POST("app/content/recommend")
    Observable<HttpResult<List<RxBookRecommend>>> userRecommendSearch(@Field("pageNum") int pageNum,
                                                                      @Field("count") String count,
                                                                      @Field("userId") String userId);

    //好书推荐
    @FormUrlEncoded
    @POST("app/content/userRecommend")
    Observable<HttpResult<List<RxBookRecommend>>> userRecommend(@Field("pageNum") int pageNum,
                                                                @Field("userId") String userId);

    //好书推荐详情
    @FormUrlEncoded
    @POST("app/content/recommendDetails")
//    Observable<HttpResult<RxBookDetial>> recommendDetails(@Field("pageNum") int page,
//                                                          @Field("contentId") String contentId,
//                                                          @Field("userId") String userId);
    Observable<HttpResult<RxBookDetial>> recommendDetails(
            @Field("contentId") String contentId,
            @Field("userId") String userId);

    //轮播图
    @POST("app/agreement/advertising")
    Observable<HttpResult<List<RxAds>>> advertising();

    //学习值介绍/关于我们
    @FormUrlEncoded
    @POST("app/agreement/lookContent")
    Observable<HttpResult<String>> lookContent(@Field("type") int type);

    //组织架构 第一级
    @POST("app/user/dept1")
    Observable<HttpResult<List<RxStructureLevelOne>>> dept1();

    //组织架构 第e二级
    @FormUrlEncoded
    @POST("app/user/dept2")
    Observable<HttpResult<List<RxStructureLevelTwo>>> dept2(@Field("deptId") String deptId,
                                                            @Field("userId") String userId);

    //组织架构 第三级
    @FormUrlEncoded
    @POST("app/user/dept3")
    Observable<HttpResult<List<RxStructurePerson>>> dept3(@Field("deptId") String deptId);

    //组织架构 第三级
    @FormUrlEncoded
    @POST("app/userCenter/learningability")
    Observable<HttpResult<List<RxStructurePerson>>> learningability(@Field("deptId") String deptId);

    //组织架构 第三级
    @FormUrlEncoded
    @POST("app/userCenter/applyFor")
    Observable<HttpResult<String>> applyFor(@Field("userId") String userId,
                                            @Field("username") String username,
                                            @Field("deptName") String deptName,
                                            @Field("number") String number,
                                            @Field("title") String title,
                                            @Field("content") String content,
                                            @Field("img") String img);

    //上传图片
    @FormUrlEncoded
    @POST("app/userCenter/upload")
    Observable<HttpResult<String>> upload(@Field("files") String files);

    //登录验证码
    @FormUrlEncoded
    @POST("app/user/sendCode")
    Observable<HttpResult<HttpResult>> loginCode(@Field("phone") String phone
    );

    //注册验证码
    @FormUrlEncoded
    @POST("app/user/sendCodes")
    Observable<HttpResult<HttpResult>> registerCode(@Field("phone") String phone);

    //登录
    @FormUrlEncoded
    @POST("app/user/login")
    Observable<HttpResult<RxMyUserInfo>> login(@Field("phone") String phone,
                                               @Field("code") String code,
                                               @Field("pid") String pid);

    //登录
    @FormUrlEncoded
    @POST("app/user/passwordLogin")
    Observable<HttpResult<RxMyUserInfo>> passwordLogin(@Field("phone") String phone,
                                                       @Field("password") String password,
                                                       @Field("pid") String pid);

    //注册
    @FormUrlEncoded
    @POST("app/user/userRegister")
    Observable<HttpResult<HttpResult>> userRegister(@Field("phone") String phone,
                                                    @Field("code") String code);

    //验证
    @FormUrlEncoded
    @POST("app/user/modifyPhoneAfter")
    Observable<HttpResult<HttpResult>> modifyPhoneAfter(@Field("phone") String phone,
                                                        @Field("code") String code);

    //修改登录密码
    @FormUrlEncoded
    @POST("app/user/revise")
    Observable<HttpResult<HttpResult>> revise(@Field("userId") String userId,
                                              @Field("password") String password);

    //验证
    @POST("app/user/deptName")
    Observable<HttpResult<List<RxDeptName>>> deptName();

    //完善个人信息
    @FormUrlEncoded
    @POST("app/user/perfect")
    Observable<HttpResult<HttpResult>> perfect(@Field("phone") String phone,
                                               @Field("username") String username,
                                               @Field("bl") boolean bl,
                                               @Field("deptId") String deptId,
                                               @Field("position") String position,
                                               @Field("member") int member,
                                               @Field("joinpatryTime") String joinpatryTime,
                                               @Field("password") String password,
                                               @Field("pid") String pid);

    //个人信息
    @FormUrlEncoded
    @POST("app/userCenter/center")
    Observable<HttpResult<RxPersonalCenter>> center(@Field("phone") String phone);

    //个人信息
    @FormUrlEncoded
    @POST("app/userCenter/archives")
    Observable<HttpResult<RxPersonalCenter>> archives(@Field("phone") String phone);

    //党费缴纳
    @FormUrlEncoded
    @POST("app/partyBuild/partyMoney")
    Observable<HttpResult<List<RxPartyFee>>> partyMoney(@Field("userId") String userId);

    //党费详情
    @FormUrlEncoded
    @POST("app/partyBuild/partyMoneyDetails")
    Observable<HttpResult<RxPartyFeeDetial>> partyMoneyDetails(@Field("userId") String userId,
                                                               @Field("duesId") String duesId);

    //缴费记录
    @FormUrlEncoded
    @POST("app/partyBuild/partyMoneyList")
    Observable<HttpResult<List<RxFeeRecord>>> partyMoneyList(@Field("userId") String userId,
                                                             @Field("pageNum") int pageNum);


    //更改用户头像
    @FormUrlEncoded
    @POST("app/userCenter/avatar")
    Observable<HttpResult<HttpResult>> avatar(@Field("phone") String phone,
                                              @Field("avatar") String avatar);

    //更改用户信息
    @FormUrlEncoded
    @POST("app/userCenter/modifyArchives")
    Observable<HttpResult<HttpResult>> modifyArchives(@Field("phone") String phone,
                                                      @Field("joinpatryTime") String joinpatryTime,
                                                      @Field("username") String username,
                                                      @Field("position") String position,
                                                      @Field("deptId ") String deptId);

    //验证手机
    @FormUrlEncoded
    @POST("app/user/modifyPhone")
    Observable<HttpResult<HttpResult>> modifyPhone(@Field("phone") String phone);

    //验证手机
    @FormUrlEncoded
    @POST("app/user/bindPhone")
    Observable<HttpResult<HttpResult>> bindPhone(@Field("phone") String phone);

    //验证
    @FormUrlEncoded
    @POST("app/user/bindPhoneAfter")
    Observable<HttpResult<HttpResult>> bindPhoneAfter(@Field("phone") String phone,
                                                      @Field("code") String code,
                                                      @Field("oldphone") String oldphone);

    //入党申请详情
    @FormUrlEncoded
    @POST("app/userCenter/applyDetail")
    Observable<HttpResult<RxApplyDetial>> applyDetail(@Field("userId") String userId);

    //入党申请详情
    @FormUrlEncoded
    @POST("app/userCenter/isApplyFor")
    Observable<HttpResult<RxIsApplyFor>> isApplyFor(@Field("userId") String userId);

    //月学习值
    @FormUrlEncoded
    @POST("app/userCenter/learnMonths")
    Observable<HttpResult<List<RxMonthScore>>> learnMonths(@Field("phone") String phone,
                                                           @Field("date") String date,
                                                           @Field("pageNum") int pageNum);

    //年学习值
    @FormUrlEncoded
    @POST("app/userCenter/learningability")
    Observable<HttpResult<List<RxTotalScore>>> learningability(@Field("phone") String phone,
                                                               @Field("pageNum") int pageNum);

    //添加学习值
    @FormUrlEncoded
    @POST("app/learningability/addLearningability")
    Observable<HttpResult<HttpResult>> addLearningability(@Field("userId") String userId,
                                                          @Field("type") int type,
                                                          @Field("time") int time,
                                                          @Field("contentId") String contentId);

    //年学习值
    @FormUrlEncoded
    @POST("app/userCenter/install")
    Observable<HttpResult<RxSetting>> install(@Field("phone") String phone);

    //签到信息
    @FormUrlEncoded
    @POST("app/partyBuild/sign")
    Observable<HttpResult<RxSign>> sign(@Field("activityId") String activityId,
                                        @Field("userId") String userId);

    //签到信息
    @FormUrlEncoded
    @POST("app/partyBuild/signDetails")
    Observable<HttpResult<RxSignResult>> signDetails(@Field("activityId") String activityId,
                                                     @Field("userId") String userId,
                                                     @Field("site") String site);


    //保存播放记录
    @FormUrlEncoded
    @POST("app/video/saveplayrecord")
    Observable<HttpResult<HttpResult>> saveplayrecord(@Field("comment_id") String comment_id,
                                                      @Field("userId") String userId,
                                                      @Field("playtime") int playtime);


    //第三方登录
    @FormUrlEncoded
    @POST("app/userCenter/thirdLogin")
    Observable<HttpResult<RxMyUserInfo>> thirdLogin(@Field("openId") String openId,
                                                    @Field("type") int type,
                                                    @Field("pid") String pic);

    //获取微信授权
    @FormUrlEncoded
    @POST("https://api.weixin.qq.com/sns/oauth2/access_token")
    Observable<HttpResult<HttpResult>> wxToken(@Field("appid") String appid,
                                               @Field("secret") String secret,
                                               @Field("code") String code,
                                               @Field("grant_type") String grant_type);

    //绑定微信qq
    @FormUrlEncoded
    @POST("app/userCenter/band")
    Observable<HttpResult<RxMyUserInfo>> band(@Field("phone") String phone,
                                              @Field("type") int type,
                                              @Field("code") String code,
                                              @Field("openId") String openId);


    //退出登录
    @FormUrlEncoded
    @POST("app/user/exit")
    Observable<HttpResult<HttpResult>> exit(@Field("userId") String userId);


}
