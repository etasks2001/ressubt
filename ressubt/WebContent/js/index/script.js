const iframe = document.querySelector("iframe");
const anchors = document.querySelectorAll("a[data-index]");

const links = ["/ressubt/control?action=ViewContribuinte", "/ressubt/control?action=ViewFinalidade", "/ressubt/control?action=ViewParticipante", "/ressubt/control?action=ViewProduto"];

const menuNav = document.querySelector("nav");

menuNav.addEventListener("click", (event) => {
    let anchor = event.target;

    let index = parseInt(anchor.getAttribute("data-index"));
    iframe.src = links[index];

    for (let i = 0; i < anchors.length; i++) {
        anchors[i].removeAttribute("class");
    }

    if (anchor instanceof HTMLAnchorElement) {
        anchor.className = "active";
    }

    let controls = iframe.contentWindow.document.querySelectorAll("select, input");
    for (let i = 0; i < controls.length; i++) {
        console.log(i);
        console.log(controls[i].disable);
        console.log((controls[i].disable = false));
        console.log(controls[i].disable);
    }
});
