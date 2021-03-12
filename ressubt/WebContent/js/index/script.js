var iframe = document.querySelector("iframe");
var anchors = document.querySelectorAll("a[data-index]");

const links = ["/ressubt/control?action=ViewContribuinte", "/ressubt/control?action=ViewFinalidade", "/ressubt/control?action=ViewParticipante", "/ressubt/control?action=ViewProduto"];

var menuNav = document.querySelector("nav");

menuNav.addEventListener("click", (event) => {
    var anchor = event.target;

    var index = parseInt(anchor.getAttribute("data-index"));
    iframe.src = links[index];

    for (let i = 0; i < anchors.length; i++) {
        anchors[i].removeAttribute("class");
    }

    if (anchor instanceof HTMLAnchorElement) {
        anchor.className = "active";
    }
});
