package com.wuping;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


/**
 * Created by wupingping on 16/6/5.
 */
public class CharsetTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String name = "你好";
        System.out.println(new String(name.getBytes(Charset.forName("GBK"))));
        String nameGBK = new String(name.getBytes(Charset.forName("GBK")), Charset.forName("GBK"));
        String nameUTF8 = new String(name.getBytes(Charset.forName("GBK")), Charset.forName("UTF8"));
        System.out.println(nameGBK);
        System.out.println(nameUTF8);


    }


}
