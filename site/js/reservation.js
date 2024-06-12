import * as flou from "./flou.js";

export function display(restaurant) {
    // Gestion des données

    // Gestion de l'affichage
    let form = document.getElementById('form');
    form.innerHTML = `
<div class="image"></div>
<div class="spans">
        <span class="span_restaurant">${restaurant.NOM}</span>
        <!-- span complet ou disponible -->
    </div>
<div class="formulaire">
    <input type="text" id="nom" name="nom" placeholder="Nom" required>
    <input type="text" id="prenom" name="prenom" placeholder="Prénom" required>
    <input type="date" id="date" name="date" required>
    <input type="number" id="nbPersonnes" name="nbPersonnes" placeholder="Nombre de personnes" required>
    <button type="submit">Réserver</button>
</div>
<span class="span_place">3 / 10 places disponibles</span>
    `;
    flou.show();

    let spanComplet = document.createElement('span');
    /**
    if (restaurant.NBRESMAX === restaurant.NBRESACT) {
        spanComplet.innerHTML = "Complet";
        spanComplet.classList.add('span_complet');
    } else {
        spanComplet.innerHTML = "Disponible";
        spanComplet.classList.add('span_disponible');
    }
        **/
    spanComplet.innerHTML = "Disponible";
    spanComplet.classList.add('span_disponible');
    form.getElementsByClassName('spans')[0].appendChild(spanComplet);
}