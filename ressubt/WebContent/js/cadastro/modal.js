"use strict";
export { ModalMessageAction, ModalConfirmAction };

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
