package com.sedin.util.weixin.oauth2;


import com.sedin.util.Utility;
import com.sedin.util.weixin.ParamesAPI.*;
import com.sedin.util.weixin.type.WechatMedieType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * OAuth2类
 * @author syl
 * @date 2016.10.14
 */
public class OAuth2Core {
	private static Logger log = LoggerFactory.getLogger(OAuth2Core.class);	/**
	 * 根据code获取用户信息
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 *            appid 服务号appid
	 *            secret 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
	 * */
	public static WeixinApp getUser( String code , String appid  , String secret) {
		String CODE_TO_USERINFO_NEW = ParamesUriAPI.GET_APP.replace("APPID",appid).replace("SECRET",secret ).replace("CODE", code);
		JSONObject jsonobject = HttpRequestUtil.httpRequest(CODE_TO_USERINFO_NEW, "GET", null);
		WeixinApp weixinApp = null;
		if (null != jsonobject) {
			if(jsonobject.containsKey("access_token")){
				log.info("{}",jsonobject);
				String access_token = jsonobject.getString("access_token");//网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
				String refresh_token = jsonobject.getString("refresh_token");//access_token接口调用凭证超时时间，单位（秒）
				String openid = jsonobject.getString("openid");//用户刷新access_token
				String scope =  jsonobject.getString("scope");//用户授权的作用域，使用逗号（,）分隔
				weixinApp = new WeixinApp();
				weixinApp.setOpenid(openid);
				weixinApp.setAccess_token(access_token);
				weixinApp.setRefresh_token(refresh_token);
				weixinApp.setScope(scope);
			} else {
				log.info("{}", jsonobject);
			}
		} else {
			log.error("获取授权失败了，自己找原因。。。");
		}
		return weixinApp;
	}


	/**
	 * 根据code获取用户信息
	 * 			  调用接口凭证
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * */
	public static WeixinAppUser getUserAppUser( String code, String appid  , String secret) {
		WeixinApp weixinApp =  getUser(code, appid, secret);
		WeixinAppUser weixinAppUser = getUserAppUser( weixinApp );
		return weixinAppUser;
	}
	/**
	 * 获取微信信息
	 * @param weixinApp
	 * */
	public static WeixinAppUser getUserAppUser( WeixinApp weixinApp ) {
		WeixinAppUser weixinAppUser = null;
		if(weixinApp != null ){
			String GET_APP_USER_INFO_NEW = ParamesUriAPI.GET_APP_USER_INFO.replace("ACCESS_TOKEN", weixinApp.getAccess_token()).replace("OPENID", weixinApp.getOpenid());
			JSONObject jsonobject = HttpRequestUtil.httpRequest(GET_APP_USER_INFO_NEW, "GET", null);
			if (null != jsonobject) {
				weixinAppUser = new WeixinAppUser();
				weixinAppUser.setAccess_token(weixinApp.getAccess_token());
				weixinAppUser.setOpenid(weixinApp.getOpenid());//用户唯一标示
				if(jsonobject.containsKey("errcode")){
					log.info("{}", jsonobject);
					return  weixinAppUser;
				}
				weixinAppUser.setNickname(jsonobject.getString("nickname"));//用户昵称
				weixinAppUser.setSex(jsonobject.getString("sex"));//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
				weixinAppUser.setProvince(jsonobject.getString("province"));//用户个人资料填写的省份
				weixinAppUser.setCity(jsonobject.getString("city"));//普通用户个人资料填写的城市
				weixinAppUser.setCountry(jsonobject.getString("country"));//	国家，如中国为CN
				weixinAppUser.setHeadimgurl(jsonobject.getString("headimgurl"));//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
				weixinAppUser.setPrivilege(jsonobject.getString("privilege"));//用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
				String unionid = "";
				if(jsonobject.containsKey("unionid")){
					unionid = jsonobject.getString("unionid");
				}
				weixinAppUser.setUnionid(unionid);//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
			}else{
				System.out.println("获取用户授权失败了，自己找原因。。。");
			}
		}
		return weixinAppUser;
	}


