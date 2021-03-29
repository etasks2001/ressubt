"use strict";
import { ModalMessageAction, ModalConfirmAction } from "../../js/cadastro/modal.js";
import { FormFields } from "../../js/cadastro/formfields.js";
import { EditButtons } from "../../js/cadastro/editbuttons.js";
import { FormMenu } from "../../js/cadastro/formmenu.js";

const menuNav = document.querySelector("nav");

let iframe = document.querySelector("iframe");

let formFields = new FormFields();
let editButtons;
let formMenu;
let modalMessageAction;
let modalConfirmAction;
const operations = document.querySelector(".operation");
const messageUser = document.querySelector("#messageUser");
const btnGravarRegistro = document.querySelector("#gravarRegistro");

window.onload = () => {
    editButtons = new EditButtons(document);
    formMenu = new FormMenu();
    const modalMessage = document.querySelector(".modalMessage");
    const modalConfirm = document.querySelector(".modalConfirm");
    modalMessageAction = new ModalMessageAction(modalMessage);
    modalConfirmAction = new ModalConfirmAction(modalConfirm);
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
        modalConfirmAction.open();
    } else if (id === "cancelar") {
        editButtons.setDefault();
        formFields.disabled(true);
    } else if (id === "pesquisar") {
        console.log(formFields.getParameters());
        modalMessageAction.open();
    }
});

btnGravarRegistro.addEventListener("click", (event) => {
    modalConfirmAction.close();
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            let json = JSON.parse(this.response);
            console.log(json.code + " - " + json.message);
            messageUser.innerHTML = json.message;
            modalMessageAction.open();
            if (json.code === 0) {
                formFields.disabled(true);
                editButtons.setDefault();
            }
        }
    };
    xhr.open("POST", "/ressubt/control");
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

    xhr.send(formFields.getParameters());
});

iframe.onload = () => {
    let fields = iframe.contentWindow.document.querySelectorAll("select, input[type='text'], input[type='hidden']");
    let form = iframe.contentWindow.document.querySelector("form");
    var formName = form.getAttribute("data-formName");

    formFields.setFields(fields);
    formFields.setFormName(formName);
    formFields.disabled(true);
};
