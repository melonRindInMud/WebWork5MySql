function add() {
    window.location = '/add'
}

function logout() {
    $.post('/logout', {}, suclogout())
}

function suclogout() {
    $("#b1").attr("disabled", false)
    $("#b2").attr("disabled", true)
    $("#b3").attr("disabled", true)
    $("#b4").attr("disabled", true)
    $("#subtitle").html('您已成功登出')
}

function login() {
    window.location = '/login'
}

function toMain() {
    window.location = '/main'
}

