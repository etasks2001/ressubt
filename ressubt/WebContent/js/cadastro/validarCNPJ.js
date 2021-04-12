export const validarCNPJ = (input) => {
    const pattern = /\d{14}/;

    if (!pattern.test(input.value) || input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }
    input.setCustomValidity("");
    return;
};
