package com.yzd.apiTest.web.model;

import com.yzd.apiTest.web.utils.httpExt.HttpRequestBo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zd.yao on 2017/8/10.
 */
@Data
@NoArgsConstructor
public class RequestForm {
    private String type;
    private String url;
    private String charset;
    private String cookie;
    private String butchParameter;
    private String[] keys;
    private String[] values;
    private String[] headKeys;
    private String[] headValues;
    public static HttpRequestBo toBo(RequestForm form){
        HttpRequestBo bo=new HttpRequestBo();
        bo.setUrl(form.getUrl());
        bo.setType(form.getType());
        bo.setCharset(form.getCharset());
        return bo;
    }
}
