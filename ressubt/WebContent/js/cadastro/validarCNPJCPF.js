export const validarCNPJCPF = (input) => {
    const pattern = /^(\d{14}|\d{11})$/;

    if (!pattern.test(input.value) || input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }
    input.setCustomValidity("");
    return;
};
