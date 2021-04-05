import { validarDescricao } from "../../js/cadastro/validarDescricao.js";

const retornarMensagemDeErro = (tipo, validity) => {
    const tiposDeErro = ["muito curto"];

    const mensagensDeErro = {
        descricao: {
            tooShort: "muito curto",
        },
    };
};
export const validarInput = (input, adicionarErro = true) => {
    const elementoEhValido = input.validity.valid;
    console.log(input.validity);

    const classeElementoErro = "erro-validacao";
    const classeInputErro = "possui-erro-validacao";
    const elementoPai = input.parentNode;

    const elementoErroExiste = elementoPai.querySelector(`.${classeElementoErro}`);
    const elementoErro = elementoErroExiste || document.createElement("div");

    const tipo = input.dataset.tipo;

    const validadoresEspecificos = {
        descricao: (input) => {
            validarDescricao(input);
        },
    };

    if (validadoresEspecificos[tipo]) {
        validadoresEspecificos[tipo](input);
    }

    if (!elementoEhValido) {
        elementoErro.className = classeElementoErro;
        elementoErro.textContent = retornarMensagemDeErro(tipo, input.validity);
        if (adicionarErro) {
            input.after(elementoErro);
            input.classList.add(classeInputErro);
        }
    } else {
        elementoErro.remove();
        input.classList.remove(classeInputErro);
    }
};
