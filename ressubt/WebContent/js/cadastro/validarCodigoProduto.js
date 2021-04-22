export const validarCodigoProduto = (input) => {
    const pattern = /\d{3}\.\w{2}\.\d{3}/;

    if (!pattern.test(input.value) || input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }
    input.setCustomValidity("");
    return;
};
