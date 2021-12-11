let username = new InputObject('username', function(){
    if(vue.username.val == '') {
        username.showTip('请输入用户名')
        username.setState(false)
    }else {
        username.hideTip()
        username.setState(true)
    }
})
let password = new InputObject('password', function(){
    if(vue.password.val == '') {
        password.showTip('请输入密码')
        password.setState(false)
    }else {
        password.hideTip()
        password.setState(true)
    }
})

$('#login').on('click', function(){
    var $btn = $(this).button('loading')
    if(!vue.username.state) {
        username.blur()
        $btn.button('reset')
        return
    }
    if(!vue.password.state) {
        password.blur()
        $btn.button('reset')
        return
    }
    $.post(url + 'user',{
        method: 'login',
        username: vue.username.val,
        password: vue.password.val
    },function(data){
        $('#modal-msg .modal-body').html(data.message)
        $btn.button('reset')
        if(data.result) {
            console.log('登录成功')
            $('#modal-msg').on('hidden.bs.modal', function (e) {
                // 延迟 0.5 秒后跳转至商城首页
                $(location).delay(500).attr('href', 'index.html')
            }).modal('show')
        }else {
            console.log('登录失败')
            $('#modal-msg').modal('show')
        }
    })
})