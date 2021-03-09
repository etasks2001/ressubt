
var iframe = document.querySelector("iframe");

let menu = document.querySelectorAll(".menu");

for (let i = 0; i < menu.length; i++) {
	menu[i].addEventListener("click", function(event) {
		let name = event.currentTarget.name;
		let src = "";
		switch (name) {
		case "contribuinte":
			src = "/ressubt/control?action=ViewContribuinte";
			break;
		case "finalidade":
			src = "/ressubt/control?action=ViewFinalidade";
			break;
		default:
			break;
		}
		iframe.src = src;
	});
}
