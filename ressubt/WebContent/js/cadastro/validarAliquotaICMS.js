export const validarAliquotaICMS = (input) => {
    const valor = parseFloat(input.value.replace(",", ".").replace(" ", ""));

    if (valor === 0) {
        input.setCustomValidity("Valor 0.");
        return;
    }

    if (input.validity.tooShort) {
        input.setCustomValidity("Está em branco.");
        return;
    }

    input.setCustomValidity("");
    return;
};
