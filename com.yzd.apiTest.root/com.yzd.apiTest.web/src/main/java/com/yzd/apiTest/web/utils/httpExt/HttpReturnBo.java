package com.yzd.apiTest.web.utils.httpExt;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by zd.yao on 2017/8/14.
 */
@Data
@NoArgsConstructor
public class HttpReturnBo {
    private Boolean isOk;
    private Map<String,Object> map;
}
