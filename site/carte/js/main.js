import * as carte from './carte.js';
import * as map from './map.js';
import * as nav from './nav.js';

window.onload = function() {
    carte.initMap();
    nav.initNav();
    map.displayMarkerVelib();
    map.displayMarkerIncident();
}