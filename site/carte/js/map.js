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

export async function displayMarkerIncident() {
    let incidents = await data.getIncidents();
    incidents = incidents.incidents;
    incidents.forEach(incident => {
        let long = incident.location.polyline.split(' ')[1];
        let lat = incident.location.polyline.split(' ')[0];
        carte.addMarker(carte.MAP, lat, long, carte.ICON_INCIDENT);
        initPopupIncident(incident, lat, long);
    })
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

export async function initPopupIncident(incident, lat, long) {
    let dateStart = incident.starttime.split('T')[0].split('-')[2] + '/' + incident.starttime.split('T')[0].split('-')[1] + '/' + incident.starttime.split('T')[0].split('-')[0];
    let dateEnd = incident.endtime.split('T')[0].split('-')[2] + '/' + incident.endtime.split('T')[0].split('-')[1] + '/' + incident.endtime.split('T')[0].split('-')[0];
    let temps = dateStart + ' - ' + dateEnd;

    let content = `
        <h2>${incident.type}</h2>
        <ul>
            <li>Description: <span class="span_gras">${incident.short_description}</span></li>
            <li>Adresse: <span class="span_gras">${incident.location.street}</span></li>
            <li>Temps: <span class="span_gras">${temps}</span></li>
        </ul>
    `;
    carte.addPopup(carte.MAP, lat, long, carte.ICON_INCIDENT, content);
}

export async function initPopupRestaurant() {
    // ...
}