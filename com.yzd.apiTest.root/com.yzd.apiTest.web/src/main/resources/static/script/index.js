/**
 * Created by zd.yao on 2017/8/10.
 */
$(function () {
    submitFun();
});
//region
function submitFun() {
    $("#submit").click(function () {
        var errorInfoObj=$("#_error_info");
        errorInfoObj.hide();
        $("#head_info").val("");
        $("#json_input").val("");
        $("#_respond_info").html("响应时间(Time)：响应状态(status)：HOST:");
        $.post($("#formId").attr('action'), submitData(), function (response) {
            //alert(response);
            if(!response.isOk){
                errorInfoObj.html("错误信息："+response.error);
                errorInfoObj.show();
                return
            }
            errorInfoObj.html("请求成功.");
            errorInfoObj.show();
            $("#head_info").val(response.head);
            $("#json_input").val(response.result);
            $("#_respond_info").html("响应时间(Time)："+response.time+"秒；响应状态(status)："+response.status+"；HOST:"+response.host);
        });
    });
}
function submitData() {
    var data = $("#formId").serialize();
    console.log(data);
    return data;
}
//endregion
//region
function parameter_exc(hideId,showId){
    $(showId).show();
    $(hideId).hide();
}
//endregion
//region
function del(obj){
    $(obj).parent().parent().remove();
}
//endregion
//region
function addCookieFun(obj){
    $(obj).show();
}
//endregion
//region
function add_url_parameter(obj,typeVal){
    var html=templateHtml(typeVal);
    $(obj).parent().parent().before(html);
    console.log(html);
}
function templateHtml(typeVal){
    var html="";
    switch (typeVal){
        case ("key"):return $("#key_template").html();
        case ("head"):return $("#head_template").html();
    }
    return html;
}
//endregion