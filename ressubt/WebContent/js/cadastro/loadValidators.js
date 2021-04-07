import { validarInput } from "../../js/cadastro/validar.js";

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
        }
        input.addEventListener("input", () => {
            validarInput(input, false);
        });

        input.addEventListener("blur", () => {
            validarInput(input);
        });
    });
};
