package com.fenixbao92.memopro.common.constants;

import lombok.Getter;
import lombok.Setter;

public class WxConstant {
    public static String appid = "wx6a56c80f9f5aa662";
    public static String secret = "36c999855e2d5df487a7789ea6e3f24f";
    public static String codeAuthUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    public static String getCodeAuthUrl(String code){
        String result = codeAuthUrl.replace("APPID",appid);
        result = result.replace("SECRET",secret);
        result = result.replace("JSCODE",code);
        return result;
    }
}