	/**
	 * 根据code获取用户信息
	 * 			  调用接口凭证
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * */
	public static WeixinAppUser getUserAppUserContainIsFollowId( String code, String appid  , String secret , String accessToken) {
		WeixinAppUser weixinAppUser = null;
		WeixinApp weixinApp =  getUser(code, appid, secret);
		if(weixinApp != null ){
			String GET_APP_USER_INFO_NEW = ParamesUriAPI.GET_APP_USER_INFO_CONTAIN_IS_FOLLOW.replace("ACCESS_TOKEN",accessToken).replace("OPENID", weixinApp.getOpenid());
			JSONObject jsonobject = HttpRequestUtil.httpRequest(GET_APP_USER_INFO_NEW, "GET", null);
			if (null != jsonobject) {
				weixinAppUser = new WeixinAppUser();
				if(jsonobject.containsKey("errcode")){
					log.error("{}", jsonobject);
				}
				weixinAppUser.setSubscribe(jsonobject.getString("subscribe"));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
				if("0".equals(weixinAppUser.getSubscribe())){//如果没有关注，则从网页授权获取
					weixinAppUser = getUserAppUser( weixinApp );
					weixinAppUser.setSubscribe("0");
					weixinAppUser.setIsFollowLlWechat("0");
					return weixinAppUser;
				}
				weixinAppUser.setIsFollowLlWechat("1");
				weixinAppUser.setAccess_token(weixinApp.getAccess_token());
				weixinAppUser.setOpenid(weixinApp.getOpenid());//用户唯一标示
				weixinAppUser.setNickname(jsonobject.getString("nickname"));//用户昵称
				weixinAppUser.setSex(jsonobject.getString("sex"));//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
				weixinAppUser.setProvince(jsonobject.getString("province"));//用户个人资料填写的省份
				weixinAppUser.setCity(jsonobject.getString("city"));//普通用户个人资料填写的城市
				weixinAppUser.setCountry(jsonobject.getString("country"));//	国家，如中国为CN
				weixinAppUser.setHeadimgurl(jsonobject.getString("headimgurl"));//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
				String unionid = "";
				if(jsonobject.containsKey("unionid")){
					unionid = jsonobject.getString("unionid");
				}
				weixinAppUser.setUnionid(unionid);//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
			} else {
				System.out.println("获取用户授权失败了，自己找原因。。。");
			}
		}
		return weixinAppUser;
	}



	/**
	 * 获取access_token
	 *
	 * @param appid
	 *            appId
	 * @param secret
	 *            管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String secret) {
		AccessToken accessToken = null;
		String requestUrl = ParamesUriAPI.access_token_url.replace("APPID", appid).replace("APPSECRET", secret);
		JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				System.out.println("获取token成功:" + jsonObject.getString("access_token") + "————" + jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				// 获取token失败
				log.info("{}", jsonObject);
			}
		}
		return accessToken;
	}


	/**
	 * 获取获取JSAPI token
	 * @param accessTokenStr  公众号access_token
	 * @return
	 */
	public static AccessToken getJSAPITicket(String accessTokenStr) {
		AccessToken accessToken = null;
		String requestUrl = ParamesUriAPI.jsapi_access_token_url.replace("ACCESS_TOKEN", accessTokenStr);
		JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("ticket"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				System.out.println("获取js_ticket成功:" + jsonObject.getString("ticket") + "————" + jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				// 获取token失败
				log.info("{}", jsonObject);
			}
		}
		return accessToken;
	}


	public static JSONObject  uploadImg(  String access_token  ,String imgPath ) {
		String new_url = ParamesUriAPI.upload.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = HttpRequestUtil.httpRequestFile(new_url, imgPath, "thumb");
		WeixinApp weixinApp = null;
		if (null != jsonobject) {
			System.out.println(jsonobject+"-----------------------------");
			if(jsonobject.containsKey("type")){
				String typeI = jsonobject.getString("type");//授权方appid
				String media_id = jsonobject.getString("thumb_media_id");//
				Long created_at = jsonobject.getLong("created_at");//
				System.out.println(typeI + "-->" +media_id + "--->" + created_at );
				return jsonobject;
			} else {
				log.info("{}", jsonobject);
			}
		} else {
			System.out.println("获取授权失败了，自己找原因。。。");
		}
		return jsonobject;
	}
	/**
	 *  上传
	 * @param   access_token   公众号token
	 * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param imgPath 文件路径
	 * @return
	 * @throws Exception
	 */
	public static JSONObject  upload(  String access_token    ,String type,String imgPath ) {
		String new_url = ParamesUriAPI.upload.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = HttpRequestUtil.httpRequestFile(new_url, imgPath, type);
		WeixinApp weixinApp = null;
		if (null != jsonobject) {
			if(jsonobject.containsKey("type")){
				String typeI = jsonobject.getString("type");//授权方appid
				String media_id = jsonobject.getString("media_id");//
				Long created_at = jsonobject.getLong("created_at");//
				return jsonobject;
			} else {
				log.info("{}", jsonobject);
			}
		} else {
			System.out.println("获取授权失败了，自己找原因。。。");
		}
		return jsonobject;
	}


