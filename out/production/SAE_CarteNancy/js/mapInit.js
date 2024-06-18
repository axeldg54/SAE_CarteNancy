import * as data from "./data.js";
import * as carte from "./carte.js";
import * as reservation from "./reservation.js";

export async function initPopupVelib(station) {
    // On récupère les données de la station
    let stationStatus = await data.getStationStatus();
    stationStatus = stationStatus.data.stations;
    let status = stationStatus.find(s => s.station_id === station.station_id);
    let name = station.name.split('0').join('');

    // On crée le contenu du popup
    let content = `
        <h2>${name}</h2>
        <ul>
            <li>Adresse: <span class="span_gras">${station.address}</span></li>
            <li>Nombre de vélos disponibles: <span class="span_gras">${status.num_bikes_available}</span></li>
            <li>Nombre de places disponibles: <span class="span_gras">${status.num_docks_available}</span></li>
        </ul>
          
    `;

    // On ajoute le popup à la carte
    carte.addPopup(carte.MAP, station.lat, station.lon, carte.ICON_VELO, content);
}

export async function initPopupIncident(incident, lat, long) {
    // On récupère les données de la station
    let dateStart = incident.starttime.split('T')[0].split('-')[2] + '/' + incident.starttime.split('T')[0].split('-')[1] + '/' + incident.starttime.split('T')[0].split('-')[0];
    let dateEnd = incident.endtime.split('T')[0].split('-')[2] + '/' + incident.endtime.split('T')[0].split('-')[1] + '/' + incident.endtime.split('T')[0].split('-')[0];
    let temps = dateStart + ' - ' + dateEnd;

    // On crée le contenu du popup
    let content = `
        <h2>${incident.type}</h2>
        <ul>
            <li>Description: <span class="span_gras">${incident.short_description}</span></li>
            <li>Adresse: <span class="span_gras">${incident.location.street}</span></li>
            <li>Temps: <span class="span_gras">${temps}</span></li>
        </ul>
    `;

    // On ajoute le popup à la carte
    carte.addPopup(carte.MAP, lat, long, carte.ICON_INCIDENT, content);
}

export async function initPopupRestaurant(restaurant) {
    let etoiles = '';
    for (let i = 0; i < Math.round(restaurant.NOTE); i++) {
        etoiles += '⭐';
    }
    // On crée le contenu du popup
    let content = document.createElement('div');
    content.classList.add('popup-restaurant');
    content.innerHTML = `
        <h2>${restaurant.NOM}</h2>
        <ul>
            <li>Adresse: <span class="span_gras">${restaurant.ADRESSE}</span></li>
            <li>Téléphone: <span class="span_gras">${restaurant.TELEPHONE}</span></li>
            <li>Capacité: <span class="span_gras">${restaurant.NBRESMAX}</span></li>
            <li>Note: <span class="span_gras">${etoiles}</span></li>
        </ul>
    `;

    // On crée le bouton de réservation
    let button = document.createElement('button');
    button.classList.add('reserver');
    button.innerHTML = "Réserver";
    button.addEventListener('click', () => {
        reservation.display(restaurant, new Date(), 0);
    });
    content.appendChild(button);

    // On crée le bouton de notation
    let buttonNote = document.createElement('button');
    buttonNote.classList.add('noter');
    buttonNote.innerHTML = "Noter";
    buttonNote.addEventListener('click', () => {
        //initNoter(restaurant);
    });
    content.appendChild(buttonNote);

    // On ajoute le popup à la carte
    carte.addPopup(carte.MAP, restaurant.LATITUDE, restaurant.LONGITUDE, carte.ICON_RESTAURANT, content);
}
