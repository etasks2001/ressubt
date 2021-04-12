export const validarNCM = (input) => {
    const pattern = /\d{8}/;

    if (!pattern.test(input.value) || input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }

    input.setCustomValidity("");
    return;
};
