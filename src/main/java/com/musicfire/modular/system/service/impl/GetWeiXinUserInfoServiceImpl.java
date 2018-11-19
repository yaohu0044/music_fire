package com.musicfire.modular.system.service.impl;

import com.musicfire.mobile.entity.WechatMpUser;
import com.musicfire.modular.system.service.GetWeiXinUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("wechatUserInfoService")
@Slf4j
public class GetWeiXinUserInfoServiceImpl implements GetWeiXinUserInfoService {

//
//    @Autowired
//    private CodeLibraryMapper codeLibraryMapper;
//
//    @Autowired
//    private WechatMpUserMapper wechatMpUserMapper;
//
//    @Autowired
//    private RedisDao redisDao;


    /**
     * 获取微信用户的code
     *
     * @return
     */
    public String getCode(String wechatpubNo) {
//        String retMsg = "";
//        CodeLibrary codeLibrary = codeLibraryMapper.selectByItemno("WECHATPUBNO", wechatpubNo);
//        String redirect_uri = EncodeUtils.urlEncode("http://zdypx.benbenniaokeji.com/mobile/getcode");
//        if (codeLibrary != null) {
//            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + codeLibrary.getItemname() + ""
//                    + "&redirect_uri=" + redirect_uri + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
//            retMsg = HttpClientUtil.httpGetRequest(url);
//            log.info("---------------公众号" + codeLibrary.getItemno() + "code获取用户信息的retMsg----------" + retMsg);
            /**if(StringUtils.isNotBlank(retMsg)){
             JSONObject retJson = JSONObject.fromObject(retMsg);
             log.info("---------------公众号"+codeLibrary.getItemno()+"获取用户信息的code返回信息----------"+retJson);
             if(retJson.has("access_token")){
             String access_token = retJson.getString("access_token");
             VmPara vmPara=vmParaMapper.selectByParaCode(codeLibrary.getItemno()+"access_token");
             if(vmPara!=null){
             vmPara.setParaContent(access_token);
             vmParaMapper.updateByPrimaryKey(vmPara);
             }
             log.info("---------------公众号"+codeLibrary.getItemno()+"的获取用户信息的code----------"+access_token);
             }else{
             String errcode = retJson.getString("errcode");
             String errmsg = retJson.getString("errmsg");
             log.info("公众号"+codeLibrary.getItemno()+"的获取用户信息的code:-----错误码："+errcode+"-----错误信息:"+errmsg);
             }
             }**/
//        }
        return null;
    }

    /**
     * 通过code换取网页授权access_token和openid
     *
     * @return
     */
    public Map<String, String> getAccessToken(String wechatpubNo, String code) {
//        log.info("-----------------------开始获取微信用户access_token和openid-----------------------");
//        Map<String, String> map = new HashMap<String, String>();
//        log.info("---------getAccessToken------map是否为空----------" + map.isEmpty());
//        CodeLibrary codeLibrary = codeLibraryMapper.selectByItemno("WECHATPUBNO", wechatpubNo);
//        if (codeLibrary != null) {
//            String urlStr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + codeLibrary.getItemname() + ""
//                    + "&secret=" + codeLibrary.getExtend1() + "&code=" + code + "&grant_type=authorization_code";
//
//            //处理url地址,避免Illegal character in scheme name at index 0错误
//            URL url;
//            URI uri = null;
//            try {
//                url = new URL(urlStr);
//                uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (URISyntaxException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            String retMsg = HttpClientUtil.httpGetRequest(uri);
//            log.info("---------------公众号" + codeLibrary.getItemno() + "access_token获取用户信息的retMsg----------" + retMsg);
//            if (StringUtils.isNotBlank(retMsg)) {
//                JSONObject retJson = JSONObject.fromObject(retMsg);
//                log.info("---------------公众号" + codeLibrary.getItemno() + "获取用户信息的access_token返回信息----------" + retJson);
//                if (retJson.has("access_token")) {
//                    String access_token = retJson.getString("access_token");
//                    String openid = retJson.getString("openid");
//                    map.put("access_token", access_token);
//                    map.put("openid", openid);
//                    log.info("---------getAccessToken------map是否为空----------" + map.isEmpty());
////					VmPara vmPara=vmParaMapper.selectByParaCode(codeLibrary.getItemno()+"access_token");
////					if(vmPara!=null){
////						vmPara.setParaContent(access_token);
////						vmParaMapper.updateByPrimaryKey(vmPara);
////					}
//                    log.info("---------------公众号" + codeLibrary.getItemno() + "的获取用户信息的access_token----------" + access_token);
//                } else {
//                    String errcode = retJson.getString("errcode");
//                    String errmsg = retJson.getString("errmsg");
//                    log.info("公众号" + codeLibrary.getItemno() + "的获取用户信息的access_token:-----错误码：" + errcode + "-----错误信息:" + errmsg);
//                }
//            }
//        }
//        log.info("---------getAccessToken------map是否为空----------" + map.isEmpty());
//        log.info("-----------------------结束获取微信用户access_token和openid-----------------------");
//        return map;
        return null;
    }

