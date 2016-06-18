package com.wuping.paygent;

import common.util.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupingping on 16/6/8.
 */
public class TestTime {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        for (int id = 38378;id<38382;id++){
            Map<String ,String > params = new HashMap();
            params.put("user_id","1801093001900019");
            params.put("amouunt","1");
            params.put("phone","13567890120");
            params.put("card_id","3003");
            params.put("cipher","322323232");
            params.put("sms_code","111111");
            params.put("pay_id",String.valueOf(id));
            params.put("s_key","fjiojgriogsjgoirjigejg");
            HttpClientUtil.sendPostRequest("http://localhost:8000/wallet/charge",params,"UTF-8");
        }
        long end  = System.currentTimeMillis() - start;
        System.out.println(end/5);
    }
}
