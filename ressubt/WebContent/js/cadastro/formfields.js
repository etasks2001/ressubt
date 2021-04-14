"use strict";
const classeElementoErro = "erro-validacao";
import { validityStatesError } from "../../js/cadastro/validityStates.js";
import { validadoresEspecificos } from "../../js/cadastro/validators.js";
import { errorMessages } from "../../js/cadastro/mensagensDeValidacao.js";

//import { validarInput } from "../../js/cadastro/validar.js";
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

        this.fields.forEach((field) => {
            field.addEventListener("focusin", (event) => {
                let input = event.target;
                let parent = input.parentNode;
                let errorMessage = parent.querySelector(".erro-validacao");

                if (errorMessage) {
                    errorMessage.remove();
                }
                input.classList.remove("possui-erro-validacao");
            });
        });
    },

    getParameters() {
        var model = {};
        this.fields.forEach((field) => {
            model[field.name] = field.value;
        });

        return "action=Cadastro" + this.formName + "&model=" + JSON.stringify(model) + "&operation=" + this.operation;
    },

    clear: function () {
        this.loop(function (f) {
            if (f.tagName === "INPUT" && f.getAttribute("type") === "text") {
                f.value = "";
            }
        });
    },

    loop: function (func) {
        this.fields.forEach((field) => {
            func(field);
        });
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
        this.loop((field) => {
            field.classList.remove("possui-erro-validacao");
        });
    },

    checkField() {
        this.loop((field) => {
            this.validarInput(field);
        });
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
        const elementoEhValido = input.validity.valid;

        const classeInputErro = "possui-erro-validacao";
        const elementoPai = input.parentNode;

        const elementoErroExiste = elementoPai.querySelector(`.${classeElementoErro}`);
        const elementoErro = elementoErroExiste || document.createElement("div");

        const tipo = input.dataset.tipo;

        if (validadoresEspecificos[tipo]) {
            validadoresEspecificos[tipo](input);
        }

        if (!elementoEhValido) {
            const label = elementoPai.querySelector("label");
            const style = getComputedStyle(label);

            elementoErro.className = classeElementoErro;
            elementoErro.style.marginLeft = style.width;

            elementoErro.textContent = this.retornarMensagemDeErro(tipo, input.validity);

            input.after(elementoErro);
            input.classList.add(classeInputErro);
        } else {
            elementoErro.remove();
            input.classList.remove(classeInputErro);
        }
    },
};
