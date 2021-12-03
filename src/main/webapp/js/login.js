var username = $(".username")
var password = $(".password")
var btn_login = $(".btn_login")
$(".error").hide()
btn_login.click(function(){
    $.post("http://localhost:8080/Bacikal_war_exploded/login", {
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