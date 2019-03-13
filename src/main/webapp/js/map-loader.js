/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/** Create the Google Map and center it around "Hogwarts" aka the Googleplex */
function createMap() {
    //Create the Google Map.
    const map = new google.maps.Map(document.getElementById("map"), {
        center: {
            lat: 37.422, 
            lng: -122.084
        },
        zoom: 16
    });
    //Create a marker for "Hogwarts" aka the Googleplex.
    createMapMarker(map, 37.422, -122.084, 
        'Hogwarts', 'This is Hogwarts, School of Wizardry');
}

/**
 * Creates a Marker on the Google Map with a listener that 
 * posts a description when clicked.
 * @param {google.maps.Map} map 
 * @param {float} lat (latitude)
 * @param {float} lng (longitude)
 * @param {String} title 
 * @param {String} description 
 */
function createMapMarker (map, lat, lng, title, description) {
    const marker = new google.maps.Marker({
        position: {
            lat: lat,
            lng: lng
        },
        map: map,
        title: title
    });
    var infoWindow = new google.maps.InfoWindow({
        content: description
    });
    marker.addListener('click', () => {
        infoWindow.open(map, marker);
    });
}