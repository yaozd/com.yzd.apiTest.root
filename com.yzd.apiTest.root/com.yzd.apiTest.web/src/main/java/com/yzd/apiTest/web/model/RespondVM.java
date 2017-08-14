package com.yzd.apiTest.web.model;

import com.yzd.apiTest.web.utils.httpExt.HttpReturnBo;
import lombok.Data;

/**
 * Created by zd.yao on 2017/8/14.
 */
@Data
public class RespondVM {
    private Boolean isOk;
    private String error;
    private String head;
    private String result;
    private String status;
    private String time;
    private String host;
    public static RespondVM toVM(HttpReturnBo bo){
        RespondVM respondVM=new RespondVM();
        respondVM.setIsOk(bo.getIsOk());
        if(!bo.getIsOk()){
            respondVM.setError(bo.getMap().toString());
            return respondVM;
        }
        respondVM.setHead(bo.getMap().get("head").toString());
        respondVM.setResult(bo.getMap().get("result").toString());
        respondVM.setStatus(bo.getMap().get("status").toString());
        respondVM.setTime(bo.getMap().get("time").toString());
        respondVM.setHost(bo.getMap().get("host").toString());
        return respondVM;
    }

}
