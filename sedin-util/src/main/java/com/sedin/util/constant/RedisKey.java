package com.sedin.util.constant;

/**
 * Created by Administrator on 2016/4/1.
 */
public class RedisKey {

    //异步保存日志
    public final static String log_sync_save_key = "log_sync_save";

    //异步消息
    public final static String msg_sync_key = "msg_sync";

    //登录记录消息处理
    public final static String app_login_sync_key = "app_user_sync";

    public final static String user_login_prefix = "login_";//登录用户

    public final static String user_manage_login_prefix = "m_login_";//管理系统登录用户

    public final static String user_app_login_prefix = "app_login_";//移动端登录用户

    public final static String boss_login_prefix = "boss_login_";//boss端登录用户

    public final static String system_all_res = "sytem_all_res";//系统所有资源菜单

    public final static String system_param = "system_param";//系统参数

    public final static String user_login_res_prefix = "login_emp_res_";//登录用户资源暂未使用
    
    public final static String user_res_access = "user_res_access_";//用户access_token
    //管理系统所有资源菜单
    public final static String manage_system_menu = "manage_system_menu";

    public final static int user_login_valid_time = 24 * 60 * 60;//登录用户有效时间

    public final static String employee_account_incr_count = "employee_account_count";//员工账号计数器

    public final static String mobile_register_sms = "mobile_register_sms_";//手机注册:短信验证码

    public final static String areaAgent_api = "areaAgent_api";//地区
    //在线的员工:数据来源于websocket，用于后台统计校准
    public final static String online_employee = "online_employee";
    //门店缓存
    public final static String shop_key = "shop_key";
    //门店统计缓存
    public final static String shop_stat_key = "shop_stat_";
    //门店统计缓存-统计每天的
    public final static String shop_day_stat_key = "shop_day_stat_";
    //保证金统计缓存
    public final static String bond_stat_key = "bond_stat_";
    //用户对应机器码缓存
    public final static String jpush_user_code_key = "jpush_user_code";
    public final static String ll_update_course_name = "ll_update_course_name";



    public static String getShopStatKey(String date, Long shopId) {
        return shop_stat_key + shopId + "_" + date;
    }

    public static String getShopDayStatKey(String date, Long shopId) {
        return shop_day_stat_key + shopId + "_" + date;
    }

    public static String getBondStatKey(String date, Long shopId) {
        return bond_stat_key + shopId + "_" + date;
    }


    //web pad
    public final static String web_pad_register_sms = "web_pad_register_sms_";    //点单pad注册发送验证码
    public final static String pad_login_prefix = "pad_login_";//pad端登录用户


    //11369 start

    public final static String user_ll369_manage_login_prefix = "m_ll369_login_";//管理系统登录用户

    public final static String user_ll369_app_login_prefix = "app_ll369_login_";//管理系统登录用户

    public final static String ll369_system_param = "ll369_system_param";//龙链369系统参数 散列

    public final static String ll369_manage_system_menu = "ll369_manage_system_menu";    //LL369管理系统所有资源菜单

    public final static String longlian_console_system_menu = "longlian_console_system_menu";    //龙链console系统所有资源菜单

    public final static String longlian_res_system_menu = "longlian_res_system_menu";    //龙链res系统所有资源菜单

    public final static String longlian_console_system_menu_time = "longlian_console_system_menu_time"; //龙链console系统所有资源菜单更新时间

    public final static String longlian_res_system_menu_time = "longlian_res_system_menu_time"; //龙链res系统所有资源菜单更新时间

    public final static int ll369_app_user_login_valid_time = 5 * 24 * 60 * 60;//登录用户有效时间

    public final static String ll369_app_advertising = "ll369_app_advertising";//广告 list 首页
    public final static String ll369_app_advertising_lecturer = "ll369_app_advertising_lecturer";//广告 list 老师

    public final static String ll369_app_video_type = "ll369_app_video_type";//视频类型 list

    public final static String ll369_app_recommend_video = "ll369_app_recommend_video";//推荐的视频 list

    public final static String ll369_app_user_invitation_code = "ll369_app_user_invitation_code";//邀请码采用  散列

    public final static String ll369_app_user_reward_rule = "ll369_app_user_reward_rule";//用户奖励规则 list

    public final static String ll369_app_user_level = "ll369_app_user_level";//用户级别 list

    public final static String ll369_app_bank = "ll369_app_bank";//银行

