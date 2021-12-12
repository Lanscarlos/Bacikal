const user = new Vue({
    el: '#tab-user',
    data() {
        return {
            user:{
                name: '',
                gender: '',
                address: '',
                reg_time: '',
            }
        }
    },
    methods: {
        save: function(event) {
            $.post(url + 'user', {
                method: 'update',
                // uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83',
                gender: this.user.gender,
                address: this.user.address,
            }, function(data) {
                if(data.result) {
                    $('#tab-user-modal .modal-body').html(data.message)
                    $('#tab-user-modal').modal('show')
                    user.user = data.user
                }
            })
        }
    }
})

// 当 我的信息 面板显示时获取 用户数据
$('#tab-controller-user').on('show.bs.tab', function(e){
    $.post(url + 'user', {
        method: 'info',
        // uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83'
    }, function(data) {
        user.user = data.user
    })
})