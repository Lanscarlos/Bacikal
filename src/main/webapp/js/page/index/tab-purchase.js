const allPurchases = new Vue({
    el:'#tab-purchase',
    data(){
        return{
            allSelect: false,
            uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83',
            purchases:[
                // {
                //     name: '《MySQL从删库到跑路》',
                //     description: 'wogan!',
                //     image: 'image/good/default.webp',
                //     price: 198
                // },{
                //     name: '《MySQL从入门到入土》',
                //     description: 'wogan!',
                //     image: 'image/good/default.webp',
                //     price: 198
                // }
            ]
        }
    },
    methods:{
        selectAll:function (event,index) {
            $.post(url + 'purchase',{
                method: 'select-all',
                uid: '5df287fc-5ce2-476f-b236-85c4e75cdd83',
            },function(data){
                allPurchases.purchases = data.purchases
                console.log('查询……')
                console.log(data)
                if(data.result){
                    console.log("查询成功")
                }
            })
        },
        }
    })

// 当处于我的订单 面板显示时
// 获取所有的订单信息
$('#tab-controller-purchase').on('show.bs.tab', function(e){
    $.post(url + 'purchase',{
        method: 'select-all',
        uid:'5df287fc-5ce2-476f-b236-85c4e75cdd83'
    },function(data){
        allPurchases.purchases = data.purchases

    })
})