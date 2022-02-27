package com.dayou.test;

import com.dayou.utils.FileNameUtil;
import com.dayou.utils.MD5Util;
import org.junit.Test;

/**
 * @Description:
 * @author: dayou
 * @create: 2022-02-19 11:49
 */
public class MyTest {

    @Test
    public void testMD5(){
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);
    }

    @Test
    public void testName(){
        FileNameUtil.getFileType("9527s.jpg");

    }

}
