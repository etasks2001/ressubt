export const validarAliquotaICMS = (input) => {
    const valor = parseFloat(input.value.replace(",", ".").replace(" ", ""));

    if (valor === 0 || input.validity.tooShort) {
        input.setCustomValidity("Erro");
        return;
    }

    input.setCustomValidity("");
    return;
};
