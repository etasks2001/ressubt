import { validarDescricao } from "../../js/cadastro/validarDescricao.js";
import { validarCodigoFinalidade } from "../../js/cadastro/validarCodigoFinalidade.js";
import { validarCPF } from "../../js/cadastro/validarCPF.js";

const retornarMensagemDeErro = (tipo, validity) => {
    let mensagemDeErro = "";
    const tiposDeErro = ["valueMissing", "tooShort", "customError", "patternMismatch"];

    const mensagensDeErro = {
        descricao: {
            tooShort: "Descrição está em branco.",
            valueMissing: "Descrição está em branco.",
        },
        codigoFinalidade: {
            tooShort: "Código finalidade está em branco.",
            valueMissing: "Código finalidade está em branco.",
            patternMismatch: "Somente números para o código",
        },
        cpf: {
            valueMissing: "O CPF está em branco.",
            customError: "CPF é inválido.",
        },
    };

    tiposDeErro.forEach((erro) => {
        if (validity[erro]) {
            mensagemDeErro = mensagensDeErro[tipo][erro];
        }
    });

    return mensagemDeErro;
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
        descricao: (input) => validarDescricao(input),
        codigoFinalidade: (input) => validarCodigoFinalidade(input),
        cpf: (input) => validarCPF(input),
    };

    if (validadoresEspecificos[tipo]) {
        validadoresEspecificos[tipo](input);
    }

    if (!elementoEhValido) {
        const label = elementoPai.querySelector("label");
        const style = getComputedStyle(label);
        console.log(style.width);
        console.log(label);

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
