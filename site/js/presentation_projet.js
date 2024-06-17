let fond = document.querySelector("#fond");
let images = document.querySelectorAll(".img-diag");
let listeZonesCode = document.querySelectorAll(".zone_de_code");

window.addEventListener("load", () => {
    afficherBigImg();
    copierCode();
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

function copierCode(){
    listeZonesCode.forEach(zone => {
        let idZone = zone.id.split("_")[1]; // on récupère l'id de la zone pour ajouter l'event sur le bon bouton
        let copyButton = zone.querySelector("#copy_button_" + idZone);
        copyButton.addEventListener("click", () => {
            let contenuZone = zone.querySelector("pre").textContent;
            navigator.clipboard.writeText(contenuZone).then(() => {
                console.log(`Texte copié avec succès : ${contenuZone}`);
            }).catch(err => {
                console.error(`Erreur lors de la copie : ${err}`);
            });
        });
    });
}