	/**
	 * 上传图文素材
	 * @param access_token  公众号token
	 * @param title 标题
	 * @param thumb_media_id 缩略图Id
	 * @param url 跳转路径
	 * @param digest 图文消息的描述
	 * @param show_cover_pic  是否显示封面，1为显示，0为不显示
	 * @return
	 */
	public static JSONObject uploadnews(  String access_token    ,String title,String thumb_media_id  , String url , String digest ,String content ,  String show_cover_pic) {
		String new_url = ParamesUriAPI.upload_news.replace("ACCESS_TOKEN", access_token);
		String data = "{" +
				" \"articles\": [" +
				"{" +
				"\"thumb_media_id\":\"" + thumb_media_id + "\"," +
				"\"author\":\"\"," +
				"\"title\":\"" + title+ "\"," +
				"\"content_source_url\":\""+ url +"\"," +
				"\"content\":\""+content+"\"," +
				"\"digest\":\""+ digest +"\"," +
				"\"show_cover_pic\":" +show_cover_pic  +
				"}]}";

		JSONObject jsonobject = HttpRequestUtil.httpRequest(new_url, "POST", data);
		WeixinApp weixinApp = null;
		if (null != jsonobject) {
			System.out.println(jsonobject+"-----------------------------");
			if(jsonobject.containsKey("type")){
				String typeI = jsonobject.getString("type");//授权方appid
				String media_id = jsonobject.getString("media_id");//
				Long created_at = jsonobject.getLong("created_at");//
				System.out.println(typeI + "-->" +media_id + "--->" + created_at );
				return jsonobject;
			} else {
				log.info("{}", jsonobject);
			}
		} else {
			System.out.println("获取授权失败了，自己找原因。。。");
		}
		return jsonobject;
	}

	/**
	 *  群发消息
	 * @param   access_token   公众号token
	 * @param is_to_all 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id : 群发到的分组的group_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写group_id
	 * @param  content :  发送内容
	 * @return
	 * @throws Exception
	 */
	public static String  sendAllMessage(  String access_token  , boolean is_to_all  ,String group_id , String  content) throws Exception{
		String data =  "{\"filter\":" +
				"{\"is_to_all\":true," + "\"group_id\":\" " + group_id + "\"}," +
				"\"text\":" +
				"{\"content\":\"" + content + "\"}," +
				"\"msgtype\":\"text\"" +
				"}";
		String new_url = ParamesUriAPI.send_all.replace("ACCESS_TOKEN",access_token);
		JSONObject jsonobject = HttpRequestUtil.httpRequest(new_url, "POST", data);
		WeixinApp weixinApp = null;
		if (null != jsonobject) {
			System.out.println(jsonobject+"-----------------------------");
			if(jsonobject.containsKey("errcode")){
				String errcode = jsonobject.getString("errcode");//授权方appid
				String errmsg = jsonobject.getString("errmsg");//
				String msg_id = jsonobject.getString("msg_id");//
				System.out.println(errcode + "-->" +errmsg + "--->" + msg_id );
				return errcode;
			} else {
				log.info("{}", jsonobject);
			}
		} else {
			System.out.println("获取授权失败了，自己找原因。。。");
		}
		return null;
	}

	/**
	 *  预览发送消息
	 * @param   access_token   公众号token
	 * @param openid 用户Openid
	 * @param media_id : 图文消息的media_id
	 * @return
	 * @throws Exception
	 */
	public static JSONObject  sendPreviewMessageByOpenId(  String access_token    ,String openid , String  media_id) {
		String data =   "{\"touser\":\"" + openid + "\","+
						"\"mpnews\":" +
							"{\"media_id\":\"" + media_id + "\"}" +
						"," +
						"\"msgtype\":\"mpnews\"" +
						"}" ;
		String new_url = ParamesUriAPI.send_preview_by_Openid.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = HttpRequestUtil.httpRequest(new_url, "POST", data);
		if (null != jsonobject) {
			System.out.println(jsonobject+"-----------------------------");
			if(jsonobject.containsKey("errcode")){
				String errcode = jsonobject.getString("errcode");//授权方appid
				String errmsg = jsonobject.getString("errmsg");//
				String msg_id = null;
				if(jsonobject.containsKey("msg_id")){
					msg_id = jsonobject.getString("msg_id");//
				}
				System.out.println(errcode + "-->" +errmsg + "--->" + msg_id );
				return jsonobject;
			} else {
				log.info("{}", jsonobject);
			}
		} else {
			System.out.println("获取授权失败了，自己找原因。。。");
		}
		return jsonobject;
	}



