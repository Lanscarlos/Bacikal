// $('#purchaseModal').modal('show')
$('#purchase-confirm').click(function(){
    console.log('购买 -> ' + vue.purchase.amount)
})
$('#crat-confirm').click(function(){
    console.log('加入购物车 -> ' + vue.purchase.amount)
})
$('#purchaseModal').on('hidden.bs.modal', function(){
    vue.purchase.amount = 1
})
$('#cratModal').on('hidden.bs.modal', function(){
    vue.purchase.amount = 1
})
function purchase() {
    $('#purchaseModal').modal('show')
}
function addToCrat() {
    $('#cratModal').modal('show')
}