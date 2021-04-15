export const validarInscricaoEstadual = (input) => {
    const pattern = /\d{5,14}/;

    if (!pattern.test(input.value) || input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }
    input.setCustomValidity("");
    return;
};
