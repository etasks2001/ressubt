import { validarInput } from "../../js/cadastro/validar.js";
import { inputFilter } from "../../js/cadastro/inputFilter.js";
import { inputUppercase } from "../../js/cadastro/inputUpperCase.js";
export const loadValidators = () => {
    const inputs = document.querySelectorAll("input");

    inputs.forEach((input) => {
        if (input.dataset.tipo === "aliquotaICMS") {
            SimpleMaskMoney.setMask(input, {
                allowNegative: false,
                negativeSignAfter: false,
                prefix: "",
                suffix: "",
                fixed: true,
                fractionDigits: 2,
                decimalSeparator: ",",
                thousandsSeparator: "",
                cursor: "end",
            });
        } else if (input.dataset.tipo === "codigoFinalidade" || input.dataset.tipo === "cnpj" || input.dataset.tipo === "cnpjcpf" || input.dataset.tipo === "inscricaoestadual" || input.dataset.tipo === "versaodoleiaute" || input.dataset.tipo === "codigodebarras" || input.dataset.tipo === "ncm" || input.dataset.tipo === "cest") {
            inputFilter(input, function (value) {
                return /^-?\d*$/.test(value);
            });
        } else if (input.dataset.tipo === "nome" || input.dataset.tipo === "descricao" || input.dataset.tipo === "unidade") {
            inputUppercase(input);
        }

        input.addEventListener("input", () => {
            validarInput(input, false);
        });

        input.addEventListener("blur", () => {
            validarInput(input);
        });
    });
};
