
let username = new InputObject('username', function(){
    if(vue.username.val == '') {
        username.showTip('请输入用户名')
        username.setState(false)
    }else {
        $.post(url + 'register', {
            method: 'check-user',
            username: vue.username.val
        },function(data){
            if(data.result) {
                username.hideTip()
                username.setState(true)
            }else {
                username.showTip(data.message)
                username.setState(false)
            }
        })
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
    if(vue.confirm.val != '' && vue.password.val != vue.confirm.val) {
        confirm.blur()
    }
})
let confirm = new InputObject('password-confirm', function(){
    if(vue.confirm.val == '' || vue.password.val != vue.confirm.val) {
        confirm.showTip(vue.confirm == '' ? '请输入密码' : '两次输入的密码不一致')
        confirm.setState(false)
    }else {
        confirm.hideTip()
        confirm.setState(true)
    }
})

$('#register').on('click', function(){
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
    if(!vue.confirm.state) {
        confirm.blur()
        $btn.button('reset')
        return
    }
    $.post(url + 'register', {
        method: 'register-user',
        username: vue.username.val,
        password: vue.password.val,
        gender: vue.gender,
        address: vue.address
    },function(data){
        $('#modal-msg .modal-body').html(data.message)
        $btn.button('reset')
        if(data.result) {
            console.log('注册成功')
            $('#modal-msg').on('hidden.bs.modal', function (e) {
                // 延迟 0.5 秒后跳转至登录界面
                $(location).delay(500).attr('href', 'login.html')
            }).modal('show')
        }else {
            console.log('注册失败')
            // 刷新当前页面
            location.reload();
        }
    })
})
