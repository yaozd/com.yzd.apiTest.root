package com.yzd.apiTest.web.utils.httpExt;

import lombok.Data;

/**
 * Created by zd.yao on 2017/8/9.
 */
@Data
public class HttpRequestBo {
    private String type;
    private String url;
    private String charset;
    private String[] keys;
    private String[] values;
    private String[] headKeys;
    private String[] headValues;
}
