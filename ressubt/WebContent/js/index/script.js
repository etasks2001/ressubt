"use strict";

import { ModalAction } from "../../js/cadastro/modal.js";
import { FormFields } from "../../js/cadastro/formfields.js";
import { EditButtons } from "../../js/cadastro/editbuttons.js";
import { FormMenu } from "../../js/cadastro/formmenu.js";

const menuNav = document.querySelector("nav");

let iframe = document.querySelector("iframe");

let formFields;
let editButtons;
let formMenu;
let modalMessageAction;
let modalConfirmAction;
let modalPesquisaAction;
let form;
let txtPesquisa = document.querySelector("#txtPesquisa");
let btnPesquisar = document.querySelector("#btnPesquisar");
let table_head = document.querySelector("#table_head");
let table_body = document.querySelector("#table_body");
let selectOrdenar = document.querySelector("#selectOrdenar");

console.log(table_head);
console.log(table_body);

console.log(selectOrdenar);

btnPesquisar.addEventListener("click", (event) => {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            let json = JSON.parse(this.response);

            console.log(json);
            console.log(json.code + " - " + json.message);
        }
    };

    let ordem = selectOrdenar.options[selectOrdenar.selectedIndex].value;
    xhr.open("POST", "/ressubt/control");
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

    xhr.send(`action=Cadastro${formFields.getFormName()}Q&p=${txtPesquisa.value}&page=1&o=${ordem}`);
});

const operations = document.querySelector(".operation");
const messageUser = document.querySelector("#messageUser");
const btnGravarRegistro = document.querySelector("#gravarRegistro");

window.onload = () => {
    const modalMessage = document.querySelector("#modalMessage");
    const modalConfirm = document.querySelector("#modalOkCancel");
    const modalPesquisa = document.querySelector("#modalSearch");

    modalMessageAction = new ModalAction(modalMessage);
    modalConfirmAction = new ModalAction(modalConfirm);
    modalPesquisaAction = new ModalAction(modalPesquisa);

    editButtons = new EditButtons(document);
    formMenu = new FormMenu();
    formFields = new FormFields();
    editButtons.disableAll();
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
        //} else if (id === "gravar") {
        //modalConfirmAction.open();
    } else if (id === "cancelar") {
        formFields.removeErroValidacao();
        const errosValidacao = form.querySelectorAll(".erro-validacao");
        errosValidacao.forEach((erro) => {
            erro.remove();
        });

        editButtons.setDefault();
        formFields.disabled(true);
    } else if (id === "pesquisar") {
        console.log(formFields.getParameters());
        console.log(formFields.getFormName());
        modalPesquisaAction.open();
    }
});

const buttonGravar = document.querySelector("#gravar");

buttonGravar.addEventListener("click", (e) => {
    e.preventDefault();
    formFields.checkField();
    let check = form.checkValidity();
    if (check) {
        modalConfirmAction.open();
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
    form = iframe.contentWindow.document.querySelector("form");
    var formName = form.getAttribute("data-formName");

    editButtons.setDefault();
    formFields.setFields(fields);
    formFields.setFormName(formName);
    formFields.disabled(true);
};
