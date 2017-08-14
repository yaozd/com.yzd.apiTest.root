package com.yzd.apiTest.web.controller;

import com.yzd.apiTest.web.model.RequestForm;
import com.yzd.apiTest.web.model.RespondVM;
import com.yzd.apiTest.web.utils.httpExt.HttpManager;
import com.yzd.apiTest.web.utils.httpExt.HttpRequestBo;
import com.yzd.apiTest.web.utils.httpExt.HttpReturnBo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        HttpRequestBo bo=RequestForm.toBo(form);
        HttpReturnBo result=HttpManager.request(bo);
        return RespondVM.toVM(result);
    }

}
