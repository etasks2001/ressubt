import { validarInput } from "../../js/cadastro/validar.js";
import { inputFilter, inputRulesPatterns } from "../../js/cadastro/inputFilter.js";
import { inputUppercase } from "../../js/cadastro/inputUpperCase.js";

export const loadValidators = () => {
    const inputValidators = {};

    const inputs = document.querySelectorAll("input");
    inputs.forEach((input) => {
        const tipo = input.dataset.tipo;
        if (inputRulesPatterns[tipo]) {
            inputFilter(input, inputRulesPatterns[tipo]);
        }

        /*
        if (tipo === "aliquotaICMS") {
            inputFilter(input, function (value) {
                return /^-?\d*[.,]?\d{0,2}$/.test(value);
            });
        } else if (tipo === "codigoFinalidade" || tipo === "cnpj" || tipo === "cnpjcpf" || tipo === "inscricaoestadual" || tipo === "versaodoleiaute" || tipo === "codigodebarras" || tipo === "ncm" || tipo === "cest") {
            inputFilter(input, function (value) {
                return /^-?\d*$/.test(value);
            });
        } else if (tipo === "nome" || tipo === "descricao" || tipo === "unidade") {
            inputUppercase(input);
        }
*/
        input.addEventListener("input", () => {
            validarInput(input, false);
        });

        input.addEventListener("blur", () => {
            validarInput(input);
        });
    });
};
