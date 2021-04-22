export const validarCNPJCPF = (input) => {
    const numeros = input.value.replace(/\D/g, "");
    const pattern = /^(\d{14}|\d{11})$/;

    if (!pattern.test(numeros) || input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }
    input.setCustomValidity("");
    return;
};
