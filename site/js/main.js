import * as carte from './carte.js';
import * as mapDisplay from './mapDisplay.js';
import * as nav from './nav.js';

window.onload = async function () {
    carte.initMap();
    nav.initNav();
    await mapDisplay.displayMarkerVelib();
    await mapDisplay.displayMarkerIncident();
    await mapDisplay.displayMarkerRestaurant();
    await mapDisplay.displayMarkerSup();
}