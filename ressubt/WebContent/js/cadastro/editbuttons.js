"use strict";
export { EditButtons };

function EditButtons(doc) {
    this.btnNovo = doc.querySelector("#novo");
    this.btnGravar = doc.querySelector("#gravar");
    this.btnCancelar = doc.querySelector("#cancelar");
    this.btnPesquisar = doc.querySelector("#pesquisar");
}

EditButtons.prototype = {
    constructor: EditButtons,
    setEditing: function () {
        this.btnNovo.disabled = true;
        this.btnGravar.disabled = false;
        this.btnCancelar.disabled = false;
        this.btnPesquisar.disabled = true;
    },

    setDefault: function () {
        this.btnNovo.disabled = false;
        this.btnGravar.disabled = true;
        this.btnCancelar.disabled = true;
        this.btnPesquisar.disabled = false;
    },

    disableAll: function () {
        this.btnNovo.disabled = true;
        this.btnGravar.disabled = true;
        this.btnCancelar.disabled = true;
        this.btnPesquisar.disabled = true;
    },
};
