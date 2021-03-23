"use strict";
export { FormButtons };

function FormButtons(buttons) {
    this.buttons = buttons;
}

FormButtons.prototype = {
    constructor: FormButtons,
    disable: function () {
        this.buttons.forEach((x, i, b) => {
            b[i].disabled = !b[i].disabled;
        });
    },
};
