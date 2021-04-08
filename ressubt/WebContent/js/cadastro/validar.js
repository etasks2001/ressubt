import { validityStatesError } from "../../js/cadastro/validityStates.js";
import { validadoresEspecificos } from "../../js/cadastro/validators.js";
import { errorMessages } from "../../js/cadastro/mensagensDeValidacao.js";

const retornarMensagemDeErro = (tipo, validity) => {
    let mensagemDeErro = "";
    validityStatesError.forEach((erro) => {
        if (validity[erro]) {
            mensagemDeErro = errorMessages[tipo][erro];
        }
    });

    return mensagemDeErro;
};
export const validarInput = (input, adicionarErro = true) => {
    const elementoEhValido = input.validity.valid;

    const classeElementoErro = "erro-validacao";
    const classeInputErro = "possui-erro-validacao";
    const elementoPai = input.parentNode;

    const elementoErroExiste = elementoPai.querySelector(`.${classeElementoErro}`);
    const elementoErro = elementoErroExiste || document.createElement("div");

    const tipo = input.dataset.tipo;

    if (validadoresEspecificos[tipo]) {
        validadoresEspecificos[tipo](input);
    }

    if (!elementoEhValido) {
        const label = elementoPai.querySelector("label");
        const style = getComputedStyle(label);

        elementoErro.className = classeElementoErro;
        elementoErro.style.marginLeft = style.width;

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
