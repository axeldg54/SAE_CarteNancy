import * as data from "./data.js";

const LEGENDE = initLegende;
const METEO = initMeteo;

const ITEMS = [LEGENDE, METEO];
let ITERATOR = 0;
export function initNav() {
    // Récupération des éléments du DOM
    let navInfo = document.getElementById('nav_info');
    let navNext = document.getElementById('nav_next');
    let navPrev = document.getElementById('nav_prev');

    // Initialisation de l'affichage
    ITEMS[ITERATOR](navInfo);

    // Gestion des événements
    navNext.addEventListener('click', () => {
        if (ITERATOR === ITEMS.length - 1) {
            ITERATOR = 0;
        } else {
            ITERATOR = (ITERATOR + 1) % ITEMS.length;
        }
        ITEMS[ITERATOR](navInfo);
    });

    navPrev.addEventListener('click', () => {
        if (ITERATOR === 0) {
            ITERATOR = ITEMS.length - 1;
        } else {
            ITERATOR = (ITERATOR - 1) % ITEMS.length;
        }
        ITEMS[ITERATOR](navInfo)
    });
}

export async function initLegende(navInfo) {
    let stations = await data.getStationInfo();
    let nbStations = stations.data.stations.length;
    navInfo.innerHTML = `
    <h2>Légende</h2>
            <ul>
                <li class="legend_item"><span class="legende-velo"></span> <img class="icon" src="./img/icon_velo3.png">
                    <p> : <span class="span_gras">${nbStations}</span> stations de velib</p></li>
                <li class="legend_item"><span class="legende-incident"></span> <img class="icon" src="./img/icon_incident.png">
                    <p> : <span class="span_gras">xx</span> incidents</p></li>
                <li class="legend_item"><span class="legende-restaurant"></span> <img class="icon" src="./img/icon_restaurant.png">
                    <p> : <span class="span_gras">xx</span> restaurants</p></li>
            </ul>
            `;
}

export async function initMeteo(navInfo) {
    // Récupération des données
    let meteo = await data.getClimat();
    let date = new Date();
    let heure = date.getHours();
    let jour = date.getDate();
    let mois = date.getMonth()+1;
    if (mois < 10) mois = `0${mois}`;
    let annee = date.getFullYear();

    // Récupération des 3 prochaines heures
    let tab = [];
    let i = 0;
    while (tab.length < 3) {
        date = `${annee}-${mois}-${jour} ${heure+i}:00:00`;
        if (meteo[date]) {
            tab.push([heure+i+":00", meteo[date]]);
        }
        i++;
    }
    navInfo.innerHTML = `<h2>Météo</h2><div id="meteo"></div>`;

    // Affichage pour chaque heure
    tab.forEach(temp => {
        // Récupération des données
        let heure = temp[0].split(":").join("h");
        let temperature = (temp[1]["temperature"]["2m"]-273.15).toFixed(0);
        let nebulosite = temp[1]["nebulosite"]["totale"];
        let pluie = temp[1]["pluie"];

        // Choix de l'icon
        let icon = "";
        if (nebulosite < 20) {
            icon = "soleil.png";
        } else if (nebulosite < 50) {
            icon = "nuageux.png";
        } else if (nebulosite > 50 && pluie <= 1) {
            icon = "nuage.png";
        } else {
            icon = "pluvieux.png";
        }

        // Affichage
        let meteoItem = document.createElement('div');
        meteoItem.classList.add('meteo_item');
        meteoItem.innerHTML = `
            <img src="./img/${icon}">
                <div class="meteo_item_infos">
                    <span class="span_grand span_gras">${temperature}°C</span>
                    <span class="span_petit">Nancy, France - ${heure}</span> 
                </div>`;

        // Evénement
        meteoItem.addEventListener('click', () => {
            alert("Clic sur la météo");
        });

        document.getElementById('meteo').appendChild(meteoItem);
    })
}