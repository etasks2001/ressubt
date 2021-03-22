export { FormButtons };

function FormButtons(buttons) {
    this.buttons = buttons;
}

FormButtons.prototype = {
    constructor: FormButtons,
    disable: function () {
        for (let i = 0; i < this.buttons.length; i++) {
            this.buttons[i].disabled = !this.buttons[i].disabled;
        }
    },
};
