/**
 * 第三方登录参数(已context.xml clientConfigService bean)示范. 在实际业务中, 应该由第三方业务系统的后端对接口发起请求
 *
 * 注: 如果想用js(Node.js)做client请参考另一个开源项目. https://github.com/IdentityModel/oidc-client-js
 *
 */

$("#codetest").click(function () {

    var req = new XMLHttpRequest();
    var url = "http://localhost:8089/client/oidc/authorize" + formatParams(params);
    var formData = new FormData();

    req.open("GET",url, false);

     req.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    req.send(formData);

    if(req.status===302){
        console.log("重定向到服务器端");
    }
   console.log( req.responseURL);
});


$("#tokentest").click(function () {

    var req = new XMLHttpRequest();
    var url = "http://localhost:8000/SouathServer/openid/authorize" + formatParams(tokenparams);

    console.log(url);

/*
    var formData = new FormData();

    req.open("GET",url, true);

    req.send(formData);

    if(req.status===302){
        console.log("重定向到服务器端");
    }*/
    console.log( req.responseURL);
});

var params = {
    client_id: "testclient",
    client_secret: "test_secret",
    redirect_uri:"http://localhost:8089/client/oidc/authorize_callback_code",
    scope:"openid refreshToken username",
    response_type:"code",

}

window.location.hash.substring(1);

var tokenparams = {
    client_id: "your clientid",
    client_secret: "test_secret",
    redirect_uri:"http://localhost:8089/client/oidc/authorize_callback_code",
    scope:"openid  username",
    response_type:"token",

}


function formatParams( params ){
    return "?" + Object
            .keys(params)
            .map(function(key){
                return key+"="+encodeURIComponent(params[key])
            })
            .join("&")
}