const search = new Vue({
    el: '#tab-search',
    data() {
        return {
            search: {
                display: '',
                type: '',
                gid: '',
                index: -1,
                loading: false,
                amount: 1
            },
            modal: {
                type: '',
                gid: '',
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
                $('#tab-search-warning .modal-body').html('商品库存已经见底啦! 请选择其他商品吧!')
                $('#tab-search-warning').modal('show')
                return false
            }
            this.modal.type = 'purchase'
            this.modal.gid = gid
            $('#tab-search-Modal').modal('show')
        },
        showCartModal: function(gid, index){
            if(this.goods[index].stock <= 0) {
                $('#tab-search-warning .modal-body').html('商品库存已经见底啦! 请选择其他商品吧!')
                $('#tab-search-warning').modal('show')
                return false
            }
            this.modal.type = 'cart'
            this.modal.gid = gid
            $('#tab-search-Modal').modal('show')
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
                uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83',
                gid: this.modal.gid,
                number: this.modal.amount
            }, function(data){
                search.modal.loading = false
                $btn.button('reset')
                $('#tab-search-Modal').modal('hide')
                // 重置变量
                search.modal.type = ''
                search.modal.gid = ''
                search.modal.amount = 1
            })
        },
    }
})


// $('#tab-controller-search').on('show.bs.tab', function(e){
//     $.post('http://172.16.109.209:8080/Bacikal/good',{
//         method: 'selectByName',
//         name: search.search.display
//     },function(data){
//         console.log(eval(data.goods))
//         search.goods = eval(data.goods)
//     })
// })