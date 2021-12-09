const home = new Vue({
    el: '#tab-home',
    data() {
        return {
            carousel:{
                url_0: 'https://gtms01.alicdn.com/tps/i1/TB1r4h8JXXXXXXoXXXXvKyzTVXX-520-280.jpg',
                url_1: 'https://gtms01.alicdn.com/tps/i2/TB10vPXKpXXXXacXXXXvKyzTVXX-520-280.jpg',
                url_2: 'https://gtms01.alicdn.com/tps/i3/TB1gXd1JXXXXXapXpXXvKyzTVXX-520-280.jpg'
            }
        }
    },
    methods: {
        subtract: function(index){
            
        },
        select: function(event) {
            this.cart.carts[event.target.dataset.index].check = event.target.checked
            this.cart.sum = 0
            console.log('changed...')
            this.cart.carts.forEach((element, index, array) => {
                if(element.check) {
                    this.cart.sum += element.price * element.amount
                }else{
                    // console.log('检测 - ' + index + ' -> false')
                }
            })
        }
    }
})