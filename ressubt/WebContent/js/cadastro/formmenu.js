"use strict";
export { FormMenu };

function FormMenu() {
    this.anchorSelected;
}
FormMenu.prototype = {
    constructor: FormMenu,
    view: ["Contribuinte", "Finalidade", "Participante", "Produto"],

    selecionarFormulario: function (anchor) {
        if (typeof this.anchorSelected === "undefined") {
            this.anchorSelected = anchor;
        } else {
            this.anchorSelected.removeAttribute("class");
            this.anchorSelected.style["pointer-events"] = "auto";
            this.anchorSelected = anchor;
        }
        this.anchorSelected.style["pointer-events"] = "none";
        this.anchorSelected.className = "active";

        let form_data_index = anchor.getAttribute("data-form-index");
        let index = parseInt(form_data_index);
        let formName = this.view[index];

        return `/ressubt/control?action=View${formName}`;
    },
};
