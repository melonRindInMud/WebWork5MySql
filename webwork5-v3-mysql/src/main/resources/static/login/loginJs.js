var pv1 = 0.2;

function OnLoad() {
    pv1 += 0.2;
    document.getElementById('form1').style.opacity = 0.6 + 0.2 * Math.cos(pv1);
    setTimeout("OnLoad()", 100);
}