import { tiposDeInputs } from "../../js/cadastro/tiposDeInputs.js";
import { inputUppercase } from "../../js/cadastro/inputUpperCase.js";

export const inputFilter = (textbox, inputFilter) => {
    ["keydown", "keyup"].forEach(function (event) {
        textbox.addEventListener(event, function () {
            console.log(textbox.validity);
            if (inputFilter(this.value)) {
                this.oldValue = this.value.toUpperCase();
                this.oldSelectionStart = this.selectionStart;
                this.oldSelectionEnd = this.selectionEnd;
            } else if (this.hasOwnProperty("oldValue")) {
                this.value = this.oldValue;
                this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
            } else {
                this.value = "";
            }
        });
    });
};
const moneyPattern = /^\d*[,]?\d{0,2}$/;
const numberPattern = /^-?\d*$/;
//const upperCasePattern = /[\w]+/g;

export const testReg = (pattern, value) => {
    return pattern.test(value);
};

export const inputRulesPatterns = {
    [tiposDeInputs.CNPJ]: (value) => testReg(numberPattern, value),
    //[tiposDeInputs.DESCRICAO]: (value) => testReg(numberPattern, value),
    [tiposDeInputs.CODIGO_FINALIDADE]: (value) => testReg(numberPattern, value),
    [tiposDeInputs.CPF]: (value) => testReg(numberPattern, value),
    //[tiposDeInputs.NOME]: (value) => testReg(upperCasePattern, value),
    [tiposDeInputs.CNPJ]: (value) => testReg(numberPattern, value),
    [tiposDeInputs.INSCRICAO_ESTADUAL]: (value) => testReg(numberPattern, value),
    [tiposDeInputs.VERSAO_DO_LEIAUTE]: (value) => testReg(numberPattern, value),
    [tiposDeInputs.CNPJ_CPF]: (value) => testReg(numberPattern, value),
    [tiposDeInputs.CODIGO_PRODUTO]: (value) => testReg(numberPattern, value),
    [tiposDeInputs.CODIGO_DE_BARRAS]: (value) => testReg(numberPattern, value),
    //[tiposDeInputs.UNIDADE]: (value) => testReg(numberPattern, value),
    [tiposDeInputs.NCM]: (value) => testReg(numberPattern, value),
    [tiposDeInputs.ALIQUOTA_ICMS]: (value) => testReg(moneyPattern, value),
    [tiposDeInputs.CEST]: (value) => testReg(numberPattern, value),
};
/*
                setInputFilter(document.getElementById("intTextBox"), function(value) {
                    return /^-?\d*$/.test(value); });
                  setInputFilter(document.getElementById("uintTextBox"), function(value) {
                    return /^\d*$/.test(value); });
                  setInputFilter(document.getElementById("intLimitTextBox"), function(value) {
                    return /^\d*$/.test(value) && (value === "" || parseInt(value) <= 500); });
                  setInputFilter(document.getElementById("floatTextBox"), function(value) {
                    return /^-?\d*[.,]?\d*$/.test(value); });
                  setInputFilter(document.getElementById("currencyTextBox"), function(value) {
                    return /^-?\d*[.,]?\d{0,2}$/.test(value); });
                  setInputFilter(document.getElementById("latinTextBox"), function(value) {
                    return /^[a-z]*$/i.test(value); });
                  setInputFilter(document.getElementById("hexTextBox"), function(value) {
                    return /^[0-9a-f]*$/i.test(value); });

        https://jsfiddle.net/emkey08/zgvtjc51
*/
