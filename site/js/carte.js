import * as data from "./data.js";
import * as mapInit from "./mapInit.js";

export let MAP;
export const ICON_VELO = L.icon({
    iconUrl: './img/icon_velo3.png',
    iconSize: [25,25],
    iconAnchor: [16, 37],
    popupAnchor: [0, -37]
});
export const ICON_INCIDENT = L.icon({
    iconUrl: './img/icon_incident.png',
    iconSize: [25,25],
    iconAnchor: [16, 37],
    popupAnchor: [0, -37]
});
export const ICON_RESTAURANT = L.icon({
    iconUrl: './img/icon_restaurant.png',
    iconSize: [25,25],
    iconAnchor: [16, 37],
    popupAnchor: [0, -37]
});

export function initMap() {
    let map = L.map('map').setView([48.687,  6.19], 13);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    MAP = map;

    map.addEventListener('contextmenu', (e) => {
        let content = `
        <form id="formRestaurant">
            <img src="./img/icon_restaurant.png">
            <h2>Ajouter un restaurant</h2>
            <input type="text" id="nom" name="nom" placeholder="Nom" required>
            <input type="text" id="adresse" name="adresse" placeholder="Adresse" required>
            <input type="tel" id="tel" name="tel" placeholder="Téléphone" required>
            <input type="number" id="capacite" name="capacite" placeholder="Capacité" required>
            <input type="number" id="note" name="note" placeholder="Note" required>
            <input type="text" id="photo" name="photo" placeholder="Photo [url]" required>
            <button type="button" id="button_form">Ajouter</button>
        </form>
        `;

        let lat = e.latlng.lat;
        let lon = e.latlng.lng;
        // créé un popup et clique dessus
        L.popup()
            .setLatLng([lat, lon])
            .setContent(content)
            .openOn(map);

        document.getElementById('button_form').addEventListener('click', async () => {
            let id;
            let restaurants = await data.getRestaurants();
            if (restaurants.length === 0) {
                id = 1;
            } else {
                id = restaurants[restaurants.length - 1].ID + 1;
            }

            content =
                {
                    "ID": id,
                    "NOM": document.getElementById('nom').value,
                    "ADRESSE": document.getElementById('adresse').value,
                    "TELEPHONE": document.getElementById('tel').value,
                    "NBRESMAX": document.getElementById('capacite').value,
                    "NOTE": document.getElementById('note').value,
                    "IMAGE": document.getElementById('photo').value,
                    "LATITUDE": lat,
                    "LONGITUDE": lon
                };
            data.addRestaurant(content);

            addMarker(map, lat, lon, ICON_RESTAURANT);
            await mapInit.initPopupRestaurant(content);
        });
    });
}

export function addMarker(map, lat, lon, icon) {
    L.marker([lat, lon], {icon: icon}).addTo(map);
}

export function addPopup(map, lat, lon, icon, popupContent) {
    L.marker([lat, lon], {icon: icon}).addTo(map).bindPopup(popupContent);
}
