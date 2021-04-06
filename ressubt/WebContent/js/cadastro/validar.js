import { validarDescricao } from "../../js/cadastro/validarDescricao.js";
import { validarCodigoFinalidade } from "../../js/cadastro/validarCodigoFinalidade.js";
import { validarCPF } from "../../js/cadastro/validarCPF.js";
import { validarNome } from "../../js/cadastro/validarNome.js";
import { validarCNPJ } from "../../js/cadastro/validarCNPJ.js";
import { validarInscricaoEstadual } from "../../js/cadastro/validarInscricaoEstadual.js";
import { validarVersaoDoLeiaute } from "../../js/cadastro/validarVersaoDoLeiaute.js";

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
        nome: {
            tooShort: "Nome está em branco.",
            valueMissing: "Nome está em branco.",
        },
        cnpj: {
            tooShort: "CNPJ em branco.",
            valueMissing: "CNPJ em branco.",
        },
        inscricaoestadual: {
            tooShort: "Inscricao Estadual em branco.",
            valueMissing: "Inscricao Estadual em branco.",
        },
        versaodoleiaute: {
            tooShort: "Versão do Leiaute está em branco.",
            valueMissing: "Versão do Leiaute está em branco.",
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
        nome: (input) => validarNome(input),
        cnpj: (input) => validarCNPJ(input),
        inscricaoestadual: (input) => validarInscricaoEstadual(input),
        versaodoleiaute: (input) => validarVersaoDoLeiaute(input),
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
