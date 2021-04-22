"use strict";
import { validityStatesError } from "../../js/cadastro/validityStates.js";
import { validadoresEspecificos } from "../../js/cadastro/validators.js";
import { errorMessages } from "../../js/cadastro/mensagensDeValidacao.js";

const classeErroValidacao = "erro-validacao";
const classePossuiErroValidacao = "possui-erro-validacao";

export { FormFields };

function FormFields() {
    this.fields;
    this.operation;
    this.formName;
}

FormFields.prototype = {
    constructor: FormFields,
    disabled: function (disabled) {
        this.loop(function (field) {
            field.disabled = disabled;
            let setfocus = field.getAttribute("data-setfocus");
            if (disabled === false) {
                if (setfocus === "true") {
                    field.focus();
                }
            }
        });
        this.clear();
    },
    setFields: function (fields) {
        this.fields = fields;
        this.loop((field) => field.addEventListener("focusin", (event) => this.erroDeValidacaoRemover(field)));
    },

    getParameters() {
        var model = {};

        this.fields.forEach((field) => (model[field.name] = field.value));
        const json = JSON.stringify(model);
        return `action=Cadastro${this.formName}&model=${json}&operation=${this.operation}`;
    },

    clear: function () {
        this.loop(function (f) {
            if (f.tagName === "INPUT" && f.getAttribute("type") === "text") {
                f.value = "";
            }
        });
    },

    loop: function (func) {
        this.fields.forEach((field) => func(field));
    },

    setInsert: function () {
        this.operation = "i";
    },

    setUpdate: function () {
        this.operation = "u";
    },

    setFormName(formName) {
        this.formName = formName;
    },

    removeErroValidacao() {
        this.loop((field) => field.classList.remove(classePossuiErroValidacao));
    },

    checkField() {
        this.loop((field) => this.validarInput(field));
    },

    retornarMensagemDeErro: function (tipo, validity) {
        let mensagemDeErro = "";

        validityStatesError.forEach((erro) => {
            if (validity[erro]) {
                mensagemDeErro = errorMessages[tipo][erro];
            }
        });
        return mensagemDeErro;
    },

    validarInput: function (input) {
        const tipo = input.dataset.tipo;
        if (validadoresEspecificos[tipo]) {
            validadoresEspecificos[tipo](input);
        }
        const elementoEhValido = input.validity.valid;
        if (elementoEhValido) {
            this.erroDeValidacaoRemover(input);
        } else {
            this.erroDeValidacaoAdicionar(input);
        }
    },

    erroDeValidacaoAdicionar: function (input) {
        const elementoPai = input.parentNode;

        const elementoErroExiste = elementoPai.querySelector(`.${classeErroValidacao}`);
        const elementoErro = elementoErroExiste || document.createElement("div");

        const label = elementoPai.querySelector("label");
        const style = getComputedStyle(label);

        elementoErro.className = classeErroValidacao;
        elementoErro.style.marginLeft = style.width;

        elementoErro.textContent = this.retornarMensagemDeErro(input.dataset.tipo, input.validity);

        input.after(elementoErro);
        input.classList.add(classePossuiErroValidacao);
    },
    erroDeValidacaoRemover: function (input) {
        let parent = input.parentNode;
        let errorMessage = parent.querySelector(`.${classeErroValidacao}`);

        if (errorMessage) {
            errorMessage.remove();
        }
        input.classList.remove(classePossuiErroValidacao);
    },
};
