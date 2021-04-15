export const validarCodigoFinalidade = (input) => {
    const pattern = /\d{2}/;
    if (!pattern.test(input.value) || input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }
    input.setCustomValidity("");
    return;
};
