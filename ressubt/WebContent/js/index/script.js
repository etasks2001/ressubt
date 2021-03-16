import { Operation } from "../../js/cadastro/operation.js";

const links = ["/ressubt/control?action=ViewContribuinte", "/ressubt/control?action=ViewFinalidade", "/ressubt/control?action=ViewParticipante", "/ressubt/control?action=ViewProduto"];

const menuNav = document.querySelector("nav");

const iframe = document.querySelector("iframe");
const anchors = document.querySelectorAll("a[data-index]");

const operations = document.querySelector(".operation");

const operationx = new Operation(document);

const eventoBotoes = (event) => {
    let nomeClasse = event.target.className;
    if (nomeClasse === "novo") {
        operationx.disableFields(false);
        operationx.disableButtons();
    } else if (nomeClasse === "gravar") {
        operationx.disableFields(true);
        operationx.disableButtons();
    } else if (nomeClasse === "cancelar") {
        operationx.disableFields(true);
        operationx.disableButtons();
    } else if (nomeClasse === "pesquisar") {
    }
    console.log(nomeClasse);
};

const selecionarFormulario = (event) => {
    let anchor = event.target;
    let data_index = anchor.getAttribute("data-index");
    let index = parseInt(data_index);

    iframe.src = links[index];

    for (let i = 0; i < anchors.length; i++) {
        anchors[i].removeAttribute("class");
    }

    if (anchor instanceof HTMLAnchorElement) {
        anchor.className = "active";
    }
};

menuNav.addEventListener("click", selecionarFormulario);
iframe.onload = () => {
    operationx.selecionarControleFormulario(iframe);
};
operations.addEventListener("click", eventoBotoes);
