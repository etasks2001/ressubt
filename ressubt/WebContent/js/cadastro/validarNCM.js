export const validarNCM = (input) => {
    console.log(input.event);
    const pattern = /\d{8}/;

    if (!pattern.test(input.value)) {
        input.setCustomValidity("NCM somente com 8 números.");
        return;
    }

    if (input.validity.tooShort) {
        input.setCustomValidity("erro");
        return;
    }

    input.setCustomValidity("");
    return;
};
