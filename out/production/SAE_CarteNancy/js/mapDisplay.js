import * as data from './data.js';
import * as carte from './carte.js';
import * as mapInit from './mapInit.js';

export async function displayMarkerVelib() {
    // On récupère les données des stations
    let stations = await data.getStationInfo()
    stations = stations.data.stations;

    // On ajoute les stations à la carte
    stations.forEach(station => {
        carte.addMarker(carte.MAP, station.lat, station.lon, carte.ICON_VELO)
        mapInit.initPopupVelib(station)
    })
}

export async function displayMarkerIncident() {
    // On récupère les données des incidents
    let incidents = await data.getIncidents();
    incidents = incidents.incidents;

    // On ajoute les incidents à la carte
    incidents.forEach(incident => {
        let long = incident.location.polyline.split(' ')[1];
        let lat = incident.location.polyline.split(' ')[0];
        carte.addMarker(carte.MAP, lat, long, carte.ICON_INCIDENT);
        mapInit.initPopupIncident(incident, lat, long);
    })
}

export async function displayMarkerRestaurant() {
    return new Promise(async (resolve) => {
        // On récupère les données des restaurants
        let restaurants = await data.getRestaurants();

        // On ajoute les restaurants à la carte
        restaurants.forEach(restaurant => {
            carte.addMarker(carte.MAP, restaurant.LATITUDE, restaurant.LONGITUDE, carte.ICON_RESTAURANT);
            mapInit.initPopupRestaurant(restaurant);
        })
        resolve();
    });
}