package com.java.ccs.spring5.example.demo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author caocs
 * @date 2021/10/6
 */
public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void main(String[] args) {
        logger.info("hhhh","ccsdfadsfa");
    }

}
