export const validarCodigoFinalidade = (input) => {
    if (input.validity.tooShort) {
        input.setCustomValidity("");
        return;
    }
    input.setCustomValidity("");
    return;
};
