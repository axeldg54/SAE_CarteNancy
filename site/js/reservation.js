import * as flou from "./flou.js";
import * as data from "./data.js";

export async function display(restaurant, date, nbPersonnes) {

    // Gestion des dates
    let todayHtml = dateToHtml(new Date());
    let dateHtml = dateToHtml(date);
    let dateFr = dateToFr(date);

    // Récupération des données de la réservation
    let reservation = await data.getReservation(restaurant.ID, dateFr);
    let nbPlaceRestante = reservation.RESTANT;
    let complet = false;
    if (nbPlaceRestante <= 0) complet = true;

    // Gestion de l'affichage
    let form = document.getElementById('form');
    form.innerHTML = `
        <div class="image" style="background-image: url(${restaurant.IMAGE})"></div>
            <div class="spans">
                <span class="span_restaurant">${reservation.NOM}</span>
                <!-- span complet ou disponible -->
            </div>
        <div class="formulaire">
            <input type="text" id="nom" name="nom" placeholder="Nom" required>
            <input type="text" id="prenom" name="prenom" placeholder="Prénom" required>
            <input type="text" id="tel" name="tel" placeholder="Téléphone" required>
            <input type="date" id="date" name="date" value="${dateHtml}" min="${todayHtml}" required>
            <input type="number" id="nbPersonnes" name="nbPersonnes" placeholder="Nombre de personnes" value="${nbPersonnes}" min="0" max="${nbPlaceRestante}" required>
            <button type="button" id="button_form">Réserver</button>
        </div>
        <span class="span_place"><span id="nbRestant">${nbPlaceRestante}</span> / ${reservation.NBRESMAX} places disponibles</span>`;
    flou.show(); // On affiche le formulaire

    // Gestion du span complet ou disponible
    form.getElementsByClassName('spans')[0].appendChild(getSpanComplet(complet));

    // Gestion des événements
    changeDateInit(restaurant);
    changeNbPersonnesInit(reservation);
    document.getElementById('button_form').addEventListener('click', async () => {
        let ratio = parseInt(document.getElementById('nbRestant').innerHTML);
        await submitForm(ratio, restaurant.ID);
    });
}

function changeDateInit(restaurant) {
    document.getElementById('date').addEventListener('change', async () => {
        let date = document.getElementById('date').value;
        await display(restaurant, new Date(date), 0);
    });
}

function changeNbPersonnesInit(reservation) {
    let nbRestant = reservation.RESTANT;
    document.getElementById('nbPersonnes').addEventListener('change', () => {
        let nbPersonnes = document.getElementById('nbPersonnes').value;
        document.getElementById('nbRestant').innerHTML = parseInt(nbRestant) - parseInt(nbPersonnes);
    });
}

function getSpanComplet(complet) {
    // Gestion du span complet ou disponible
    let spanComplet = document.createElement('span');
    if (complet) {
        spanComplet.innerHTML = "Complet";
        spanComplet.classList.add('span_complet');
    } else {
        spanComplet.innerHTML = "Disponible";
        spanComplet.classList.add('span_disponible');
    }
    return spanComplet;
}

function dateToHtml(date) {
    let month = date.getMonth() + 1;
    if (month < 10) month = '0' + month;
    let day = date.getDate();
    if (day < 10) day = '0' + day;
    return date.getFullYear() + '-' + month + '-' + day;
}

function dateToFr(date) {
    let month = date.getMonth() + 1;
    if (month < 10) month = '0' + month;
    let day = date.getDate();
    if (day < 10) day = '0' + day;
    return day + '/' + month + '/' + date.getFullYear();
}

async function submitForm(ratio, id) {
    // Si le nombre de places restantes est suffisant
    if (ratio >= 0) {
        // Récupération des données
        let nom = document.getElementById('nom').value;
        let prenom = document.getElementById('prenom').value;
        let tel = document.getElementById('tel').value;
        let date = document.getElementById('date').value;
        let dateFr = date.split('-')[2] + '/' + date.split('-')[1] + '/' + date.split('-')[0];
        let nbPersonnes = document.getElementById('nbPersonnes').value;

        if (nom === '' || prenom === '' || tel === '' || date === '') {
            alert('Veuillez remplir tous les champs');
            return;
        } else if (nbPersonnes <= 0) {
            alert('Veuillez rentrer un nombre de personnes valide');
            return;
        }

        // Envoi des données
        await data.postReservation(id, dateFr, nom, prenom, tel, nbPersonnes);
        flou.hide(); // On cache le formulaire
    } else {
        alert('Pas assez de places disponibles');
    }
}