    public final static String ll369_mobile_register_sms = "ll369_mobile_register_sms";//369.交易密码验证码

    public final static String ll369_msg_sync_key = "ll369_msg_sync";    //异步消息

    public final static String ll369_app_home_last_update_date = "ll369_app_home_last_update_date";    //APP端首页最后一次更新日期

    public final static String ll369_log_sync_save_key = "ll369_log_sync_save_key";//异步保存日志

    public final static String jpush_369user_code_key = "jpush_369user_code";     //用户对应机器码缓存

    public final static String ll369_app_hot_video = "ll369_app_hot_video";            //369热门视频搜索

    public final static String ll369_app_hot_video_group = "ll369_app_hot_video_group";            //369热门视频集搜索

    public final static String ll369_active_user_key = "ll369_active_user_"; //369活跃用户

    public final static String ll369_new_user_key = "ll369_new_user_"; //369新增用户

    public final static String ll369_new_vip_user_key = "ll369_new_vip_user_"; //369新增vip用户

    public final static String ll369_start_count_key = "ll369_start_count_"; //369启动计数

    public final static String ll369_play_count_key = "video_";  //369视频计数器

    public final static String ll369_Gold_lecture = "ll369_Gold_lecture";    //369首页金牌讲师

    public final static String ll369_lecture = "lecture_";//369讲师

    public final static String ll369_videoType = "videoType_";//视频分类

    public final static String ll369_videoType_banner = "videoType_banner_";//视频分类banner图

    public final static String ll369_index_video_group = "ll369_index_video_group";    //369首页视频集合

    public final static String ll369_video_praise_key = "ll369_video_praise_";        //369点赞

    public final static String ll369_video_praise_wait2db = "video_praise_wait2db";    //点赞数据库存储队列

    public final static String ll369_videotype_video_key = "videotype_videogroup_";//视频分类下的课程

    public final static String ll369_video_play_count_key = "video_play"; //视频播放量

    public final static String ll369_video_play_count_wait2db = "video_play_wait2db"; //视频播放量数据库存储队列

    public final static String ll369_ios_apply = "iOS_apply_version"; //IOS审核版本开关

    public final static String ll369_ios_apply_video_is_freee = "iOS_apply_video_is_free"; //IOS审核视频是否免费开关 0-正常  1-免费

    public final static String ll369_pref_count = "ll369_pref_count";    //vip充值,一级,二级代理的数量

    public final static String ll369_user_pref_count_wait2db = "ll369_user_pref_count_wait2db";//vip充值,一级,二级代理的数量(存储队列)

    public final static String ll369_push_msg = "push_msg";//发送推送消息(队列)
    
    public final static String ll369_push_msg_repeat = "push_msg_repeat_";//已经发送推送消息的id
    
    public final static String ll369_cloud = "ll369_cloud";//龙链科技展示排行榜

    public final static String ll369_Ranking_cloud = "ll369_Ranking_cloud";

    public final static String ll369_Proxy_level = "ll369_Proxy_level"; //369代理等级

    /**
     * 用户使用客户端的版本号和type(android或ios)
     */
    public final static String ll369_app_type_version = "ll369_app_type_version";

    /**
     * 存放二维码，key:ll369_share_img_mobile_code
     */
    public final static String ll369_share_img = "ll369_share_img";


    public final static String ll369_h5_third_user_login_token = "ll369_h5_third_user_login_token";//第三方系统的登录token
    public final static int ll369_h5_third_user_login_token_valid_time = 3 * 60;//第三方系统登录
    public final static String ll369_weixin_access_token = "ll369_weixin_access_token";//微信accessTokenkey
    public final static String ll369_weixin_jsapi_ticket = "ll369_weixin_jsapi_ticket";//微信js 授权票据
    public final static int ll369_weixin_access_token_use_time = 1 * 60 * 60;//微信accessToken有效时间
    public final static String ll369_h5_unbundling_tel = "ll369_h5_unbundling_tel";    //微信更改手机绑定

    public final static String ll369_star_lectuer_one = "star_lectuer_one"; //明星讲师(主)
    public final static String ll369_star_lectuer_two = "star_lectuer_two"; //明星讲师(辅1)
    public final static String ll369_star_lectuer_three = "star_lectuer_three"; //明星讲师(辅2)

