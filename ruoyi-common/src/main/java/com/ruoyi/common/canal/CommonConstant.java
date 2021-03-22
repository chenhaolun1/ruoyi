package com.ruoyi.common.canal;

public interface CommonConstant {



    public static  final  int ROLE_ADMIN = 1;
    public  static  final int ROLE_TEACHER = 2;
    public  static  final int ROLE_STUDENT = 3;

    public static final String PREFIX_USER_TOKEN  = "PREFIX_USER_TOKEN_";
    public  static  final String X_ACCESS_TOKEN = "Authorization";

    public static  final String DB_NAME="ruoyi-vue-activiti7";
    public static final String TABLE_NAME="canal_t";

    /*http 500 错误码*/
    public  static  final String TOKEN_NULL = "10010"; //token 为空
    public  static  final String TOKEN_FAILURE = "10011"; //token 失效
    public  static  final String TOKEN_ILLEGAL ="10012";//token 不合法
    public  static  final String NO_SUSH_PERSON ="10013";//无此用户

    public  static  final String NOT_ALLOWED_IP= "10014";//不允许的IP

}
