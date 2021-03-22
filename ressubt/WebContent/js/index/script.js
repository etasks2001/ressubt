import { FormFields } from "../../js/cadastro/formfields.js";
import { FormButtons } from "../../js/cadastro/formbuttons.js";
import { FormMenu } from "../../js/cadastro/formmenu.js";

const menuNav = document.querySelector("nav");

let iframe = document.querySelector("iframe");

let anchors;
let formFields = new FormFields();
let formButtons;
let formMenu;
const operations = document.querySelector(".operation");

window.onload = () => {
    let buttons = document.querySelectorAll("button");
    formButtons = new FormButtons(buttons);
    anchors = document.querySelectorAll("a[data-index]");
    formMenu = new FormMenu(anchors);
};

menuNav.addEventListener("click", (event) => {
    if (event.target instanceof HTMLAnchorElement) {
        iframe.src = formMenu.selecionarFormulario(event.target);
    }
});

operations.addEventListener("click", (event) => {
    let id = event.target.id;
    if (id === "novo") {
        formFields.disable(false);
        formButtons.disable();
    } else if (id === "gravar") {
        formFields.disable(true);
        formButtons.disable();
    } else if (id === "cancelar") {
        formFields.disable(true);
        formButtons.disable();
    } else if (id === "pesquisar") {
    }
});

iframe.onload = () => {
    let fields = iframe.contentWindow.document.querySelectorAll("select, input[type='text']");
    formFields.setFields(fields);
    formFields.disable(true);
};
