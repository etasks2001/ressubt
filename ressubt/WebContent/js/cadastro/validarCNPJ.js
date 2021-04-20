export const validarCNPJ = (input) => {
    const pattern = /\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}/;

    if (!pattern.test(input.value) || input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }

    input.setCustomValidity("");
    return;
};
