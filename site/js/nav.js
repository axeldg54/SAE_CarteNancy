import * as data from "./data.js";

const LEGENDE = initLegend;
const METEO = displayMeteo;

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

export function displayLegend(navInfo) {
    return new Promise(async (resolve) => {
        let stations = await data.getStationInfo();
        data.getIncidents().then(incidents => {
            let nbIncidents = incidents.incidents.length;
            let nbStations = stations.data.stations.length;
            data.getRestaurants().then(restaurants => {
                let nbRestaurants = restaurants.length;
                data.getSup().then(sup => {
                    let nbSup = sup.etablissements.length;
                    navInfo.innerHTML = `
             <h2>Légende</h2>
                <ul>
                <li class="legend_item"><span class="legende-velo"></span> <img class="icon" src="./img/icon_velo3.png">
                    <p><span class="span_moyen span_gras">${nbStations}</span> stations de velib</p></li>
                <li class="legend_item"><span class="legende-incident"></span> <img class="icon" src="./img/icon_incident.png">
                    <p><span class="span_moyen span_gras">${nbIncidents}</span> incidents</p></li>
                <li class="legend_item"><span class="legende-restaurant"></span> <img class="icon" src="./img/icon_restaurant.png">
                    <p><span class="span_moyen span_gras">${nbRestaurants}</span> restaurants</p></li>
                <li class="legend_item"><span class="legende-sup"></span> <img class="icon" src="./img/icon_sup.png">
                    <p><span class="span_moyen span_gras">${nbSup}</span> établissements supérieurs</p></li>
                </ul>`;
                    resolve();
                })
            });
        }).catch(e => {
            showWithoutData();
            resolve();
        });
    });
}

export async function displayMeteo(navInfo) {
    // Récupération des données
    let meteo = await data.getClimat();
    let date = new Date();
    let heure = date.getHours();
    let jour = date.getDate();
    let mois = date.getMonth() + 1;
    if (mois < 10) mois = `0${mois}`;
    let annee = date.getFullYear();

    // Récupération des 3 prochaines heures
    let tab = [];
    let i = 0;
    while (tab.length < 3) {
        date = `${annee}-${mois}-${jour} ${heure + i}:00:00`;
        if (meteo[date]) {
            tab.push([heure + i + ":00", meteo[date]]);
        }
        i++;
    }
    navInfo.innerHTML = `<h2>Météo</h2><div id="meteo"></div>`;

    // Affichage pour chaque heure
    tab.forEach(temp => {
        // Récupération des données
        let heure = temp[0].split(":").join("h");
        let temperature = (temp[1]["temperature"]["2m"] - 273.15).toFixed(0);
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
            alert(`Il fait ${temperature}°C à ${heure} à Nancy`);
        });

        document.getElementById('meteo').appendChild(meteoItem);
    })
}

function toggleShow(element) {
    for (let i = 0; i < document.getElementById('map').getElementsByTagName('img').length; i++) {
        if (document.getElementById('map').getElementsByTagName('img')[i].src.includes(element)) {
            if (document.getElementById('map').getElementsByTagName('img')[i].style.display === 'none') {
                document.getElementById('map').getElementsByTagName('img')[i].style.display = 'block';
            } else {
                document.getElementById('map').getElementsByTagName('img')[i].style.display = 'none';
            }
        }
    }
}

function showWithoutData() {
    document.getElementById('nav_info').innerHTML = `
            <h2>Légende</h2>
            <ul>
                <li class="legend_item"><span class="legende-velo"></span> <img class="icon" src="./img/icon_velo3.png">
                    <p>stations de velib</p></li>
                <li class="legend_item"><span class="legende-incident"></span> <img class="icon" src="./img/icon_incident.png">
                    <p>incidents</p></li>
                <li class="legend_item"><span class="legende-restaurant"></span> <img class="icon" src="./img/icon_restaurant.png">
                    <p>restaurants</p></li>
                <li class="legend_item"><span class="legende-sup"></span> <img class="icon" src="./img/icon_sup.png">
                    <p>établissements supérieurs</p></li>
            </ul>`;
}

async function initLegend(navInfo) {
    displayLegend(navInfo).then(() => {
        // Evénement
        for (let i = 0; i < navInfo.getElementsByTagName('li').length; i++) {
            navInfo.getElementsByTagName('li')[i].addEventListener('click', () => {
                let element = navInfo.getElementsByTagName('li')[i].getElementsByTagName('img')[0].src.split('/')[navInfo.getElementsByTagName('li')[i].getElementsByTagName('img')[0].src.split('/').length - 1];
                toggleShow(element);
            });
        }
    });
}