	/**
	 *  客服发送图文消息
	 * @param   access_token   公众号token
	 * @param openid 用户Openid
	 * @return
	 * @throws Exception
	 */
	public static JSONObject  sendCustomMessageByOpenId(  String access_token    ,String openid ,String title , String description, String url , String picurl) {
		String data =   "{" +
				"\"touser\":\"" + openid + "\","+
				"\"msgtype\":\"news\"," +
				"\"news\":{" +
				"\"articles\":[" +
				"{" +
				"\"title\":\"" + title + "\"," +
				"\"description\":\"" + description + "\"," +
				"\"url\":\"" + url + "\"," +
				" \"picurl\":\"" + picurl + "\"" +
				" }]" +
				"}}" ;
		String new_url = ParamesUriAPI.custom_send.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = HttpRequestUtil.httpRequest(new_url, "POST", data);
		if (null != jsonobject) {
			//System.out.println(jsonobject+"-----------------------------");
			if(jsonobject.containsKey("errcode")){
				String errcode = jsonobject.getString("errcode");//授权方appid
				String errmsg = jsonobject.getString("errmsg");//
				String msg_id = null;
				if(jsonobject.containsKey("msg_id")){
					msg_id = jsonobject.getString("msg_id");//
				}
				log.info("发送客服消息成功{}", jsonobject);
				return jsonobject;
			} else {
				log.info("{}", jsonobject);
			}
		} else {
			log.info("发送消息获取授权失败了，自己找原因。。。");
		}
		return jsonobject;
	}


	/**
	 *  客服发送文本消息
	 * @param   access_token   公众号token
	 * @param openid 用户Openid
	 * @return
	 * @throws Exception
	 */
	public static JSONObject  sendCustomTextMessageByOpenId(  String access_token    ,String openid ,String content ) {
		String data =   "{" +
				"\"touser\":\"" + openid + "\","+
				"\"msgtype\":\"text\"," +
				"\"text\":" +
				"{" +
				"\"content\":\"" + content + "\"," +
				"}}" ;
		String new_url = ParamesUriAPI.custom_send.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = HttpRequestUtil.httpRequest(new_url, "POST", data);
		if (null != jsonobject) {
			//System.out.println(jsonobject+"-----------------------------");
			if(jsonobject.containsKey("errcode")){
				String errcode = jsonobject.getString("errcode");//授权方appid
				String errmsg = jsonobject.getString("errmsg");//
				String msg_id = null;
				if(jsonobject.containsKey("msg_id")){
					msg_id = jsonobject.getString("msg_id");//
				}
				//System.out.println(errcode + "-->" +errmsg + "--->" + msg_id );
				return jsonobject;
			} else {
				log.info("{}", jsonobject);
			}
		} else {
			System.out.println("发送文本获取授权失败了，自己找原因。。。");
		}
		return jsonobject;
	}

	/**
	 * 客服发送文本消息 暂适用图片和语音
	 * @param   access_token 公众号token
	 * @param openid 用户Openid
	 * @param type 发送文件类型，如图片、视频等详细见 WechatMedieType
	 * @param mediaId 发送的图片/语音/视频/图文消息（点击跳转到图文消息页）的媒体ID
	 * @return
	 * @throws Exception
	 */
	public static JSONObject  sendCustomMediaMessageByOpenId(  String access_token  ,String openid , String type,String mediaId ) {
		String data =   "{" +
				"\"touser\":\"" + openid + "\","+
				"\"msgtype\":\"" + type + "\"," ;
		if(WechatMedieType.image.getValue().equals(type)
				|| WechatMedieType.voice.getValue().equals(type)){
			data = data + "\"" + type + "\":";
		}
	   data = data + "{\"media_id\":\"" + mediaId + "\"}}";
		String new_url = ParamesUriAPI.custom_send.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = HttpRequestUtil.httpRequest(new_url, "POST", data);
		if (null != jsonobject) {
			//System.out.println(jsonobject+"-----------------------------");
			if(jsonobject.containsKey("errcode")){
				String errcode = jsonobject.getString("errcode");//授权方appid
				String errmsg = jsonobject.getString("errmsg");//
				String msg_id = null;
				if(jsonobject.containsKey("msg_id")){
					msg_id = jsonobject.getString("msg_id");//
				}
				return jsonobject;
			} else {
				log.info("{}", jsonobject);
			}
		} else {
			System.out.println("发送图片获取授权失败了，自己找原因。。。");
		}
		return jsonobject;
	}

