"use strict";
export { ModalAction };

function ModalAction(modalMessage) {
    this.modalMessage = modalMessage;
    this.modalMessage.addEventListener("click", (event) => {
        let target = event.target;
        let className = target.className;
        let hasClose = className.search("close") >= 0;

        if (className === "modal" || hasClose) {
            this.close();
        }
    });
    this.WireEvents = function () {};
}

ModalAction.prototype = {
    constructor: ModalAction,
    close: function () {
        this.modalMessage.style.display = "none";
    },
    open: function () {
        this.modalMessage.style.display = "block";
    },
};
