class InputObject {

    /**
     * 构造器
     * @param {*} name 类名
     * @param {*} blur 焦点事件回调函数
     */
    constructor(name, blur) {
        this.class = '.' + name
        this.blur = blur

        /**
         * 获取状态
         */
        this.getState = function () {
            switch(name) {
                case 'username':
                    return vue.username.state
                case 'password':
                    return vue.password.state
                case 'password-confirm':
                    return vue.confirm.state
                default:
                    return false
            }
        }

        /**
         * 设置确认密码框的状态
         * @param {*} state 是否正确
         */
        this.setState = function (state) {
            switch(name) {
                case 'username':
                    vue.username.state = state
                    break
                case 'password':
                    vue.password.state = state
                    break
                case 'password-confirm':
                    vue.confirm.state = state
                    break
            }
            $(this.class).removeClass(state ? 'has-error' : 'has-success').addClass(state ? 'has-success' : 'has-error')
            $(this.class + ' .glyphicon').removeClass(state ? 'glyphicon-remove' : 'glyphicon-ok').addClass(state ? 'glyphicon-ok' : 'glyphicon-remove')
        }

        /**
         * 显示信息
         * @param {*} message tip显示的信息
         */
        this.showTip = function (message) {
            $(this.class + ' label').attr('data-original-title', message).tooltip('show')
        }

        /**
         * 隐藏Tip信息
         * @param {*} message tip显示的信息
         */
        this.hideTip = function () {
            $(this.class + ' label').attr('data-original-title', '').tooltip('hide')
        }

        $(this.class + ' input').blur(function(){
            blur()
        })
    }
}