	/**
	 * 获取客服回话状态
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static JSONObject  getCustomSessionStatus(  String access_token  ,String openid ) {
		String new_url = ParamesUriAPI.get_custom_session_status.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		JSONObject jsonobject = HttpRequestUtil.httpRequest(new_url, "GET", null);
		return jsonobject;
	}


	/**
	 * 获取所有用户根据公众号
	 * @param access_token
	 * @param nextOpenid
	 * @param jsonArrayPara
	 */
	public static void getAllUserListByAppid( String access_token    ,String nextOpenid ,JSONArray jsonArrayPara ){
		String new_url = ParamesUriAPI.get_user_list.replace("ACCESS_TOKEN", access_token).replace("NEXT_OPENID", nextOpenid);
		JSONObject jsonobject = HttpRequestUtil.httpRequest(new_url, "GET", "");
		if (null != jsonobject) {
			if(jsonobject.containsKey("total")){
				int total = jsonobject.getInt("total");
				int count = jsonobject.getInt("count");
				JSONObject data = jsonobject.getJSONObject("data");
				JSONArray jsonArray = data.getJSONArray("openid");
				String next_openid = jsonobject.getString("next_openid");
				jsonArrayPara.addAll(jsonArray);
				if(count>=10000 && !Utility.isNullorEmpty("next_openid")){
					getAllUserListByAppid(access_token, next_openid , jsonArrayPara );
				}
			}else{
				log.info("{}", jsonobject);
			}
		}
	}

	/**
	 * 获取临时素材文件
	 * @param access_token
	 * @param mediaid
	 * @return
	 */
	public static Object getTemporaryMediaByMediaid( String access_token    ,String mediaid ){
		String new_url = ParamesUriAPI.get_temporary_media.replace("ACCESS_TOKEN", access_token).replace("MEDIA_ID", mediaid);
		return HttpRequestUtil.getFileUploadStream(new_url);
	}



	/**
	 * 获取 JS api 签名
	 * @param jsapi_ticket
	 * @param timestamp
	 * @param nonce
	 * @param jsurl
	 * @return
	 * @throws IOException
	 */
	public static String getJSApiSignature(String jsapi_ticket, String timestamp,
									  String nonce, String jsurl) throws IOException {
		/****
		 * 对 jsapi_ticket、 timestamp 和 nonce 按字典排序 对所有待签名参数按照字段名的 ASCII
		 * 码从小到大排序（字典序）后，使用 URL 键值对的格式（即key1=value1&key2=value2…）拼接成字符串
		 * string1。这里需要注意的是所有参数名均为小写字符。 接下来对 string1 作 sha1 加密，字段名和字段值都采用原始值，不进行
		 * URL 转义。即 signature=sha1(string1)。
		 * **如果没有按照生成的key1=value&key2=value拼接的话会报错
		 */
		String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket,
				"timestamp=" + timestamp, "noncestr=" + nonce, "url=" + jsurl };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		String content = paramArr[0].concat("&"+paramArr[1]).concat("&"+paramArr[2])
				.concat("&" + paramArr[3]);
		System.out.println("拼接之后的content为:"+content);
		String gensignature = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 对拼接后的字符串进行 sha1 加密
			byte[] digest = md.digest(content.toString().getBytes());
			gensignature = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 将 sha1 加密后的字符串与 signature 进行对比
		if (gensignature != null) {
			return gensignature.toLowerCase();// 返回signature
		} else {
			return "";
		}
		// return (String) (ciphertext != null ? ciphertext: false);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 *
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	public static void main(String[] args) {
		String ss = "jsapi_ticket=kgt8ON7yVITDhtdwci0qeQJmKj2D79rBTcNKE9lSbVJnXl_p0cmhrgBrLXw8Xqz0F3-eopH5U_hga2z3ungNWg&noncestr=50e437d8fe364d9bac5b6b4accab8b4&timestamp=1477724516&url=http://369app.longlianwang.com/weixin/share/";
		String gensignature = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 对拼接后的字符串进行 sha1 加密
			byte[] digest = md.digest(ss.getBytes());
			gensignature = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(gensignature.toLowerCase());
	}
}
