package com.yzd.apiTest.web.controller;

import com.yzd.apiTest.web.model.RequestForm;
import com.yzd.apiTest.web.model.RespondVM;
import com.yzd.apiTest.web.utils.httpExt.HttpManager;
import com.yzd.apiTest.web.utils.httpExt.HttpRequestBo;
import com.yzd.apiTest.web.utils.httpExt.HttpReturnBo;
import com.yzd.apiTest.web.utils.stringExt.StringUtil2;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zd.yao on 2017/8/9.
 */
@Controller
public class HomeController {
    @RequestMapping(value = {"", "/index"})
    public String index(){
        return "home/index";
    }
    @RequestMapping("httpRequest")
    @ResponseBody
    public RespondVM httpRequest(RequestForm form){
        RespondVM filterResult= filter(form);
        if(filterResult!=null)return filterResult;
        HttpRequestBo bo=RequestForm.toBo(form);
        HttpReturnBo result=HttpManager.request(bo);
        return RespondVM.toVM(result);
    }

    private RespondVM filter(RequestForm form ) {
        RespondVM respondVM=new RespondVM();
        respondVM.setIsOk(false);
        String error = validateForm(form);
        respondVM.setError(error);
        return StringUtils.isBlank(error)?null:respondVM;
    }

    private String validateForm(RequestForm form) {
        if(StringUtils.isBlank(form.getUrl())){
            return "接口URL地址不能为空";
        }
        try {
            URL url=new URL(form.getUrl());
        } catch (MalformedURLException e) {
            return "接口URL地址格式不正确";
        }
        if(StringUtils.isNotBlank(form.getButchParameter())&& StringUtil2.isNotEmpty(form.getKeys())){
            return "单一参数添加与批量参数添加不能同时存在";
        }
        return null;
    }

}
