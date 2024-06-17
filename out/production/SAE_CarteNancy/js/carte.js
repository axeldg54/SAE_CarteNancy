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
}

export function addMarker(map, lat, lon, icon) {
    L.marker([lat, lon], {icon: icon}).addTo(map);
}

export function addPopup(map, lat, lon, icon, popupContent) {
    L.marker([lat, lon], {icon: icon}).addTo(map).bindPopup(popupContent);
}