    public final static String ll369_share_moment_ward = "ll369_share_moment_ward";//分享朋友圈ID_TIME  KEY  1000_2016-12-19
    public final static int ll369_share_moment_ward_valid_time = 24 * 60 * 60;//分享朋友圈奖励时间


    public final static String ll369_proxy_app_id = "ll369_proxy_app_id";//采用散列 就是存APPId，判断是否是代理
    //11369 end

    //2016年抽奖开始
    public final static  String annual_meeting_draw_for_2016_phone = "annual_meeting_draw_for_2016_phone";//2016年抽奖所有手机号 list
    public final static  String annual_meeting_draw_for_2016_select_phone = "annual_meeting_draw_for_2016_select_phone";//2016年抽奖抽中手机号 list
    //2016年抽奖结束

    //第三方用户登录
    public final static String ll_live_third_login_prefix = "ll_live_third_login_";//第三方用户登录

    //龙链直播start
    public final static String ll_live_weixin_login_prefix = "ll_live_app_login_";//app和微信用户登录时间
    //用户登录有效时间
    public final static int  ll_live_app_user_login_valid_time= 5 * 24 * 60 * 60;//登录用户有效时间

    public  final  static String ll_live_component_verify_ticket = "ll_live_component_verify_ticket";//微信第三方平台component_verify_ticket

    public final static String ll_live_mobile_register_sms_tra = "ll_live_mobile_register_sms_tra";//369.交易密码验证码
    public final static String ll_live_mobile_register_sms = " ll_live_mobile_register_sms_";//手机注册:短信验证码

    public final static String ll_live_third_component_access_token = "ll_live_third_component_access_token";//微信第三方平台component_access_token
   // public final static String ll_live_weixin_access_token = "ll_live_weixin_access_token";//微信token
    public final static String ll_live_weixin_jsapi_ticket = "ll_live_jsapi_ticket";//微信js 授权票据
    public final static int ll_live_access_token_use_time =  7000 ;//微信accessToken有效时间，微信为7200秒
    public final static String ll_live_appid_use_authorizer_room_info= "ll_live_appid_use_authorizer_room_info";//授权公众号微信(有效的) 信息--采用散列形式 key-直播间Id   value-wechat_official appid
    public final static String ll_live_appid_access_token_pre= "ll_live_appid_access_token_";//授权公众号微信accessToken前缀
    public final static String ll_live_third_auth_wechat_qrcode_next_num = "ll_live_third_auth_wechat_qrcode_next_num";//关注第三方授权的二维码生成带参数二维码最大值
    public final static String ll_live_third_auth_wechat_qrcode_pre= "ll_live_third_auth_wechat_qrcode_pre_";//关注第三方授权的二维码生成带参数二维码前缀
    //public final static int ll_live_third_auth_wechat_qrcode_use_time= 1 * 60 * 60;//第三方授权的二维码生成带参数二维码有效时间 一个小时

    public final static String ll_live_app_advertising = "ll_live_app_advertising";//广告 list 首页
    public final static String ll_live_course_type = "ll_live_app_course_type";//课程类型 首页

    //龙链直播老师登录
    public final static String ll_live_teacher_app_login_prefix = "ll_live_teacher_app_login_prefix";

    //龙链直播老师web登录
    public final static String ll_live_teacher_web_login_prefix = "ll_live_teacher_web_login_prefix";

    public final static String ll_chat_room_msg_wait2db = "ll_chat_room_msg_wait2db";//聊天室消息(存储队列)

    public final static String ll_chat_room_msg_By_chatRoomId = "ll_chat_room_msg_By_chatRoomId";
    public final static int   ll_chat_room_msg_By_chatRoomId_valid_time =24*60*60;


    public final static String ll_live_system_param = "ll_live_system_param";//视频直播系统参数 散列
    public final static String ll_log_sync_save_key = "ll_log_sync_save_key";//异步保存日志

    public final static String ll_live_invi_card = "ll_live_invi_card"; //邀请卡模板

