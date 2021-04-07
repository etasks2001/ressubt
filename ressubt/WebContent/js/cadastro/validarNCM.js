export const validarNCM = (input) => {
    if (input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }

    input.setCustomValidity("");
    return;
};
