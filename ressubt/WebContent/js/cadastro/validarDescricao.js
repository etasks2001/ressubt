export const validarDescricao = (input) => {
    console.log("digitando: " + input);
    const value = input.value;
    console.log("<<validarDescricao>>" + value);

    if (input.validity.tooShort) {
        input.setCustomValidity(">>>Descricao está em branco.");
        return;
    }
    input.setCustomValidity("");
    return;
};