    public final static String ll_live_user_distribution_record = "ll_live_user_distribution_record"; //分销记录 散列类型
    public final static String ll_live_teach_course_last_reward_time= "ll_live_teach_course_last_reward_time"; //老师讲课最后一次奖励时间 ，散列类型 key-老师ID value-最后一次奖励时间
    public final static String ll_live_reward_success_invitation_teach_record = "ll_live_reward_success_invitation_teach_record"; //推荐老师 成功过的老师记录--> set 存放
    public final static String ll_live_follow_reward_rule="ll_live_follow_reward_rule";//老师粉丝关注奖励规则
    public final static String ll_live_follow_reward_record_="ll_live_follow_reward_record_";//老师粉丝关注奖励记录（set ，每个老师一个key）
    public final static String ll_user_follow_key = "ll_user_follow_";        //关注直播间
    public final static String ll_live_create_course_send_wechat_messsage = "ll_live_create_course_send_wechat_messsage";//创建课程发送微信模板消息库存储队列

    public final static String ll_live_follow_Liveroom_send_wechat_messsage = "ll_live_follow_Liveroom_send_wechat_messsage";//用户关注存储队列


    public final static String ll_live_share_course_or_room_card_send_wechat_messsage = "ll_live_share_course_or_room_card_send_wechat_messsage";//分享直播间或者课程邀请卡发送微信模板消息库存储队列

    public final static String ll_user_follow_wait2db = "follow_user_follow_wait2db";    //关注直播间数据库存储队列

    public final static String ll_app_msg_wait2db = "ll_app_msg_wait2db";//消息库存储队列

    public final static String ll_join_course_user_key = "ll_join_course_user_";//报名加入课程的人员列表
    public final static String ll_live_visit_course_record_wait2db= "ll_live_visit_course_record_wait2db"; //观看直播记录数据库存储队列

    public final static String ll_live_visit_course_record = "ll_live_visit_course_record"; //观看直播记录

    public final static String ll_cache_visit_record_record = "ll_visit_record_record_"; //缓存查看详情记录

    public final static String ll_jpush_user_code_key = "ll_jpush_user_code";     //用户对应机器码缓存

    public final static String ll_user_machine_info_wait2db = "ll_user_machine_info_wait2db";     //用户对应机器情况

    public final static String ll_user_active_zset = "ll_user_active_zset";     //用户活动zset时间


    public final static String ll_course_end_event_key = "ll_course_end_event";     //结束课程事件

    public final static String ll_live_trade_password_sms = "ll_live_trade_password_sms";//忘记交易密码:短信验证码

    //老师开课时间提醒
    public final static String ll_live_teacher_course_remind="ll_live_teacher_course_remind";
    //学生开课时间提醒
    public final static String ll_live_student_course_remind="ll_live_teacher_course_remind";
    //直播通道
    public final static String ll_live_channel="ll_live_channel";
    //可以直播的通道
    public final static String ll_live_channel_can_use="ll_live_channel_can_use";
    //已经用了的直播的通道
    public final static String ll_live_channel_using="ll_live_channel_using";
    //直播通道统计key
    public final static String ll_live_channel_using_counts="ll_live_channel_using_count";
    public final static int   ll_live_channel_using_counts_valid_time =3*24*60*60;//直播通道统计key有效时间
    
    //直播ID和通道code关联
    public final static String ll_live_channel_rel="ll_live_channel_rel_";
    //直播ID和通道code反关联
    public final static String ll_ID_live_channel_rel="ll_ID_live_channel_rel_";

    public final static String ll_study_record_wait2db = "ll_study_record_wait2db";//学习记录数据库存储队列

    public final static String ll_channel_visit_record_wait2db = "ll_channel_visit_record_wait2db";//渠道访问记录数据库存储队列

    public final static String ll_button_visit_record_wait2db = "ll_button_visit_record_wait2db";//按钮点击数据库存储队列

    public final static String ll_url_visit_record_wait2db = "ll_url_visit_record_wait2db";//页面地址点击数据库存储队列


    public final static String ll_join_count_wait2db = "ll_join_count_wait2db";//报名数最数据库存储队列

    //public final static String ll_new_user_count_key = "ll_new_user_count_"; //新增用户记数
    public final static String ll_new_user_key = "ll_new_user_"; //新增用户
    //public final static String ll_new_teacher_count_key = "ll_new_teacher_count_"; //新增老师记数
    public final static String ll_new_teacher_key = "ll_new_teacher_"; //新增老师记数
    public final static String ll_active_user_key = "ll_active_user_"; //活跃用户
    public final static String ll_pv_count_key = "ll_pv_count_"; //总pv记数
    public final static String ll_pay_user_key = "ll_pay_user_"; //付费人数
    public final static String ll_pay_amt_count_key = "ll_pay_amt_count_"; //付费金额
    public final static String ll_new_course_key = "ll_new_course_"; //新课程记录
    public final static String ll_new_pay_course_key = "ll_new_pay_course_"; //付费课程记录

