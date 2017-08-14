package com.yzd.apiTest.web.utils.httpExt;

import com.yzd.apiTest.web.utils.stringExt.StringUtil2;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.params.CoreConnectionPNames;

import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zd.yao on 2017/8/9.
 */
public class HttpManager {
    /**
     * 模拟各种Http请求
     * @param bo
     * @return
     */
    @SuppressWarnings("all")
    public static HttpReturnBo request(HttpRequestBo bo){
        HttpReturnBo httpReturnBo=new HttpReturnBo();
        Boolean httpReturnIsOk=true;
        Map<String,Object> map = new HashMap<String,Object>();
        double begin = System.currentTimeMillis();
        String type = bo.getType();
        String url  = bo.getUrl();
        if("https".equalsIgnoreCase(url)){
            SSLUtil2.ignoreSsl();
        }
        HttpMethodBase method = null;
        try {
            //匹配请求
            if("Post".equals(type)){
                method = new PostMethod(url);
            }else if("Get".equals(type)){
                method = new GetMethod(url);
            }else if("Delete".equals(type)){
                method = new DeleteMethod(url);
            }else if("Put".equals(type)){
                method = new PutMethod(url);
            }else if("Trace".equals(type)){
                method = new TraceMethod(url);
            }else if("Head".equals(type)){
                method = new HeadMethod(url);
            }else if("Options".equals(type)){
                method = new OptionsMethod(url);
            }
            HttpClient client = new HttpClient();
            //请求超时
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 556000);
            //读取超时
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 555000);
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
            client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            method.setRequestHeader("Connection","close");
            method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
            //设置参数
            String[] keys = bo.getKeys(),values = bo.getValues();
            if(StringUtil2.isNotEmpty(keys)&&StringUtil2.isNotEmpty(values)){
                NameValuePair[] params = new NameValuePair[keys.length];
                for (int i = 0; i < keys.length; i++) {
                    if(StringUtils.isBlank(keys[i]))continue;
                    params[i] = new NameValuePair(keys[i], values[i]);
                }
                if(params.length>0){
                    String  queryString = EncodingUtil.formUrlEncode(params, bo.getCharset());
                    method.setQueryString(queryString);
                }
            }
            //设置请求头 和上面方法一致，如果一样，那么就覆盖。
            String[] headKeys = bo.getHeadKeys(),headValues = bo.getHeadValues();
            if(StringUtil2.isNotEmpty(headKeys)&&StringUtil2.isNotEmpty(headValues)){
                for (int i = 0;i < headKeys.length; i++) {
                    method.setRequestHeader(headKeys[i], headValues[i]);
                }
            }
            int status = client.executeMethod(method);
            map.put("status",status);
            Header[] heads = method.getResponseHeaders();
            //获取请求的Host，用于IP解析
            String host = method.getHostConfiguration().getHost();
            StringBuffer sb = new StringBuffer("");
            for (Header header : heads) {
                sb.append(header.getName())
                        .append(" : ")
                        .append(header.getValue())
                        .append("\r\n");
            }
            //头信息
            map.put("head", sb.toString());
            String result = method.getResponseBodyAsString();
            map.put("result", result);
            String ip = "未知";
            try {
                InetAddress iAddress=InetAddress.getByName(host);
                ip = iAddress.getHostAddress();
                map.put("host", ip);
            } catch (Exception e) {
                //LoggerUtils.fmtError(HttpManager.class, e, "获取IP地址失败，域名:[%s]", host);
            }
        } catch (UnknownHostException e) {
            //域名错误
            map.put("message", "请输入正确的网址");
            httpReturnIsOk=false;
            //LoggerUtils.fmtError(HttpManager.class, e, "网址异常[%s]", url);
        } catch (SocketTimeoutException e) {
            //超时
            map.put("message", "请求地址超时");
            httpReturnIsOk=false;
            //LoggerUtils.fmtError(HttpManager.class, e, "请求地址超时[%s]", url);
        } catch (Exception e) {
            //其他异常
            map.put("message", "请求出现未知异常，请重试！");
            httpReturnIsOk=false;
            //LoggerUtils.fmtError(HttpManager.class, e, "请求出现未知异常，请重试！[%s]", url);
        }
        double end = System.currentTimeMillis();
        map.put("time", (end - begin) / 1000);
        //
        httpReturnBo.setIsOk(httpReturnIsOk);
        httpReturnBo.setMap(map);
        return httpReturnBo;

    }
}
