var iframe = document.querySelector("iframe");
var anchors = document.querySelectorAll("a[data-index]");

const links = ["/ressubt/control?action=ViewContribuinte", "/ressubt/control?action=ViewFinalidade"];

var menuAside = document.querySelector("aside");

menuAside.addEventListener("click", (event) => {
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
