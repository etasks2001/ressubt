export const validarNome = (input) => {
    input.style["text-transform"] = "uppercase";
    if (input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }
    input.setCustomValidity("");
    return;
};