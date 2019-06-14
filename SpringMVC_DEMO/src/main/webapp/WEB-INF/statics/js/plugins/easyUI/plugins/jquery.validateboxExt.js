//汉化
$.extend($.fn.validatebox.defaults,{
    missingMessage: "必输项.",
    rules:{
        email:{
            message:"请输入一个有效的邮箱地址."
        },
        url: {
            message: "请输入一个有效的URL地址."
        },
        length: {
            message: "请输入一个 {0} 到 {1}之间的值."
        },
        //后台验证
        remote: {
            message: "验证未通过请重新输入."
        }
    }
});
//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules,{
    //验证汉字
    CHS:{
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字.'
    },
    //移动手机号码验证
    Mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '请输入正确的电话号码.'
    },
    //国内邮编验证
    ZipCode: {
        validator: function (value) {
            var reg = /^[0-9]\d{5}$/;
            return reg.test(value);
        },
        message: 'The zip code must be 6 digits and 0 began.'
    },
    //数字
    Number: {
        validator: function (value) {
            var reg =/^[0-9]*$/;
            return reg.test(value);
        },
        message: '请输入数字.'
    }
});
//使用案例
//<td><input class="easyui-validatebox textbox" data-options="required:true,validType:'length[3,10]'"></td>
//<td><input class="easyui-validatebox textbox" data-options="required:true,validType:'email'"></td>