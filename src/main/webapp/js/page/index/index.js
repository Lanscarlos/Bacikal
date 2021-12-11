const vue = new Vue({
    el: '#index-navbar',
    data() {
        return {
            search: {
                val: '',
            }
        }
    },
    methods: {
        
        // 顶部按钮搜索
        // 1. 先判断搜索内容是否为空
        // 2. 发送请求到后端获取搜索结果
        // 3. 跳转到 搜索结果 面板
        searchGoods: function() {
            // let $btn = $(this).button('loading')
            let $btn = event.target.nodeName == 'BUTTON' ? $(event.target).button('loading') : $(event.path[1]).button('loading')
            if(vue.search.val == '') {
                let input = $('#input-search')
                input.attr('data-original-title', '请输入搜索内容！').tooltip('show')
                setTimeout(function(){
                    input.attr('data-original-title', '').tooltip('hide')
                }, 3000);
                $btn.button('reset')
            }else{
                // 显示 搜索中... 面板遮挡
                $('#tab-controller-search-cover').tab('show')

                // 发送Post请求...
                $.post(url + 'good',{
                    method: 'selectByName',
                    name: vue.search.val
                },function(data){

                    // 设置延迟 500 毫秒后执行代码
                    setTimeout(function(){
                        search.goods = data.goods
                        search.search.display = vue.search.val
                        $('#tab-controller-search').tab('show')
                        $btn.button('reset')
                    }, 500);
                })

            }
        },

        /**
         * 用户登出
         */
        logout: function() {
            $.post(url + 'user', {
                method: 'logout'
            }, function(data) {
                // 判断返回的结果 是否已成功登出
                if(data.result) {
                    
                    // 跳转到登录页面
                    $(location).delay(500).attr('href', 'login.html')
                }
            })
        }
    }
})