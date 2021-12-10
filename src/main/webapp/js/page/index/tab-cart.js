const cart = new Vue({
    el: '#tab-cart',
    data() {
        return {
            sum: 0,
            allSelect: false,
            carts: [
                // {
                //     name: '《MySQL从删库到跑路》',
                //     description: 'wogan!',
                //     image: 'image/good/default.webp',
                //     stock: 0,
                //     price: 198,
                //     amount: 1,
                //     time: '',
                //     check: false
                // },
                // {
                //     name: '《Bug核心编程技术》',
                //     description: 'wogan!',
                //     image: 'https://gtms01.alicdn.com/tps/i3/TB1gXd1JXXXXXapXpXXvKyzTVXX-520-280.jpg',
                //     stock: 0,
                //     price: 198,
                //     amount: 1,
                //     time: '',
                //     check: false
                // }
            ]
        }
    },
    methods: {
        add: function(event, index){
            let $btn = event.target.nodeName == 'BUTTON' ? $(event.target).button('loading') : $(event.path[1]).button('loading')
            this.carts[index].amount += 1

            $.post(url + 'cart', {
                method: 'increaseInfo',
                uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83',
                gid: this.carts[index].gid,
                number: 1
            }, function(data){
                $btn.button('reset')
                cart.calcSum()
            })
        },
        subtract: function(event, index){
            let $btn = event.target.nodeName == 'BUTTON' ? $(event.target).button('loading') : $(event.path[1]).button('loading')
            if(this.carts[index].amount > 1) {
                this.carts[index].amount -= 1
                $.post(url + 'cart', {
                    method: 'increaseInfo',
                    uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83',
                    gid: this.carts[index].gid,
                    number: -1
                }, function(data){
                    $btn.button('reset')
                    cart.calcSum()
                })
            }else{
                $btn.button('reset')
            }
        },
        remove: function(event, index){
            let $btn = event.target.nodeName == 'BUTTON' ? $(event.target).button('loading') : $(event.path[1]).button('loading')

            let gids = []

            if(index < 0) {
                // 删除多列
                this.carts.forEach((element, index, array) => {
                    if(element.check) {
                        gids.push(element.gid)
                    }
                })
            }else{
                // 删除单个
                gids.push(cart.carts[index].gid)
            }
            if(gids.length <= 0) {
                $btn.button('reset')
                return
            }
            $.post(url + 'cart', {
                method: 'deleteByIds',
                uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83',
                gids: gids
            }, function(data){
                if(data.result) {
                    // 删除
                    for(var i=0; i<cart.carts.length; i++) {
                        if(gids.indexOf(cart.carts[i].gid) >= 0) {
                            cart.carts.splice(i, 1)
                            i--
                        }
                    }
                    cart.allSelect = false
                }
                $btn.button('reset')
                cart.calcSum()
            })
        },
        select: function(event, index) {
            if(index < 0) {
                // 选择所有
                this.carts.forEach((element, index, array) => {
                    element.check = event.target.checked
                })
                this.allSelect = event.target.checked
            }else{
                this.carts[index].check = event.target.checked
            }
            this.calcSum()
        },
        calcSum: function() {
            this.sum = 0
            this.carts.forEach((element, index, array) => {
                if(element.check) {
                    this.sum += element.price * element.amount
                }
            })
        }
    }
})

// 当 秋冬季特卖 面板显示时
// 获取所有的购物车商品
$('#tab-controller-cart').on('show.bs.tab', function(e){
    $.post(url + 'cart',{
        method: 'selectInfo',
        uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83'
    },function(data){
        let carts = eval(data.carts)
        let goods = eval(data.goods)
        goods.forEach((element, index, array) => {
            element.amount = carts[index].amount
        })
        cart.carts = goods
    })
})