import { URL_PAIS, URL_UF, URL_MUNICIPIO, requestJson } from "../../js/util/script.js";
import { loadValidators } from "../../js/cadastro/loadValidators.js";
const selectUf = document.querySelector("select[name=uf]");
const selectMunicipio = document.querySelector("select[name=cod_mun]");
const selectPais = document.querySelector("select[name=cod_pais]");

let tipoDePessoa = document.querySelector("#tipodepessoa");

let selector = document.querySelector("#cnpj_cpf");

tipoDePessoa.addEventListener("click", (event) => {
    let option = event.target.options[event.target.selectedIndex];
    console.log(option.value);
    console.log(selector);
    let im;
    if (option.value === "1") {
        im = new Inputmask("99.999.999/9999-99");
    } else if (option.value === "2") {
        im = new Inputmask("999.999.999-99");
    }
    im.mask(selector);
});

window.onload = () => {
    loadValidators();
    requestJson(URL_UF, selectUf);
    requestJson(URL_PAIS, selectPais);
};

selectUf.addEventListener("change", (event) => {
    var option = event.target.options[event.target.selectedIndex];

    selectMunicipio.innerHTML = "";
    requestJson(URL_MUNICIPIO + option.value, selectMunicipio);
});
