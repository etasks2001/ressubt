"use strict";
export { ModalMessageAction, ModalConfirmAction, ModalPesquisaAction };

function ModalMessageAction(modalMessage) {
    this.modalMessage = modalMessage;
    this.modalMessage.addEventListener("click", (event) => {
        let className = event.target.className;
        if (className === "modalMessage" || className === "closeMessage" || className === "btnOk") {
            this.close();
        }
    });
}

ModalMessageAction.prototype = {
    constructor: ModalMessageAction,
    close: function () {
        this.modalMessage.style.display = "none";
    },

    open: function () {
        this.modalMessage.style.display = "block";
    },
};

function ModalConfirmAction(modalConfirm) {
    this.modalConfirm = modalConfirm;
    this.modalConfirm.addEventListener("click", (event) => {
        let className = event.target.className;
        if (className === "modalConfirm" || className === "closeMessage" || className === "btnCancelar") {
            this.close();
        }
    });
}
ModalConfirmAction.prototype = {
    constructor: ModalConfirmAction,
    close: function () {
        this.modalConfirm.style.display = "none";
    },

    open: function () {
        this.modalConfirm.style.display = "block";
    },
};

function ModalPesquisaAction(modalPesquisa) {
    this.modalPesquisa = modalPesquisa;
    this.modalPesquisa.addEventListener("click", (event) => {
        let className = event.target.className;
        if (className === "modalPesquisa" || className === "closePesquisa") {
            this.close();
        }
    });
}
ModalPesquisaAction.prototype = {
    constructor: ModalPesquisaAction,
    close: function () {
        this.modalPesquisa.style.display = "none";
    },

    open: function () {
        this.modalPesquisa.style.display = "block";
    },
};
