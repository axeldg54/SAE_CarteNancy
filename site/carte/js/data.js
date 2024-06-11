const ENTREE = 'https://transport.data.gouv.fr/gbfs/nancy/gbfs.json';
const SYSTEM = 'https://transport.data.gouv.fr/gbfs/nancy/system_information.json';
const STATION_INFO = 'https://transport.data.gouv.fr/gbfs/nancy/station_information.json';
const STATION_STATUS = 'https://transport.data.gouv.fr/gbfs/nancy/station_status.json';
const CLIMAT = 'https://www.infoclimat.fr/public-api/gfs/json?_ll=48.978,6.242&_auth=U0lVQgR6XH5TfgYxUiQHLlc%2FVWALfQUiVChSMQ5rAH1UPl49UzhSL1QkWz9SfAs9AjVQMgAgCS5QMwRkWDVeNFMzVS4EeFwjUz0Ge1J9BzJXbVU2CyoFOFQ3UisOYgBgVDJeJFMyUjBUPVsmUn0LPgI2UDEANwkxUDoEZVg2XjxTMlUuBHhcOFM6BmJSagcyV2VVZws8BT1UY1IxDjUAYlQ2XiRTNVIwVDNbOFJgCz8COFA4ACAJLlBKBBBYKF59U3JVZAQhXCNTaQY6UjY%3D&_c=d066154513fa88e232bdf89a0392991f';

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

