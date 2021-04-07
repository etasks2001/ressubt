export const validarAliquotaICMS = (input) => {
    const valor = input.formatToNumber();

    if (valor === 0 || input.validity.tooShort) {
        input.setCustomValidity("Erro");
        return;
    }

    input.setCustomValidity("");
    return;
};
