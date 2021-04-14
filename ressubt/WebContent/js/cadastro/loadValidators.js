import { inputFilter, inputRulesPatterns } from "../../js/cadastro/inputFilter.js";

export const loadValidators = () => {
    const inputValidators = {};

    const inputs = document.querySelectorAll("input");
    inputs.forEach((input) => {
        const tipo = input.dataset.tipo;
        if (inputRulesPatterns[tipo]) {
            inputFilter(input, inputRulesPatterns[tipo]);
        }
    });
};
