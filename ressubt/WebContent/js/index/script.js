"use strict";
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
    formMenu = new FormMenu();
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
        formFields.setInsert();
    } else if (id === "gravar") {
        console.log(formFields.getParameters());
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                let json = JSON.parse(this.response);
                console.log(json.code + " - " + json.message);

                if (json.code === 0) {
                    formFields.disable(true);
                    formButtons.disable();
                } else {
                    console.log();
                }
            }
        };

        xhr.open("POST", "/ressubt/control");
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        xhr.send("action=DbFinalidade&" + formFields.getParameters());
    } else if (id === "cancelar") {
        formFields.disable(true);
        formButtons.disable();
    } else if (id === "pesquisar") {
        formFields.setUpdate();
        console.log(formFields.getParameters());
    }
});

iframe.onload = () => {
    let fields = iframe.contentWindow.document.querySelectorAll("select, input[type='text']");
    formFields.setFields(fields);
    formFields.disable(true);
};
