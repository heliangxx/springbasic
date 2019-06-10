package com.pactera.common.constants;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 类名称：Constants 类描述：公共常量 创建人：admin 创建时间：2018年2月7日 下午1:21:17
 * 
 * @version 1.0.0
 */
public class Constants {

    private static final Logger LOGGER = LogManager.getLogger(Constants.class);

    /** 默认页码 1 */
    public static final Integer PAGENUM = 1;

    /** 默认行数 10 */
    public static final Integer PAGESIZE = 10;

    /** 默认页码 1 */
    public static final String PAGE_NUM = "1";

    /** 默认行数 10 */
    public static final String PAGE_SIZE = "10";

    /** 默认行数 50 */
    public static final String PAGE_SIZE_FIFTY = "50";

    /** APP默认行数5 */
    public static final String APP_PAGE_SIZE = "5";

    /** session attribute 当前登录用户名-key */
    public static final String LOGIN_NAME_SESSION = "login_name";

    /** session attribute 当前登录用户id-key */
    public static final String LOGIN_SID_SESSION = "login_sid";

    /** session attribute 当前登录用户vo-key */
    public static final String LOGIN_USER_SESSION = "login_user";

    /** session attribute 当前登录用户菜单列表-key */
    public static final String MENU_LIST_SESSION = "menu_list";

    /** session attribute 当前登录用户权限URL-key */
    public static final String AUTH_URL_LIST_SESSION = "auth_url_list";

    /** isValid 是否有效 1 有效 */
    public static final Integer VALID = 1;

    /** isValid 是否有效 2 无效 ;是否删除 2 已删除 */
    public static final Integer INVALID = 2;

    /** 服务器域名 */
    public static String LOG_URL = "http://ams.tahoecndemo.com:8090";

    /**
     * 文件上传模式(server/nas)
     */
    public static String UPLOAD_MODE = "server";

    /**
     * 文件上传NAS盘目录
     */
    public static String UPLOAD_NAS_DIR = "/opt/upload";

    /**
     * 文件NAS访问根目录
     */
    public static String UPLOAD_NAS_ACCESS = "/nas";

    /**
     * 上传默认目录
     */
    public static String UPLOAD_ROOT = "/files";

    /** 默认密码 */
    public static final String PASSWORD = "123456";

    static {
        Constants.loadConfig();
    }

    /**
     * 方法描述：读取常量配置 创建人：admin
     */
    synchronized static public void loadConfig() {
        LOGGER.info("读取配置文件中的常量 Begin...");
        InputStream is = Constants.class.getResourceAsStream("/config.properties");
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(is, "UTF-8"));

            if (properties.getProperty("upload.mode") != null) {
                UPLOAD_MODE = properties.getProperty("upload.mode");
            }
            if (properties.getProperty("upload.nas.dir") != null) {
                UPLOAD_NAS_DIR = properties.getProperty("upload.nas.dir");
            }
            if (properties.getProperty("upload.nas.access") != null) {
                UPLOAD_NAS_ACCESS = properties.getProperty("upload.nas.access");
            }
        } catch (Exception e) {
            LOGGER.error("读取常量配置文件异常:", e);
        }
        LOGGER.info("读取配置文件中的常量 End...");
    }
}
