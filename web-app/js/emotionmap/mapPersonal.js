google.load("earth", "1");

var defaultLatlng = [23.016548, 120.753479],
ge = null,
placemark = null,

flickrPointsArray,
flickrLineArray

$(document).ready(initialize);

function initialize() {

    /* -------------------------------------------------------------------------------------- */

    // Google earth construction
    google.earth.createInstance('map_convas', function(instance){

         // Get google earth instance and set navigator
         ge = instance;
         ge.getWindow().setVisibility(true);
         ge.getNavigationControl().setVisibility(ge.VISIBILITY_SHOW);

         // Place all flickr markers
         placeFlickrMarker();
         
         // Move the camera. (set the map center)
        changeLookAt(55.213, 320000);

    }, failureCB)

}

// Fail create google map instance
function failureCB(){}



 /* -------------------------------------------------------------------------------------- */




// Create line
function createLine(lat, lng, emotion, intensity, cityEmotionInfo, th){

         var latitudeUnitFlickr = 5000
         // Create the placemark.
         var lineStringPlacemark = ge.createPlacemark('');

         // Create the LineString; set it to extend down to the ground
         // and set the altitude mode.
         var lineString = ge.createLineString('');
         lineStringPlacemark.setGeometry(lineString);
         lineString.setExtrude(true);
         lineString.setAltitudeMode(ge.ALTITUDE_RELATIVE_TO_GROUND);

         // Add LineString points.
         var latitude = 0
         if(intensity != 0 && intensity != "0"){
            latitude = intensity * latitudeUnitFlickr
         }else{
            latitude = latitudeUnitFlickr
         }// end if
         var topRightX = lat + 0.0001,
         topRightY = lng + 0.0001;
         lineString.getCoordinates().pushLatLngAlt(lat, topRightY, latitude);
         lineString.getCoordinates().pushLatLngAlt(topRightX, topRightY, latitude);
         lineString.getCoordinates().pushLatLngAlt(topRightX, lng, latitude);
         lineString.getCoordinates().pushLatLngAlt(lat, lng, latitude);
         lineString.getCoordinates().pushLatLngAlt(lat, topRightY, latitude);

         // Create a style and set width and color of line.
         lineStringPlacemark.setStyleSelector(ge.createStyle(''));
         var lineStyle = lineStringPlacemark.getStyleSelector().getLineStyle();
         lineStyle.setWidth(20);

         var color
         if(emotion == emotionGroup[0].name){
             color = emotionGroup[0].color
         }else if(emotion == emotionGroup[1].name){
             color = emotionGroup[1].color
         }else if(emotion == emotionGroup[2].name){
             color = emotionGroup[2].color
         }else if(emotion == emotionGroup[3].name){
             color = emotionGroup[3].color
         }else if(emotion == emotionGroup[4].name){
             color = emotionGroup[4].color
         }else if(emotion == emotionGroup[5].name){
             color = emotionGroup[5].color
         }else if(emotion == emotionGroup[6].name){
             color = emotionGroup[6].color
         }else if(emotion == emotionGroup[7].name){
             color = emotionGroup[7].color
         }else if(emotion == emotionGroup[8].name){
             color = emotionGroup[8].color
         }else if(emotion == emotionGroup[9].name){
             color = emotionGroup[9].color
         }else if(emotion == emotionGroup[10].name){
             color = emotionGroup[10].color
         }else if(emotion == emotionGroup[11].name){
             color = emotionGroup[11].color
         }else{
             color = emotionGroup[0].color
         }// end
         lineStyle.getColor().set(color);  // aabbggrr format

         // Add the feature to Earth.
         ge.getFeatures().appendChild(lineStringPlacemark);
         flickrLineArray[th] = lineStringPlacemark


}