    public final static String ll_live_img_shot_wait2db = "ll_live_img_shot_wait2db";//直播流截图事件
    public final static String ll_userfollow_readed_sync_key = "ll_userfollow_readed_sync_key";//关注我的直播间设为已看状态

    public final static String ll_live_pre_teacher_course_remind = "ll_live_pre_teacher_course_remind";// 课程提醒key
    public final static int   ll_live_pre_teacher_course_valid_time =10 * 60;//课程提醒key有效时间

    public final static String ll_console_count_key = "ll_console_yesterday_count_key";// 统计昨天key
    public final static int   ll_console_count_valid_time =2*24*60*60;//统计有效时间

//    public final static String ll_console_week_count_key = "ll_console_week_count_key";// 统计周key
//    public final static int   ll_console_week_count_valid_time =2*24*60*60;//统计周有效时间
//
//    public final static String ll_console_month_count_key = "ll_console_month_count_key";// 统计月key
//    public final static int   ll_console_month_count_valid_time =2*24*60*60;//统计月有效时间
//
//    public final static String ll_console_year_count_key = "ll_console_year_count_key";// 统计年key
//    public final static int   ll_console_year_count_valid_time =2*24*60*60;//统计年有效时间
    /**
     * 用户使用客户端的版本号和type(android或ios)
     */
    public final static String ll_app_type_version = "ll_app_type_version";

    public final static String ll_binding_mobile = "ll_binding_mobile"; //绑定手机号

    public final static String ll_ios_pay_type = "ll_ios_pay_type"; //IOS支付信息

    public final static String ll_live_user_reward_type = "ll_live_user_reward_type"; //用户打赏类型列表 list

    public final static String ll_course_base_join_num = "ll_course_base_join_num"; //加入课程基数

    public final static String ll_course_base_visit_num = "ll_course_base_visit_num"; //访问课程详情基数

    public final static String android_pay_type = "android_pay_type"; //安卓支付信息

    public final static String ll_img_upload_sys_compress = "ll_img_upload_sys_compress"; //图片上传压缩民步处理

    public final static String ll_series_course_update_time_wait2db= "ll_series_course_update_time_wait2db"; //系列课时间更新

    public final static String ll_course_class_index = "ll_course_class_index_"; //根据课程id获取该课程的相关课件页码

    public final static String ll_page_url = "ll_page_url";//页面地下存方

    public final static String ll_page_visit_user = "ll_page_visit_user_";//页面访问人员

    //app用户存入redis opendid  map散列 key--> openid value-->id
    public final static String ll_live_app_user_by_openid="ll_live_app_user_by_openid";
    //app用户存入redis unionid  map散列 key--> unionid value-->id
    public final static String ll_live_app_user_by_unionid="ll_live_app_user_by_unionid";

    public  final static String ll_create_yunxin_user = "ll_create_yunxin_user"; //创建用户队列

    public final static String ll_lock_pre="ll_lock_";//分布式锁前缀

    public final static String ll_join_lock_pre="ll_join_lock_";//分布式加入课程锁前缀

    public final static String ll_load_user_reward_lock_pre = "ll_load_user_reward_lock_" ;//分布式加载打赏锁前缀

    public  final static String ll_yunxin_token_temp = "ll_yunxin_token_temp_"; //创建用户yuntoken临时存储

    public final static String ll_user_reward = "ll_user_reward_" ;//打赏数据存储

    public final static String ll_user_info = "ll_user_info" ;//缓存用户信息hash

    public final static String ll_course_join_user_deal = "ll_course_join_user_deal";//课程人员处理

    public final static String ll_join_course_first_user = "ll_join_course_first_user_";//报名加入课程的第一个人

    public final  static String ll_course_show_users = "ll_course_show_users_";//课程直播间显示的用户

    public final  static String  ll_course_show_all_users  = "ll_course_show_all_users_";//课程直播间显示其它的用户

    public final static String ll_course_user_income_queue = "ll_course_user_income_queue";//课程打赏收益变化

    public final static String ll_live_web_user_navigation_record="ll_live_user_navigation_record";//web端用户引导记录 三列 key-appId value-WebNavigationType.val的字符串，以逗号分隔，如,1,2，

