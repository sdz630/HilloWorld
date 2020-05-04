package com.sdz.hilloworld.global;

import com.sdz.hilloworld.utils.*;
import com.sdz.hilloworld.utils.DBUtil;

import java.sql.SQLException;

public final class Constants {
    /**
     * Server端口
     */
    public static final String SERVER_IP = "192.168.1.107";

    public static final int SERVER_PORT = 5554;

    public static final String QQ_NUMBER = "2268050692";

    public static final String EMAIL = "sdz630@outlook.com";

    public static final String BLOG_ADDRESS = "https://www.sundongzhe.cn";

    public static final String NAME = "不吃棉花糖";

    public static boolean INIT_TASK_FRAGMENT_COMPLETED = false;

    public static boolean loginSucceed = false;

    public static boolean isRegistered = false;

    public static DBUtil db = new DBUtil.Builder().init().build();

    public static int LOGIN_RESULT_CODE = 200;

    public static int userNum;

    static {
        try {
            userNum = db.QueryNum("select count(*) from user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
