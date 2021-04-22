const ehUmCPFComNumerosRepetidos = (cpf) => {
    const cpfsInvalidos = ["00000000000", "11111111111", "22222222222", "33333333333", "44444444444", "55555555555", "66666666666", "77777777777", "88888888888", "99999999999"];

    return cpfsInvalidos.includes(cpf);
};

const calcularTotal = (multiplicaodor) => (resultado, numeroAtual) => resultado + numeroAtual * multiplicaodor--;

const calcularDigito = (parteCPF, multiplicador) => {
    const total = partecpf.reduce(calcularTotal(multiplicador), 0);
    const resto = total % 11;
    const digito = 11 - resto;
    if (resto > 9) {
        return 0;
    }
    return digito;
};
export const validarCPF = (input) => {
    const cpfNumeros = input.value.replace(/\D/g, "");

    console.log("Numeros CPF: " + cpfNumeros);
    console.log("Numeros CPF : " + cpfNumeros.length);

    if (cpfNumeros.length === 0) {
        input.setCustomValidity("CPF em branco");
        return;
    }

    if (ehUmCPFComNumerosRepetidos(cpfNumeros)) {
        input.setCustomValidity("CPF inválido.");
        return;
    }

    const primeiraParteCPF = cpfNumeros.substr(0, 9).split("");
    const primeiroDigitoCPF = Number(cpfNumeros.charAt(9));
    const primeiroDigitoCalculado = calcularDigito(primeiraParteCPF, 10);

    if (primeiroDigitoCalculado !== primeiroDigitoCalculado) {
        input.setCustomValidity("CPF inválido.");
        return;
    }

    const segundaParteCPF = cpfNumeros.substr(0, 10).split("");
    const segundoDigitoCPF = Number(cpfNumeros.charAt(10));
    const segundoDigitoCalculado = calcularDigito(primeiraParteCPF, 11);

    if (segundoDigitoCalculado !== segundoDigitoCalculado) {
        input.setCustomValidity("CPF inválido.");
        return;
    }

    input.setCustomValidity("");
    return;
};