// Place marker
function placeMarker(lat, lng, emotion, intensity, content, name, publishTime, image1, image2, image3, th){

         // Icon source
         var url = window.location.toString();
         url = url.replace("publicfeed/emotionMap", "images/emotionMapIcon/");
         url = url.replace("#","")

         // Create the placemark.
         var pmk = ge.createPlacemark('');

         // Set name
         pmk.setName("");

         // Set description
         var description = "<div class='popWrapper'>";
         if(publishTime != null && typeof(publishTime) != "undefined" && publishTime!= 0 && publishTime!='0')
            description = description + "<div class='popupTime'><b>time: </b>" + publishTime + "</div>"
         if(content != null && typeof(content) != "undefined" && content != "")
            description = description + "<div id='popupContent' class='popupContent'><b>content: </b>" + content + "</div>"
         if(image1 != null && typeof(image1) != "undefined" && image1 != "")
             description = description + "<image alt='image' width=300 height=300 src='" + image1 + "' />"
         if(image2 != null && typeof(image2) != "undefined" && image2 != "")
             description = description + "<image alt='image' width=300 height=300 src='" + image2 + "' />"
         if(image3 != null && typeof(image3) != "undefined" && image3 != "")
             description = description + "<image alt='image' width=300 height=300 src='" + image3 + "' />"
         description = description + " </div>"

        pmk.setDescription(description)
         // Set the placemark's location.  
         var point = ge.createPoint('');
         point.setLatitude(lat);
         point.setLongitude(lng);
         pmk.setGeometry(point);

         // Create a style map.
         var styleMap = ge.createStyleMap('');

         // Create normal style for style map.
         var normalStyle = ge.createStyle(''),
         normalIcon = ge.createIcon('');

         // Create highlight style for style map.
         var highlightStyle = ge.createStyle(''),
         highlightIcon = ge.createIcon('');

         // Set icon
         if(emotion == emotionGroup[0].name){
             normalIcon.setHref(url + 'happy.png');
             highlightIcon.setHref(url + "happy.png");
         }else if(emotion == emotionGroup[1].name){
             normalIcon.setHref(url + 'sad.png');
             highlightIcon.setHref(url + "sad.png");
         }else if(emotion == emotionGroup[2].name){
             normalIcon.setHref(url + 'worried.png');
             highlightIcon.setHref(url + "worried.png");
         }else if(emotion == emotionGroup[3].name){
             normalIcon.setHref(url + 'angry.png');
             highlightIcon.setHref(url + "angry.png");
         }else if(emotion == emotionGroup[4].name){
             normalIcon.setHref(url + 'disappoint.png');
             highlightIcon.setHref(url + "disappoint.png");
         }else if(emotion == emotionGroup[5].name){
             normalIcon.setHref(url + 'peace.png');
             highlightIcon.setHref(url + "peace.png");
         }else if(emotion == emotionGroup[6].name){
             normalIcon.setHref(url + 'nervous.png');
             highlightIcon.setHref(url + "nervous.png");
         }else if(emotion == emotionGroup[7].name){
             normalIcon.setHref(url + 'afraid.png');
             highlightIcon.setHref(url + "afraid.png");
         }else if(emotion == emotionGroup[8].name){
             normalIcon.setHref(url + 'touched.png');
             highlightIcon.setHref(url + "touched.png");
         }else if(emotion == emotionGroup[9].name){
             normalIcon.setHref(url + 'hope.png');
             highlightIcon.setHref(url + "hope.png");
         }else if(emotion == emotionGroup[10].name){
             normalIcon.setHref(url + 'love.png');
             highlightIcon.setHref(url + "love.png");
         }else if(emotion == emotionGroup[11].name){
             normalIcon.setHref(url + 'hate.png');
             highlightIcon.setHref(url + "hate.png");

         }else{
             normalIcon.setHref(url + 'happy.png');
             highlightIcon.setHref(url + "happy.png");
         }// end
         
         normalStyle.getIconStyle().setIcon(normalIcon);         
         highlightStyle.getIconStyle().setIcon(highlightIcon);
         highlightStyle.getIconStyle().setScale(3.0); // make same icon bigger while mouse over

         styleMap.setNormalStyle(normalStyle);
         styleMap.setHighlightStyle(highlightStyle);

         // Apply stylemap to a placemark.
         pmk.setStyleSelector(styleMap);

         // Add the placemark to Earth.
         ge.getFeatures().appendChild(pmk);
         flickrPointsArray[th] = pmk
         createLine(lat, lng, emotion, intensity, "", th)


         
}



 /* -------------------------------------------------------------------------------------- */


// Place all flickr markers on map
function placeFlickrMarker(){

    if(flickrPointsInfo.length > 0){
        flickrPointsArray = new Array(flickrPointsInfo.length)
        flickrLineArray = new Array(flickrPointsInfo.length)
        for(var i=0; i<flickrPointsInfo.length; i++){

            placeMarker(
                flickrPointsInfo[i].lat, // latitude
                flickrPointsInfo[i].lng, // longitude
                flickrPointsInfo[i].emotion, // emotion
                flickrPointsInfo[i].intensity, // intensity
                flickrPointsInfo[i].content, // html content
                flickrPointsInfo[i].title, // title
                flickrPointsInfo[i].publishTime, // publish time
                flickrPointsInfo[i].imagePath, // image1
                null, // image2 - for government point
                null, // image3 - for government point
                i // the Nth record
            )

        }// end for
    }

}


// Change the camera 
function changeLookAt(downtilt, range){

    // Move the camera. (set the map center)
    var la = ge.createLookAt('');
    la.set(
        defaultLatlng[0], // latitude
        defaultLatlng[1], // longitude
        1800, // altitude
        ge.ALTITUDE_RELATIVE_TO_GROUND,
        -6.541, // heading
        downtilt, // straight-downtilt 視角
        range // range
    );
    ge.getView().setAbstractView(la);

}

