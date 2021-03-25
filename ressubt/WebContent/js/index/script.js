"use strict";
import { FormFields } from "../../js/cadastro/formfields.js";
import { EditButtons } from "../../js/cadastro/editbuttons.js";
import { FormMenu } from "../../js/cadastro/formmenu.js";

const menuNav = document.querySelector("nav");

let iframe = document.querySelector("iframe");

let formFields = new FormFields();
let editButtons;
let formMenu;
const operations = document.querySelector(".operation");

window.onload = () => {
    editButtons = new EditButtons(document);
    formMenu = new FormMenu();
};

menuNav.addEventListener("click", (event) => {
    if (event.target instanceof HTMLAnchorElement) {
        iframe.src = formMenu.selecionarFormulario(event.target);
        editButtons.setDefault();
    }
});

operations.addEventListener("click", (event) => {
    let id = event.target.id;
    if (id === "novo") {
        formFields.setInsert();
        formFields.disabled(false);
        editButtons.setEditing();
    } else if (id === "gravar") {
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                let json = JSON.parse(this.response);
                console.log(json.code + " - " + json.message);
                if (json.code === 0) {
                    formFields.disabled(true);
                    editButtons.setDefault();
                } else {
                    console.log(json.code + " - " + json.message);
                }
            }
        };
        xhr.open("POST", "/ressubt/control");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        xhr.send(formFields.getParameters());
    } else if (id === "cancelar") {
        editButtons.setDefault();
    } else if (id === "pesquisar") {
        formFields.setUpdate();
        console.log(formFields.getParameters());
    }
});

iframe.onload = () => {
    let fields = iframe.contentWindow.document.querySelectorAll("select, input[type='text'], input[type='hidden']");
    let form = iframe.contentWindow.document.querySelector("form");
    var formName = form.getAttribute("data-formName");

    formFields.setFields(fields);
    formFields.setFormName(formName);
    formFields.disabled(true);
};
