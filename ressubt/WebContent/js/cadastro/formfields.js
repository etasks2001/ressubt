"use strict";
import { tiposDeInputs } from "../../js/cadastro/tiposDeInputs.js";
export { FormFields };

const errorMessage = {
    [tiposDeInputs.DESCRICAO]: "Descrição está em branco.....",
};

const fieldHasError = {
    [tiposDeInputs.DESCRICAO]: (input) => {
        if (input.value.trim() === "") {
            input.blanckField = "blanckField";
        }
        return;
    },
    /*
    codigoFinalidade: (input) => validarCodigoFinalidade(input),
    cpf: (input) => validarCPF(input),
    nome: (input) => validarNome(input),
    cnpj: (input) => validarCNPJ(input),
    inscricaoestadual: (input) => validarInscricaoEstadual(input),
    versaodoleiaute: (input) => validarVersaoDoLeiaute(input),
    cnpjcpf: (input) => validarCNPJCPF(input),
    codigoproduto: (input) => validarCodigoProduto(input),
    codigodebarras: (input) => validarCodigoDeBarras(input),
    unidade: (input) => validarUnidade(input),
    ncm: (input) => validarNCM(input),
    aliquotaICMS: (input) => validarAliquotaICMS(input),
    cest: (input) => validarCest(input),*/
};

function FormFields() {
    this.fields;
    this.operation;
    this.formName;
}

FormFields.prototype = {
    constructor: FormFields,
    disabled: function (disabled) {
        this.loop(function (f) {
            f.disabled = disabled;
            let setfocus = f.getAttribute("data-setfocus");
            if (disabled === false) {
                if (setfocus === "true") {
                    f.focus();
                }
            }
        });
        this.clear();
    },
    setFields: function (fields) {
        this.fields = fields;
    },

    getParameters() {
        var model = {};
        this.fields.forEach((x, i, f) => {
            model[f[i].name] = f[i].value;
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
        this.fields.forEach((x, i, f) => {
            func(f[i]);
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
        this.fields.forEach((field) => {
            field.classList.remove("possui-erro-validacao");
        });
    },

    checkField() {
        this.fields.forEach((field) => {
            let tipo = field.dataset.tipo;
            if (fieldHasError[tipo]) {
                console.log(fieldHasError[tipo](field));
                if (fieldHasError[tipo](field)) {
                    console.log(errorMessage[tipo]);
                }
            }
        });
    },
};
