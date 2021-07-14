"use strict";

import { ModalAction } from "../../js/cadastro/modal.js";
import { FormFields } from "../../js/cadastro/formfields.js";
import { EditButtons } from "../../js/cadastro/editbuttons.js";
import { FormMenu } from "../../js/cadastro/formmenu.js";
import { PesquisaCadastro } from "../../js/cadastro/pesquisaCadastro.js";

const menuNav = document.querySelector("nav");

let iframe = document.querySelector("iframe");

let formFields;
let editButtons;
let formMenu;
let modalMessageAction;
let modalConfirmAction;
let modalPesquisaAction;
let pesquisaCadastro;
let form;
let btnPesquisar = document.querySelector("#btnPesquisar");

let btnNext = document.querySelector("#next");
btnNext.addEventListener("click", (event) => {
    pesquisaCadastro.nextPage(formFields.getFormName());
});

btnPesquisar.addEventListener("click", (event) => {
    pesquisaCadastro.pesquisar(formFields.getFormName());
});

const operations = document.querySelector(".operation");
const messageUser = document.querySelector("#messageUser");
const btnOkDialog = document.querySelector("#btnOk");

window.onload = () => {
    const modalMessage = document.querySelector("#modalMessage");
    const modalConfirm = document.querySelector("#modalOkCancel");
    const modalPesquisa = document.querySelector("#modalSearch");

    const txtPesquisa = document.querySelector("#txtPesquisa");
    const thead = document.querySelector("#table_head");
    const tbody = document.querySelector("#table_body");
    const selectOrdenar = document.querySelector("#selectOrdenar");

    modalMessageAction = new ModalAction(modalMessage);
    modalConfirmAction = new ModalAction(modalConfirm);
    modalPesquisaAction = new ModalAction(modalPesquisa);
    pesquisaCadastro = new PesquisaCadastro(txtPesquisa, thead, tbody, selectOrdenar);

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

const operacoes = {
    novo: function (event) {
        formFields.setInsert();
        formFields.disabled(false);
        editButtons.setEditing();
    },
    gravar: function (event) {
        event.preventDefault();
        formFields.checkField();
        let check = form.checkValidity();
        if (check) {
            modalConfirmAction.open();
        }
    },
    cancelar: function (event) {
        formFields.removeErroValidacao();
        const errosValidacao = form.querySelectorAll(".erro-validacao");
        errosValidacao.forEach((erro) => {
            erro.remove();
        });
        editButtons.setDefault();
        formFields.disabled(true);
    },
    pesquisar: function (event) {
        console.log(formFields.getParameters());
        console.log(formFields.getFormName());

        pesquisaCadastro.clear();
        modalPesquisaAction.open();
    },
};

operations.addEventListener("click", (event) => {
    let id = event.target.id;
    if (operacoes[id]) {
        operacoes[id](event);
    }
});

btnOkDialog.addEventListener("click", (event) => {
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