    public final static String ll_live_join_course_record_pre = "ll_live_join_course_record_";//购买课程记录存入 redis，每节课一个，设置过期时间5天 ，散列 key-appId ,value-购买状态SIGN_UP_STATUS 0-购买中1-购买成功 2-失败
    public final static int   ll_live_join_course_record_use_time =5*24*60*60;//购买课程记录存入redis ,设置过期时间5天

    public final static String ll_course_manager_real = "ll_course_manager_real_"; //课程场控人员信息

    public final static String ll_course_share_title = "ll_course_share_title_"; //课程分享

    public final static String ll_add_virtual_user = "ll_add_virtual_user"; //创建课程增加虚拟用户队列

    public final static String ll_all_virtual_userid = "ll_all_virtual_userid";//所有的虚拟用户

    public final static String ll_live_manager_appId = "ll_live_manager_appId_"; //直播间的管理人员

    public final static String ll_course_live_connected = "ll_course_live_connected_";//直播流连接情况

    public final static String ll_channel_visit_record = "ll_channel_visit_record_";//

    public final static String ll_course_ware = "ll_course_ware_" ;//课程课件

    public final static String ll_course_ware_lock_pre = "ll_course_ware_lock_" ;//课程课件锁

    public final static String ll_set_vertical_coverss_img = "ll_set_vertical_coverss_img";//后台处理竖屏直播背景图

    public static final String ll_course = "ll_course_";//课程详情缓存

    public static final String ll_chatroom_address = "ll_chatroom_address_";//课程聊天室地址

    public static final String ll_course_manager_wait2db = "ll_course_manager_wait2db_";//课程场控使用

    public static final String ll_live_create_course_roominfo = "ll_live_create_course_roominfo" ;//创建课程，设置roominfo

    public static final String ll_create_chatroom_lock = "ll_create_chatroom_lock_"; //创建课程，创建聊天室，锁，避免后台任务同时创建

    public static final String  ll_chat_room_temp = "ll_chat_room_temp_";//创建聊天室临时存储

    public static final String ll_live_room_manager = "ll_live_room_manager_";//直播间后台场控设置

    public static final String ll_live_push_notify = "ll_live_push_notify";

    public static final String ll_live_room_create = "ll_live_room_create_"; //直播间后台添加场控人员

    public static final String ll_room_manager_lock = "ll_room_manager_lock_";//直播间后台添加场控人员 redis 锁

    public static final String ll_room_manager_lock_temp = "ll_room_manager_lock_temp_";//直播间后台添加场控人员 redis 锁

    public static final String ll_set_user_gag = "ll_set_user_gag_"; //设置用户禁言

    public static final String ll_set_user_gag_wait2db = "ll_set_user_gag_wait2db_"; //设置用户禁言 mq

    public static final String ll_del_user_gag_wait2db = "ll_del_user_gag_wait2db_"; //取消用户禁言 mq

    public static final String ll_class_img_wait2db = "ll_class_img_wait2db"; //编辑课程图片 mq

    public  static final String  ll_is_open_qr = "ll_is_open_qr";//是否打开二维码功能

    public static final String ll_invi_code = "ll_invi_code";   //邀请码去重

    public static final String ll_get_invi_code_course = "ll_get_invi_code_course_";   //已经抢到的课程的邀请码 ，散列 key:appid value：邀请码

    public static final String ll_get_no_invi_code_course = "ll_get_no_invi_code_course";   //未被抢的邀请码 队列

    public static final String ll_invi_code_use_time = "ll_invi_code_use_time"; //邀请码使用时间

    public static final String ll_get_use_invi_code_course_wait2db = "ll_get_use_invi_code_course_wait2db_";//抢到的邀请码去mq处理  .list队列

    public static final String ll_create_video_wait2db = "ll_create_video_wait2db";//创建课程视频 .list队列

    public static final String ll_video_convert_pre = "ll_video_convert_"; //视频转换结果缓存

    public static final String ll_course_garbage = "ll_course_garbage";//课程图文签别

    public static final String ll_clear_chatmsg = "ll_clear_chatmsg";//清理聊天消息

    public static final String ll_is_create_course ="ll_is_create_course_";//创建课标志

    public static final String ll_series_course_join_user = "ll_series_course_join_user";//系列课加入人员后，将下面的单节课加入人员
    //龙链直播end
}
