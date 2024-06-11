import * as data from './data.js';
import * as carte from './carte.js';
export async function displayMarkerVelib() {
    let stations = await data.getStationInfo()
    stations = stations.data.stations;
    stations.forEach(station => {
        carte.addMarker(carte.MAP, station.lat, station.lon, carte.ICON_VELO)
        initPopupVelib(station)
    })
}

export async function displayMarkerInsident() {
    // ...
}

export async function displayMarkerRestaurant() {
    // ...
}



async function initPopupVelib(station) {
    let stationStatus = await data.getStationStatus();
    stationStatus = stationStatus.data.stations;
    let status = stationStatus.find(s => s.station_id === station.station_id);
    let content = `
        <h2>${station.name}</h2>
        <ul>
            <li>Adresse: <span class="span_gras">${station.address}</span></li>
            <li>Nombre de vélos disponibles: <span class="span_gras">${status.num_bikes_available}</span></li>
            <li>Nombre de places disponibles: <span class="span_gras">${status.num_docks_available}</span></li>
        </ul>
          
    `;
    carte.addPopup(carte.MAP, station.lat, station.lon, carte.ICON_VELO, content);
}

export async function initPopupIncident() {
    // ...
}

export async function initPopupRestaurant() {
    // ...
}