import { URL_UF, URL_MUNICIPIO, requestJson } from "../../js/util/script.js";

const selectUf = document.querySelector("select[name=uf]");
const selectMunicipio = document.querySelector("select[name=cod_mun]");

window.onload = () => {
    requestJson(URL_UF, selectUf);
};

selectUf.addEventListener("change", (event) => {
    var option = event.target.options[event.target.selectedIndex];

    selectMunicipio.innerHTML = "";
    requestJson(URL_MUNICIPIO + option.value, selectMunicipio);
});
