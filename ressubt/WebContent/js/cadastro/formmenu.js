export { FormMenu };

function FormMenu(anchors) {
    this.anchors = anchors;
    this.anchorSelected;
}
FormMenu.prototype = {
    constructor: FormMenu,
    links: ["/ressubt/control?action=ViewContribuinte", "/ressubt/control?action=ViewFinalidade", "/ressubt/control?action=ViewParticipante", "/ressubt/control?action=ViewProduto"],

    selecionarFormulario: function (anchor) {
        if (typeof this.anchorSelected === "undefined") {
            this.anchorSelected = anchor;
        } else {
            this.anchorSelected.removeAttribute("class");
            this.anchorSelected = anchor;
        }
        this.anchorSelected.className = "active";

        let data_index = anchor.getAttribute("data-index");
        let index = parseInt(data_index);
        return this.links[index];
    },
};
