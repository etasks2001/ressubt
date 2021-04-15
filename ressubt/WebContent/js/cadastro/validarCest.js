export const validarCest = (input) => {
    const pattern = /\d{7}/;

    if (!pattern.test(input.value) || input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }

    input.setCustomValidity("");
    return;
};
