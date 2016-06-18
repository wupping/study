package com.wuping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wupingping on 16/6/5.
 */
public class LogTest {
    private static final Logger logger  = LoggerFactory.getLogger(LogTest.class);
    public static void main(String[] args) {
     logger.error("test error");
    }
}
