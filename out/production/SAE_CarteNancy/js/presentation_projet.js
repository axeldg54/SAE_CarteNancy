let fond = document.querySelector("#fond");
let images = document.querySelectorAll(".img-diag");

window.addEventListener("load", () => {
    afficherBigImg();
});

function afficherBigImg(){
    images.forEach(image => {
        image.addEventListener("click", () => {
            let bigImg = document.querySelector("#bigImg");
            bigImg.src = image.src;
            afficher();
            fond.addEventListener("click", () => {
                retirer();
                bigImg.src = "";
            })
        })
    })
}

function afficher(){
    fond.classList.remove("none");
    fond.classList.add("block");
}

function retirer(){
    fond.classList.remove("block");
    fond.classList.add("none");
}