    /**
     * 通过access_token,openid获取微信用户信息
     *
     * @return
     */
    public Map<String, String> getWxUserInfo(String wechatpubNo, String access_token, String openid) {
//        log.info("-----------------------开始获取微信用户信息-----------------------");
//        log.info("-----------------------开始获取微信用户信息wechatpubNo的值-----------------------" + wechatpubNo);
//        log.info("-----------------------开始获取微信用户信息access_token的值-----------------------" + access_token);
//        log.info("-----------------------开始获取微信用户信息openid的值-----------------------" + openid);
//        Map<String, String> map = new HashMap<String, String>();
//        log.info("---------getWxUserInfo------map是否为空----------" + map.isEmpty());
//        CodeLibrary codeLibrary = codeLibraryMapper.selectByItemno("WECHATPUBNO", wechatpubNo);
//        if (codeLibrary == null) {
//            log.info("-----------------------开始获取微信用户信息codeLibrary为null-----------------------");
//        }
//        if (codeLibrary != null) {
//            String urlStr = " https://api.weixin.qq.com/sns/userinfo?"
//                    + "access_token=" + access_token + "&openid=" + openid + "&lang=en";
//            log.info("-----------------------开始获取微信用户信息url是-----------------------" + urlStr);
//
//            //处理url地址,避免Illegal character in scheme name at index 0错误
//            URL url;
//            URI uri = null;
//            try {
//                url = new URL(urlStr);
//                uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (URISyntaxException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            String retMsg = HttpClientUtil.httpGetRequest(uri);
////            log.info("---------------公众号" + codeLibrary.getItemno() + "getWxUserInfo获取用户信息的retMsg----------" + retMsg);
//            if (StringUtils.isNotBlank(retMsg)) {
//                JSONObject retJson = JSONObject.fromObject(retMsg);
////                log.info("---------------公众号" + codeLibrary.getItemno() + "获取用户信息的getWxUserInfo返回信息----------" + retJson);
//                if (retJson.has("openid")) {
//                    String nickname = retJson.getString("nickname");
//                    String city = retJson.getString("city");
//                    map.put("nickname", nickname);
//                    map.put("city", city);
////                    log.info("---------getWxUserInfo------map是否为空----------" + map.isEmpty());
////                    log.info("---------------公众号" + codeLibrary.getItemno() + "获取用户信息的nickname----------" + nickname);
////                    log.info("---------------公众号" + codeLibrary.getItemno() + "获取用户信息的city----------" + city);
//                } else {
//                    String errcode = retJson.getString("errcode");
//                    String errmsg = retJson.getString("errmsg");
//                    log.info("公众号" + codeLibrary.getItemno() + "的获取用户信息的access_token:-----错误码：" + errcode + "-----错误信息:" + errmsg);
//                }
//            }
//        }
////        log.info("---------getWxUserInfo------map是否为空----------" + map.isEmpty());
////        log.info("-----------------------结束获取微信用户信息-----------------------");
        return null;
    }

//    @Transactional
//    public int restoreWxMpUser(WechatMpUser mpUser) {
//        String key = RedisConstant.WECHAT_PRE + mpUser.getOpenId();
//        int result = wechatMpUserMapper.insert(mpUser);
//        if (redisDao.exist(key)) {
//            redisDao.update(key, mpUser);
//        }else {
//            redisDao.add(key, mpUser);
//        }
//
//        return result;
//    }

    @Transactional
    public int update(String  openId) {
//        if(StrUtil.isEmpty(openId)){
//            return 0;
//        }
//        String key = RedisConstant.WECHAT_PRE + openId;
//        WechatMpUser mpUser = new WechatMpUser();
//        if(redisDao.exist(key)){
//             mpUser =   redisDao.get(RedisConstant.WECHAT_PRE + openId, WechatMpUser.class);
//        }else{
//
//            mpUser.setOpenId(openId);
//            mpUser = wechatMpUserMapper.selectOne(mpUser);
//        }
//        if(mpUser==null){
//            return  0 ;
//        }
//        if(mpUser.getIsAdmin()>0){
//            mpUser.setIsAdmin(0);
//        }else {
//            mpUser.setIsAdmin(1);
//        }
//
//        int result = wechatMpUserMapper.updateByPrimaryKey(mpUser);
//        if(redisDao.exist(key)){
//            redisDao.update(key, mpUser);
//        }
//
//        return result;
        return 1;
    }

    @Override
    public WechatMpUser getWechatMpUserByOpenId(String openid) {
        return null;
    }


//    public WechatMpUser getWechatMpUserByOpenId(String openId) {
//        if (redisDao.exist(RedisConstant.WECHAT_PRE + openId)) {
//            return redisDao.get(RedisConstant.WECHAT_PRE + openId, WechatMpUser.class);
//        } else {
//            WechatMpUser mpUser = new WechatMpUser();
//            mpUser.setOpenId(openId);
//            mpUser = wechatMpUserMapper.selectOne(mpUser);
//            if(mpUser!=null){
//                redisDao.add(RedisConstant.WECHAT_PRE + mpUser.getOpenId(), mpUser);
//            }else {
//                return null;
//            }
//
//            return mpUser;
//        }
//
//
//    }
}
