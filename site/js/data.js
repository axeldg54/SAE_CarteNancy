const ENTREE = 'https://transport.data.gouv.fr/gbfs/nancy/gbfs.json';
const SYSTEM = 'https://transport.data.gouv.fr/gbfs/nancy/system_information.json';
const STATION_INFO = 'https://transport.data.gouv.fr/gbfs/nancy/station_information.json';
const STATION_STATUS = 'https://transport.data.gouv.fr/gbfs/nancy/station_status.json';
const CLIMAT = 'https://www.infoclimat.fr/public-api/gfs/json?_ll=48.978,6.242&_auth=U0lVQgR6XH5TfgYxUiQHLlc%2FVWALfQUiVChSMQ5rAH1UPl49UzhSL1QkWz9SfAs9AjVQMgAgCS5QMwRkWDVeNFMzVS4EeFwjUz0Ge1J9BzJXbVU2CyoFOFQ3UisOYgBgVDJeJFMyUjBUPVsmUn0LPgI2UDEANwkxUDoEZVg2XjxTMlUuBHhcOFM6BmJSagcyV2VVZws8BT1UY1IxDjUAYlQ2XiRTNVIwVDNbOFJgCz8COFA4ACAJLlBKBBBYKF59U3JVZAQhXCNTaQY6UjY%3D&_c=d066154513fa88e232bdf89a0392991f';
const INCIDENT = 'https://carto.g-ny.org/data/cifs/cifs_waze_v2.json';
const INCIDENT_LOCAL = 'http://localhost:8000/incidents';
const RESTAURANT = 'http://localhost:8000/restaurants';
const RESERVATION = 'http://localhost:8000/remaining?id=#&date=#';
const POST_RESERVATION = 'http://localhost:8000/reservations';
const SUP = 'http://localhost:8000/sup';

export function getSystem() {
    return new Promise((resolve, reject) => {
        fetch(SYSTEM)
            .then(response => response.json())
            .then(data => resolve(data))
            .catch(error => reject(error));
    });
}

export function getStationInfo() {
    return new Promise((resolve, reject) => {
        fetch(STATION_INFO)
            .then(response => response.json())
            .then(data => resolve(data))
            .catch(error => reject(error));
    });
}

export function getStationStatus() {
    return new Promise((resolve, reject) => {
        fetch(STATION_STATUS)
            .then(response => response.json())
            .then(data => resolve(data))
            .catch(error => reject(error));
    });
}

export function getVelibData() {
    return new Promise((resolve, reject) => {
        fetch(ENTREE)
            .then(response => response.json())
            .then(data => resolve(data))
            .catch(error => reject(error));
    });
}

export function getClimat() {
    return new Promise((resolve, reject) => {
        fetch(CLIMAT)
            .then(response => response.json())
            .then(data => resolve(data))
            .catch(error => reject(error));
    });
}

export function getIncidents() {
    return new Promise((resolve, reject) => {
        fetch(INCIDENT_LOCAL)
            .then(response => response.json())
            .then(data => resolve(data))
            .catch(error => {
                console.log("Erreur : Attention a bien lancer le serveur local, le 'main' dans le fichier SAE_CarteNancy/HTTPServer/Main.java");
                reject(error)
            })
    });
}

export function getRestaurants() {
    return new Promise((resolve, reject) => {
        fetch(RESTAURANT)
            .then(response => response.json())
            .then(data => resolve(data))
            .catch(error => reject(error));
    });
}

export function getReservation(id, date) {
    return new Promise((resolve, reject) => {
        fetch(RESERVATION.replace('#', id).replace('#', date))
            .then(response => response.json())
            .then(data => resolve(data))
            .catch(error => reject(error));
    });
}

export async function postReservation(id, date, nom, prenom, tel, nbPersonnes) {
    let data = {
        "idRestaurant": id,
        "dateRes": date,
        "nom": nom,
        "prenom": prenom,
        "numTel": tel,
        "nbPersonnes": nbPersonnes
    };

    await fetch(POST_RESERVATION, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'mode': 'cors'
        },
        body: data
    });
}

export async function addRestaurant(content) {
    await fetch(RESTAURANT, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'mode': 'cors'
        },
        body: content
    });
}

export function getSup() {
    return new Promise((resolve, reject) => {
        fetch(SUP)
            .then(response => response.json())
            .then(data => resolve(data))
            .catch(error => reject(error));
    });
}