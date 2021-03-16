export { Operation };

class Operation {
    constructor(doc) {
        this.btnNovo = doc.querySelector(".novo");
        this.btnPesquisar = doc.querySelector(".pesquisar");
        this.btnGravar = doc.querySelector(".gravar");
        this.btnCancelar = doc.querySelector(".cancelar");
    }

    disableButtons() {
        this.btnNovo.disabled = !this.btnNovo.disabled;
        this.btnGravar.disabled = !this.btnGravar.disabled;
        this.btnCancelar.disabled = !this.btnCancelar.disabled;
        this.btnPesquisar.disabled = !this.btnPesquisar.disabled;
    }

    disableFields(flag) {
        for (let i = 0; i < this.controls.length; i++) {
            this.controls[i].disabled = flag;
        }
    }

    selecionarControleFormulario(iframe) {
        this.controls = iframe.contentWindow.document.querySelectorAll("select, input[type='text']");
        console.log(this.controls);
        this.disableFields(this.controls, true);
    }
}
