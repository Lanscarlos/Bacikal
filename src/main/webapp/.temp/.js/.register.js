var username = $(".username")
var password = $(".password")
var confirm = $(".confirm")
var btn_reg = $(".btn_reg")

var result = -2
var registrable = false

$(".error, .right").hide()
username.blur(function() {
    result = -1
    $.post("http://localhost:8080/Bacikal_war_exploded/register", {
            method: "check-user",
            username: username.val()
        },
        function(data, status){
            if(data.result) {
                result = 1
                $(".error-username").hide()
                $(".right-username").show()
            }else{
                result = 0
                $(".error-username").show()
                $(".right-username").hide()
                $(".msg-username span").html(data.message)
            }
        })
})
confirm.blur(function() {
    if(password.val() == "") {
        $(".error-password").show()
        $(".right-password").hide()
        $(".msg-password span").html("请输入密码")
        return
    }
    if(confirm.val() != password.val()) {
        $(".error-password").show()
        $(".right-password").hide()
    }else {
        $(".error-password").hide()
        $(".right-password").show()
    }
})
btn_reg.click(function(){
    if(result == -2 || username.val() == "") {
        $(".error-username").show()
        $(".right-username").hide()
        $(".msg-username span").html("请输入用户名")
        return
    }
    if(result == -1){
        alert("正在验证用户名...请耐心等待...")
        return
    }else if(result == 0) {
        $(".error-username").show()
        $(".right-username").hide()
        $(".msg-username span").html("用户名已被占用")
    }
    if(password.val() == "") {
        $(".error-password").show()
        $(".right-password").hide()
        $(".msg-password span").html("请输入密码")
        return
    }
    if(confirm.val() != password.val()) {
        $(".error-password").show()
        $(".right-password").hide()
        $(".msg-password span").html("两次输入的密码不一致  ")
        return
    }
    $.post("http://localhost:8080/Bacikal_war_exploded/register", {
            method: "check-user",
            username: username.val()
        },
        function(data, status){
            if(data.result) {
                result = 1
                $(".error-username").hide()
                $(".right-username").show()
                $.post("http://localhost:8080/Bacikal_war_exploded/register", {
                        method: "register-user",
                        username: username.val(),
                        password: password.val()
                    },
                    function(data, status){
                        $(location).delay(3000).attr('href', './login.html')
                    })
            }else{
                result = 0
                $(".error-username").show()
                $(".right-username").hide()
                $(".msg-username span").html(data.message)
            }
        })
})