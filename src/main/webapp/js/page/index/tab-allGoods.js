const allGoods = new Vue({
    el: '#tab-allGoods',
    data() {
        return {
            modal: {
                type: '',
                gid: '',
                index: -1,
                loading: false,
                amount: 1
            },
            goods: [
                // {
                //     gid: '0001',
                //     cid: '',
                //     sid: '',
                //     name: '《MySQL从删库到跑路》',
                //     description: 'wogan!',
                //     image: 'image/good/default.webp',
                //     stock: 0,
                //     price: 198
                // },
                // {
                //     gid: '0002',
                //     cid: '',
                //     sid: '',
                //     name: '《BUG》',
                //     description: 'wogan!',
                //     image: 'image/good/default.webp',
                //     stock: 0,
                //     price: 198
                // }
            ]
        }
    },
    methods: {

        // 弹出购买框
        showPurchaseModal: function(gid, index){
            if(this.goods[index].stock <= 0) {
                $('#tab-allGoods-warning .modal-body').html('商品库存已经见底啦! 请选择其他商品吧!')
                $('#tab-allGoods-warning').modal('show')
                return false
            }
            this.modal.type = 'purchase'
            this.modal.gid = gid
            this.modal.index = index
            $('#tab-allGoods-Modal').modal('show')
        },
        showCartModal: function(gid, index){
            if(this.goods[index].stock <= 0) {
                $('#tab-allGoods-warning .modal-body').html('商品库存已经见底啦! 请选择其他商品吧!')
                $('#tab-allGoods-warning').modal('show')
                return false
            }
            this.modal.type = 'cart'
            this.modal.gid = gid
            this.modal.index = index
            $('#tab-allGoods-Modal').modal('show')
        },
        
        // 增加 / 减少数量
        addAmount: function(){
            if(this.modal.loading || this.goods[this.modal.index].stock <= this.modal.amount) { return }
            this.modal.amount += 1
        },
        subAmount: function(){
            if(!this.modal.loading && this.modal.amount > 1) {
                this.modal.amount -= 1
            }
        },

        // 提交需求（加入购物车 / 立即购买）
        commit: function(event){
            this.modal.loading = true
            let $btn = event.target.nodeName == 'BUTTON' ? $(event.target).button('loading') : $(event.path[1]).button('loading')
            
            $.post(url + 'cart', {
                method: 'increaseInfo',
                // uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83',
                gid: this.modal.gid,
                number: this.modal.amount
            }, function(data){
                allGoods.modal.loading = false
                $btn.button('reset')
                $('#tab-allGoods-Modal').modal('hide')
                // 重置变量
                allGoods.modal.type = ''
                allGoods.modal.gid = ''
                allGoods.modal.amount = 1
            })
            
        },
    }
})

// 当 秋冬季特卖 面板显示时
// 获取所有的商品
$('#tab-controller-goods-all').on('show.bs.tab', function(e){
    $.post(url + 'good',{
        method: 'selectAll'
    },function(data){
        allGoods.goods = data.goods
        data.goods.forEach((element, index, array) => {
            if(element.image == null || element.image == '') {
                element.image = 'image/good/default.webp'
            }
        })
    })
})