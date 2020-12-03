function onInput(self, target) {                  // target 是 该提示框对应的提示信息的elemt
    if ('' === self.val()) {                      // 空的
        target.text('?请填写此栏')
        target.css('color', 'rgb(240, 120, 0)')
    }
    else if (true === isValidPattern(self[0])) {     // 符合模式
        target.text('√格式正确')
        target.css('color', 'rgb(15, 75, 221)')
    }
    else {                                        // 不符合模式
        target.text('×格式错误')
        target.css('color', 'rgb(255, 62, 62)')
    }
}

function isValidPattern(self) {
    let pattern = self.pattern
    let info = self.value
    let regex = new RegExp('^' + pattern + '$')
    let re = regex.exec(info)

    if (null != re) // 格式正确
        return true
    else
        return false
}

// 绑定
$('#Uname').on('input', function () {
    onInput($('#Uname'), $('#r1'))
})

$('#Utel').on('input', function () {
    onInput($('#Utel'), $('#r2'))
})

$('#Uemail').on('input', function () {
    onInput($('#Uemail'), $('#r3'))
})

$('#Uadd').on('input', function () {
    onInput($('#Uadd'), $('#r4'))
})

$('#Uqq').on('input', function () {
    onInput($('#Uqq'), $('#r5'))
})

$("#Utel").change(function () {
    if (true === isValidPattern($("#Utel")[0])) {     // 有效格式才会提交加处理
        let tel = $("#Utel").val()   // 其实写为 $('#Utel')[0].val()  更为准确
        $.ajax({
            type: 'POST',
            url: '/checktel',
            data: {'tel': tel},
            success: function (data) {
                let elem = $('#r2')
                if (1 === data) {
                    elem.text('该电话已经存在')
                    elem.css('color', 'rgb(255, 255, 0)')
                } else {
                    elem.text('该电话可用')
                    elem.css('color', 'rgb(0, 240, 0)')
                }
            }
        })
    }
})
