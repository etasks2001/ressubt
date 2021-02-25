console.log("ola contribuinte");

var btnGravar = document.querySelector("button");
console.log(btnGravar);

btnGravar.addEventListener("click", (event) => {
    event.preventDefault();
    console.log("botão pressionado.");
});
