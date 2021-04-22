import { loadValidators } from "../../js/cadastro/loadValidators.js";

window.onload = () => {
    loadValidators();

    const cod_item = document.querySelector("#cod_item");
    const maskCod_item = new Inputmask("999.**.999");
    maskCod_item.mask(cod_item);

    console.log(maskCod_item);
};
