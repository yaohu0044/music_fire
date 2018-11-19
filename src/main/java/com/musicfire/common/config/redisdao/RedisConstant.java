package com.musicfire.common.config.redisdao;

/**
 * Created by sommer on 2017/4/20.

 */
public class RedisConstant {
    /**
     * shiro-redis的session对象前缀
     */
    public static final String SHIRO_REDIS_SESSION_PRE = "shiro_session:";

    /**
     * 存放uid的对象前缀
     */
    public static final String SHIRO_SESSION_PRE = "shiro_sessionid:";

    /**
     * 存放uid当前状态状态的前缀 uid
     */
    public static final String UID_PRE = "uid:";

    /**
     * 存放用户信息uid
     */
    public static final String USER_PRE="user:";

    /**
     * 存放用户信息uid
     */
    public static final String ROLE_MENU_PRE="rolemenu:";
    /**
     * 存放用户权限的前缀
     */
    public static final String PERMISSION_PRE = "permission:";
    /**
     * 消息缓存前缀
     */
    public static final String GOODS_PRE = "goods:";
    /**
     * 角色中的权限
     */
    public static final String ROLE_PRE = "role:";

    /**
     * 菜单
     */
    public static final String MENU_PRE = "menu:";
    /**
     * 字典缓存前缀
     */
    public static final String DICT_PRE = "dict:";


    /**
     * 消息缓存前缀
     */
    public static final String MESSAGE_PRE = "message:";

    /**
     * 消息缓存前缀
     */
    public static final String QUERY_PRE = "query:";


    public static final String MACHINE_STATE_PRE = "ms:";

    public static final String ORDER_PRE = "order:";

    public static final String SELLER_PRE = "seller:";
    public static final String MACHINE_PRE = "machine:";

    public static final String USER_SELLER_PRE = "user_seller:";

    public static final String USER_TREE = "user_tree";

    public static final String USER_SELLER_TREE = "user_seller_tree";

    public static final String ALIPAY_PRE = "alipay:";

    public static final String WECHAT_PRE = "wechat:";

    public static final String MACHINE_CODE_PRE = "mcode:";

    public final static String TOPIC_REGISTER= "register";
    public  final static String TOPIC_STATE = "state";
    public  final static String TOPIC_OFFLINE = "offline";

    /**
     * 组装key
     * @param pre 前缀
     * @param after 后缀
     * @return key
     */
    public static final String getKey(String pre,String after){
        return pre+after;
    }
}
