package com.vessel.gather.app.constant;

/**
 * @author vesselzhang
 * @date 2017/11/30
 */

public class Constants {
    // SD卡路径
    public static final String SD_DIR = "/sdcard";
    // 项目路径
    public static final String SD_APP_DIR = SD_DIR + "/Juji";
    // Log保存目录
    public static final String SD_LOG_DIR = SD_APP_DIR + "/.log";
    // 文件保存路径
    public static final String SD_SAVE_DIR = SD_APP_DIR + "/.save";
    // 异常日志路径
    public static final String SD_CRASH_DIR = SD_APP_DIR + "/.crash";
    // 缓存路径
    public static final String SD_CACHE_DIR = SD_APP_DIR + "/.cache";
    // 图片路径
    public static final String SD_IMAGE_DIR = SD_APP_DIR + "/.image";
    // 音频路径
    public static final String SD_AUDIO_DIR = SD_APP_DIR + "/.audio";
    // 下载APP路径
    public static final String SD_APK_DIR = SD_APP_DIR + "/apk";

    public static final long    DEFAULT_LONG     = -1L;
    public static final int     DEFAULT_INT      = -1;
    public static final String  DEFAULT_STRING   = "";
    public static final boolean DEFAULT_BOOLEAN  = false;


    public static final String WEB = "WEB";
    public static final String WEB_SITE   = WEB + "_Site";
    public static final String WEB_TYPE   = WEB + "_Type";
    public static final String WEB_ID     = WEB + "_Id";

    public static final String PAGE = "PAGE";
    //Me
    public static final String PAGE_SETTING  = PAGE + "_Setting";
    public static final String PAGE_COLLECT  = PAGE + "_Collect";
    public static final String PAGE_ORDER    = PAGE + "_Order";
    public static final String PAGE_ADDRESS  = PAGE + "_Address";
    public static final String PAGE_SUGGEST  = PAGE + "_Suggest";
    public static final String PAGE_MEMO     = PAGE + "_Memo";
    public static final String PAGE_ABOUT    = PAGE + "_About";
    //Home
    public static final String PAGE_SELLER_APPLY = PAGE + "_Seller_apply";
    public static final String PAGE_WORKER_APPLY = PAGE + "_Worker_apply";
    public static final String PAGE_SELLER_LIST  = PAGE + "_Seller_list";
    public static final String PAGE_WORKER_LIST  = PAGE + "_Worker_list";
    public static final String PAGE_SELLER       = PAGE + "_Seller";
    public static final String PAGE_WORKER       = PAGE + "_Worker";
    public static final String PAGE_SEARCH       = PAGE + "_Search";
    public static final String PAGE_CART         = PAGE + "_Cart";


    //Key
    public static final String KEY = "KEY";
    public static final String KEY_WORKER_ID = KEY + "_Worker_Id";
    public static final String KEY_ORDER_ID = KEY + "_Order_Id";
    public static final String KEY_CART_LIST = KEY + "_Cart_List";

    //EventTAG
    public static final String TAG = "TAG";
    public static final String TAG_BANNERS_REFRESH = TAG + "_Banners_Refresh";

}
