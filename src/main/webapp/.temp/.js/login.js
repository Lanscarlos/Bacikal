var username = $(".username")
var password = $(".password")
var btn_login = $(".btn_login")

$(".error").hide()
btn_login.click(function(){
    if(username.val() == ""){
        $(".error").html("请输入用户名和密码").show()
        return
    }
    $.post("http://localhost:8080/Bacikal_war_exploded/login", {
            method: "user-login",
            username: username.val(),
            password: password.val()
        },
        function(data, status){
            console.log(data)
            if(data.result) {
                $(".error").hide()
                $(location).attr('href', './index.html')
            }else {
                $(".error").html(data.message).show()
            }
        })
})