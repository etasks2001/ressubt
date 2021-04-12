export const validarUnidade = (input) => {
    input.style["text-transform"] = "lowercase";
    if (input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }
    input.setCustomValidity("");
    return;
};
