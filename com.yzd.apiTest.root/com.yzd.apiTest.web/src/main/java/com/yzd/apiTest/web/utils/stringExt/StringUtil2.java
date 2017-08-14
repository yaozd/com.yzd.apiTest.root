package com.yzd.apiTest.web.utils.stringExt;

/**
 * Created by zd.yao on 2017/8/10.
 */
public class StringUtil2 {
    public static boolean isNotEmpty(String [] obj){
        if(obj==null)return false;
        if(obj.length==0)return false;
        return true;
    }
}
