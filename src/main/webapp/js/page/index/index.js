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
                // 发送请求...
                //
                // 代码...待编写...
                //
                $('#tab-controller-search-cover').tab('show')
                $.post(url + 'good',{
                    method: 'selectByName',
                    name: vue.search.val
                },function(data){
                    setTimeout(function(){
                        console.log(eval(data.goods))
                        search.goods = eval(data.goods)
                        search.search.display = vue.search.val
                        $('#tab-controller-search').tab('show')
                        $btn.button('reset')
                    }, 500);
                })

            }
        }
    }
})


// vue.cart.carts.forEach((element, index, array) => {
//     // console.log(element)
//     element.check = true
// });
