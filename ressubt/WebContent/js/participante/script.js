import { URL_PAIS, URL_UF, URL_MUNICIPIO, requestJson } from "../../js/util/script.js";
import { loadValidators } from "../../js/cadastro/loadValidators.js";
const selectUf = document.querySelector("select[name=uf]");
const selectMunicipio = document.querySelector("select[name=cod_mun]");
const selectPais = document.querySelector("select[name=cod_pais]");

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
