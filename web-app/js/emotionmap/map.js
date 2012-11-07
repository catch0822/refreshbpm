google.load("earth", "1");

var defaultLatlng = [23.016548, 120.753479],
ge = null,
placemark = null,

flickrPointsArray,
flickrLineArray,
govPointsArray,
govPointsLineArray,
happinessCityArray,
happinessCityLineArray = [
    [],[],[],[],[],[],
    [],[],[],[],[],[],
    [],[],[],[],[],[]
],

isClickedFlickr = 1,
isClickedHappy = 0,
isClickedScenic = 0


$(document).ready(initialize);

function initialize() {

    // Button effect
    $('#menu-jquery li a').hover(

        function() {

            $(this).css('padding', '5px 15px')
            .stop()
            .animate({
                'paddingLeft'	: '25px',
                'paddingRight'	: '25px',
                'backgroundColor':'rgba(0,0,0,0.5)'
            },
            'fast');
        },

        function() {

            $(this).css('padding', '5px 25px')
            .stop()
            .animate({
                'paddingLeft'	: '15px',
                'paddingRight'		: '15px',
                'backgroundColor' :'rgba(0,0,0,0.2)'
            },
            'fast');

        }).mousedown(function() {

        $(this).stop().animate({
            'backgroundColor': 'rgba(0,0,0,0.1)'
        }, 'fast');

    }).mouseup(function() {

        $(this).stop().animate({
            'backgroundColor': 'rgba(0,0,0,0.5)'
        }, 'fast');
    });


    $("#colorBar li a").click(function(){
        var theEmotion = this.id
        theEmotion = parseInt(theEmotion.replace("emotionBar",""))
        hideEmotion(theEmotion)
    })


    // Show all flickr markers and hide others
    $("#footprint").click(function(){

        // Set isClick
        isClickedFlickr = 1,
        isClickedHappy = 0,
        isClickedScenic = 0

        // Move the camera. (set the map center)
        changeLookAt(45.213, 350000);

        // Show flickr markers
        for(var j=0;j<flickrPointsArray.length;j++){
            flickrPointsArray[j].setVisibility(true);
            flickrLineArray[j].setVisibility(true);
        }// end for

        // Hide happiness city markers
        for(var k=0;k<happinessCityArray.length;k++){
            happinessCityArray[k].setVisibility(false);
            for(var l=0; l<happinessCityLineArray[k].length; l++)
                happinessCityLineArray[k][l].setVisibility(false);
        }// end for

        // Hide government points markers
        for(var i = 0; i<govPointsArray.length; i++){
            govPointsArray[i].setVisibility(false);
            govPointsLineArray[i].setVisibility(false);
        }// end for
        
    })

    // Show all happiness city markers and hide others
    $("#happinessCity").click(function(){

        // Set isClick
        isClickedFlickr = 0,
        isClickedHappy = 1,
        isClickedScenic = 0

        // Move the camera. (set the map center)
        changeLookAt(45.213, 380000);

        // Hide flickr markers
        for(var j=0;j<flickrPointsArray.length;j++){
            flickrPointsArray[j].setVisibility(false);
            flickrLineArray[j].setVisibility(false);
        }// end for

        // Show happiness city markers
        for(var k=0;k<happinessCityArray.length;k++){
            happinessCityArray[k].setVisibility(true);
            for(var l=0; l<happinessCityLineArray[k].length; l++)
                happinessCityLineArray[k][l].setVisibility(true);
        }//end for

        // Hide government points markers
        for(var i = 0; i<govPointsArray.length; i++){
            govPointsArray[i].setVisibility(false);
            govPointsLineArray[i].setVisibility(false);
        }// end for

    })


    // Show all government points markers and hide others
    $("#senicPointsEvaluation").click(function(){

        // Set isClick
        isClickedFlickr = 0,
        isClickedHappy = 0,
        isClickedScenic = 1


        // Move the camera. (set the map center)
        changeLookAt2(45.213, 15000);

        // Hide flickr markers
        for(var j=0;j<flickrPointsArray.length;j++){
            flickrPointsArray[j].setVisibility(false);
            flickrLineArray[j].setVisibility(false);
        }// end for

        // Hide happiness city markers
        for(var k=0;k<happinessCityArray.length;k++){
            happinessCityArray[k].setVisibility(false);
            for(var l=0; l<happinessCityLineArray[k].length; l++)
               happinessCityLineArray[k][l].setVisibility(false);
        }// end for

        // Show government points markers
        for(var i=0; i<govPointsArray.length; i++){
            govPointsArray[i].setVisibility(true);
            govPointsLineArray[i].setVisibility(true);
        }// end for

    })    



    /* -------------------------------------------------------------------------------------- */



    // Google earth construction
    google.earth.createInstance('map_convas', function(instance){

         // Get google earth instance and set navigator
         ge = instance;
         ge.getWindow().setVisibility(true);
         ge.getNavigationControl().setVisibility(ge.VISIBILITY_SHOW);

         // Place all flickr markers
         placeFlickrMarker();
         
         // Place all government points markers
         placeGovMarkers();
         
         // Place all happiness city markers
         placeHappinessCityMarkers();
         
         // Move the camera. (set the map center)
        changeLookAt(55.213, 320000);

    }, failureCB)

}

// Fail create google map instance
function failureCB(){}



 /* -------------------------------------------------------------------------------------- */




// Create line
function createLine(lat, lng, emotion, intensity, cityEmotionInfo, th){

         var latitudeUnitScenicPoint = 100,
         latitudeUnitFlickr = 5000,
         latitudeUnitHappy = 500;
         if(emotion == "happiness city"){

                var cityInfo = cityEmotionInfo.split(",")
                for(var i=0; i<cityInfo.length-1; i++){

                     // Create the placemark.
                     var lineStringPlacemark = ge.createPlacemark('');

                     // Create the LineString; set it to extend down to the ground and set the altitude mode.
                     var lineString = ge.createLineString('');
                     lineStringPlacemark.setGeometry(lineString);
                     lineString.setExtrude(true);
                     lineString.setAltitudeMode(ge.ALTITUDE_RELATIVE_TO_GROUND);

                     // Set latitude
                     var latitude = 0
                     var theIntensity = cityInfo[i]
                     if(theIntensity != 0 && theIntensity != "0")
                        latitude = theIntensity * latitudeUnitHappy
                     else
                        latitude = 0

                     // Add LineString points.
                     var topRightX=0, topRightY=0, topLeftY=0
                     if(i!=0){

                        topLeftY =  lng + (i)*0.001,
                        topRightX = lat + 0.001,
                        topRightY = lng + ((i+1)*0.001)
                        lineString.getCoordinates().pushLatLngAlt(lat, topRightY, latitude);
                        lineString.getCoordinates().pushLatLngAlt(topRightX, topRightY, latitude);
                        lineString.getCoordinates().pushLatLngAlt(topRightX, topLeftY, latitude);
                        lineString.getCoordinates().pushLatLngAlt(lat, topLeftY, latitude);
                        lineString.getCoordinates().pushLatLngAlt(lat, topRightY, latitude);

                     }else{

                        topRightX = lat + 0.001,
                        topRightY = lng + 0.001
                        lineString.getCoordinates().pushLatLngAlt(lat, topRightY, latitude);
                        lineString.getCoordinates().pushLatLngAlt(topRightX, topRightY, latitude);
                        lineString.getCoordinates().pushLatLngAlt(topRightX, lng, latitude);
                        lineString.getCoordinates().pushLatLngAlt(lat, lng, latitude);
                        lineString.getCoordinates().pushLatLngAlt(lat, topRightY, latitude);

                     }// end for


                     // Create a style and set width and color of line.
                     lineStringPlacemark.setStyleSelector(ge.createStyle(''));
                     var lineStyle = lineStringPlacemark.getStyleSelector().getLineStyle();
                     lineStyle.setColorMode(ge.COLOR_NORMAL);
                     lineStyle.setWidth(25);

                     var color
                     if(i == 0){
                         color = emotionGroup[0].color
                     }else if(i == 1){
                         color = emotionGroup[1].color
                     }else if(i == 2){
                         color = emotionGroup[2].color
                     }else if(i == 3){
                         color = emotionGroup[3].color
                     }else if(i == 4){
                         color = emotionGroup[4].color
                     }else if(i == 5){
                         color = emotionGroup[5].color
                     }else if(i == 6){
                         color = emotionGroup[6].color
                     }else if(i == 7){
                         color = emotionGroup[7].color
                     }else if(i == 8){
                         color = emotionGroup[8].color
                     }else if(i == 9){
                         color = emotionGroup[9].color
                     }else if(i == 10){
                         color = emotionGroup[10].color
                     }else if(emotion == 11){
                         color = emotionGroup[11].color
                     }else{
                         color = emotionGroup[0].color
                     }// end
                     lineStyle.getColor().set(color);  // aabbggrr format
                     //lineStringPlacemark.setVisibility(false)

                     // Add the feature to Earth.
                     ge.getFeatures().appendChild(lineStringPlacemark);
                     happinessCityLineArray[th][i] = lineStringPlacemark
                     happinessCityLineArray[th][i].setVisibility(false)

                }// end for

         }else if(emotion == "scenic point"){

                 var creditInfo = intensity.split(",")

                 // Create the placemark.
                 var lineStringPlacemark = ge.createPlacemark('');
                 var lineStringPlacemark2 = ge.createPlacemark('');

                 // Create the LineString; set it to extend down to the ground and set the altitude mode.
                 var lineString = ge.createLineString('');
                 var lineString2 = ge.createLineString('');
                 lineStringPlacemark.setGeometry(lineString);
                 lineStringPlacemark2.setGeometry(lineString2);
                 lineString.setExtrude(true);
                 lineString2.setExtrude(true);
                 lineString.setAltitudeMode(ge.ALTITUDE_RELATIVE_TO_GROUND);
                 lineString2.setAltitudeMode(ge.ALTITUDE_RELATIVE_TO_GROUND);

                 // Add LineString points.
                 var latitude = 0;
                 if(creditInfo[0] != 0 && creditInfo[0] != "0"){
                    latitude = creditInfo[0] * latitudeUnitScenicPoint
                 }// end if
                 var latitude2 = 0;
                 if(creditInfo[1] != 0 && creditInfo[1] != "0"){
                    latitude2 = creditInfo[1] * latitudeUnitScenicPoint
                 }// end if


                 // Add LineString points.
                var distance = 0.000001
                lineString2.getCoordinates().pushLatLngAlt(lat, lng +distance, latitude2);
                lineString2.getCoordinates().pushLatLngAlt(lat + distance, lng + distance, latitude2);
                lineString2.getCoordinates().pushLatLngAlt(lat + distance, lng + distance*2, latitude2);
                lineString2.getCoordinates().pushLatLngAlt(lat, lng + distance*2, latitude2);
                lineString2.getCoordinates().pushLatLngAlt(lat, lng + distance, latitude2);
                lat = lat
                lng = lng + 0.0008
                lineString.getCoordinates().pushLatLngAlt(lat, lng + distance, latitude);
                lineString.getCoordinates().pushLatLngAlt(lat + distance, lng + distance, latitude);
                lineString.getCoordinates().pushLatLngAlt(lat + distance, lng + distance*2, latitude);
                lineString.getCoordinates().pushLatLngAlt(lat, lng + distance*2, latitude);
                lineString.getCoordinates().pushLatLngAlt(lat, lng + distance, latitude);

                 // Create a style and set width and color of line.
                 lineStringPlacemark.setStyleSelector(ge.createStyle(''));
                 lineStringPlacemark2.setStyleSelector(ge.createStyle(''));
                 var lineStyle = lineStringPlacemark.getStyleSelector().getLineStyle();
                 var lineStyle2 = lineStringPlacemark2.getStyleSelector().getLineStyle();
                 lineStyle.setWidth(20);
                 lineStyle2.setWidth(20);
                 lineStyle.getColor().set(emotionGroup[0].color);
                 lineStyle2.getColor().set(emotionGroup[1].color);


                 // Add the feature to Earth.
                 ge.getFeatures().appendChild(lineStringPlacemark);
                 ge.getFeatures().appendChild(lineStringPlacemark2);
                 if(th == 0){
                    govPointsLineArray[0] = lineStringPlacemark;
                    govPointsLineArray[0].setVisibility(false);
                    govPointsLineArray[1] = lineStringPlacemark2;
                    govPointsLineArray[1].setVisibility(false);
                 }else{
                    govPointsLineArray[th * 2] = lineStringPlacemark;
                    govPointsLineArray[th * 2].setVisibility(false);
                    govPointsLineArray[th * 2 + 1] = lineStringPlacemark2;
                    govPointsLineArray[th * 2 + 1].setVisibility(false);
                 }// end if


         }else{

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
                 
         }// end if
         
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
         if(emotion == "happiness city"){
            pmk.setName(name);
         }else if(emotion == "scenic point"){
            pmk.setName(name);
         }else{
            pmk.setName("");
         }// end if

         // Set description
         var description        
         if(emotion == "happiness city"){
             
            description = "<div class='popWrapper2'>";
            theEmotion = content.split(','),
            total = parseInt(theEmotion[0]) + parseInt(theEmotion[1]) +
                parseInt(theEmotion[2]) + parseInt(theEmotion[3]) +
                parseInt(theEmotion[4]) + parseInt(theEmotion[5]) +
                parseInt(theEmotion[6]) + parseInt(theEmotion[7]) +
                parseInt(theEmotion[8]) + parseInt(theEmotion[9]) +
                parseInt(theEmotion[10]) + parseInt(theEmotion[11]);
            if(total == 0)
                total = 1
            description = description +
                "<div class='happy'><b>Happy: </b><p class='happyPercentage'>"        + theEmotion[0]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[0])/total * 100) + "% </p></div>" +
                "<div class='sad'><b>Sad: </b><p class='happyPercentage'>"          + theEmotion[1]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[1])/total * 100) + "% </p></div>" +
                "<div class='worried'><b>Worried: </b><p class='happyPercentage'>"      + theEmotion[2]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[2])/total * 100) + "% </p></div>" +
                "<div class='angry'><b>Angry: </b><p class='happyPercentage'>"        + theEmotion[3]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[3])/total * 100) + "% </p></div>" +
                "<div class='disappoint'><b>Disappoint: </b><p class='happyPercentage'>"   + theEmotion[4]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[4])/total * 100) + "% </p></div>" +
                "<div class='peace'><b>Peace: </b><p class='happyPercentage'>"        + theEmotion[5]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[5])/total * 100) + "% </p></div>" +
                "<div class='nervous'><b>Nervous: </b><p class='happyPercentage'>"      + theEmotion[6]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[6])/total * 100) + "% </p></div>" +
                "<div class='afraid'><b>Afraid: </b><p class='happyPercentage'>"       + theEmotion[7]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[7])/total * 100) + "% </p></div>" +
                "<div class='touched'><b>Touched: </b><p class='happyPercentage'>"      + theEmotion[8]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[8])/total * 100) + "% </p></div>" +
                "<div class='hope'><b>Hope: </b><p class='happyPercentage'>"         + theEmotion[9]  + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[9])/total * 100) + "% </p></div>" +
                "<div class='love'><b>Love: </b><p class='happyPercentage'>"         + theEmotion[10] + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[10])/total * 100) + "% </p></div>" +
                "<div class='hate'><b>Hate: </b><p class='happyPercentage'>"         + theEmotion[11] + " ----- &nbsp;&nbsp" + Math.floor(parseInt(theEmotion[11])/total * 100) + "% </p></div>"


         }else if(emotion == "scenic point"){
             
             description = "<div class='popWrapper3'>";

              var creditInfo = intensity.split(","),
              positive = parseInt(creditInfo[0]),
              negative = parseInt(creditInfo[1]),
              credit = positive - negative;
              description = description + "<div class='positive'>" + "<img alt='good' src='" + url + 'good.png' + "' />" + positive + "</div>"
              description = description + "<div class='negative'>" + "<img alt='bad' src='" + url + 'bad.png' + "' />" + negative + "</div>"
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

         }else{

             description = "<div class='popWrapper'>";
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
                         
        }// end if
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

         }else if(emotion == "happiness city"){

            if(name.indexOf("taipei")!=-1){
                normalIcon.setHref(url + 'newTaipei.png');
                highlightIcon.setHref(url + "newTaipei.png");
            }else if(name.indexOf("kaohsiung")!=-1){
                normalIcon.setHref(url + 'Kaohsiung.png');
                highlightIcon.setHref(url + "Kaohsiung.png");
            }else if(name.indexOf("yilan")!=-1){
                normalIcon.setHref(url + 'Yilan.png');
                highlightIcon.setHref(url + "Yilan.png");
            }else if(name.indexOf("taoyuan")!=-1){
                normalIcon.setHref(url + 'Taoyuan.png');
                highlightIcon.setHref(url + "Taoyuan.png");
            }else if(name.indexOf("hsinchu")!=-1){
                normalIcon.setHref(url + 'Hsinchu.png');
                highlightIcon.setHref(url + "Hsinchu.png");
            }else if(name.indexOf("miaoli")!=-1){
                normalIcon.setHref(url + 'Miaoli.png');
                highlightIcon.setHref(url + "Miaoli.png");
            }else if(name.indexOf("taichung")!=-1){
                normalIcon.setHref(url + 'Taichung.png');
                highlightIcon.setHref(url + "Taichung.png");
            }else if(name.indexOf("changhua")!=-1){
                normalIcon.setHref(url + 'Changhua.png');
                highlightIcon.setHref(url + "Changhua.png");
            }else if(name.indexOf("nantou")!=-1){
                normalIcon.setHref(url + 'Nantou.png');
                highlightIcon.setHref(url + "Nantou.png");
            }else if(name.indexOf("yunlin")!=-1){
                normalIcon.setHref(url + 'Yunlin.png');
                highlightIcon.setHref(url + "Yunlin.png");
            }else if(name.indexOf("chiayi")!=-1){
                normalIcon.setHref(url + 'Chiayi.png');
                highlightIcon.setHref(url + "Chiayi.png");
            }else if(name.indexOf("tainan")!=-1){
                normalIcon.setHref(url + 'Tainan.png');
                highlightIcon.setHref(url + "Tainan.png");
            }else if(name.indexOf("pingtung")!=-1){
                normalIcon.setHref(url + 'Pingtung.png');
                highlightIcon.setHref(url + "Pingtung.png");
            }else if(name.indexOf("hualien")!=-1){
                normalIcon.setHref(url + 'Hualien.png');
                highlightIcon.setHref(url + "Hualien.png");
            }else if(name.indexOf("keelung")!=-1){
                normalIcon.setHref(url + 'Keelung.png');
                highlightIcon.setHref(url + "Keelung.png");
            }else if(name.indexOf("taitung")!=-1){
                normalIcon.setHref(url + 'Taitung.png');
                highlightIcon.setHref(url + "Taitung.png");
            }else{}

         }else if(emotion!=null){

              if(intensity.indexOf(",")!=-1){
                  var creditInfo = intensity.split(","),
                  credit = parseInt(creditInfo[0]) - parseInt(creditInfo[1])
                  if(credit>0){
                    normalIcon.setHref(url + "good.png");
                    highlightIcon.setHref(url + "good.png");
                  }else if(credit<0){
                    normalIcon.setHref(url + "bad.png");
                    highlightIcon.setHref(url + "bad.png");
                  }else{
                    normalIcon.setHref(url + "normal.png");
                    highlightIcon.setHref(url + "normal.png");                      
                  }// end if
              }else{
                    normalIcon.setHref(url + 'happy.png');
                    highlightIcon.setHref(url + "happy.png");
              }// end if

         }else{
             normalIcon.setHref(url + 'happy.png');
             highlightIcon.setHref(url + "happy.png");
         }// end
         
         normalStyle.getIconStyle().setIcon(normalIcon);         
         highlightStyle.getIconStyle().setIcon(highlightIcon);

         if(emotion == "happiness city"){
            normalStyle.getIconStyle().setScale(1.2);
            highlightStyle.getIconStyle().setScale(5.0); // make same icon bigger while mouse over
         }else if(emotion == "scenic point"){
            highlightStyle.getIconStyle().setScale(3.0); // make same icon bigger while mouse over
         }else{
            highlightStyle.getIconStyle().setScale(3.0); // make same icon bigger while mouse over
         }// end if
         styleMap.setNormalStyle(normalStyle);
         styleMap.setHighlightStyle(highlightStyle);

         // Apply stylemap to a placemark.
         pmk.setStyleSelector(styleMap);

         // Add the placemark to Earth.
         ge.getFeatures().appendChild(pmk);
         
         if(emotion == "scenic point"){

            govPointsArray[th] = pmk;
            govPointsArray[th].setVisibility(false);
            createLine(lat, lng, emotion, intensity, content, th)

         }else if(emotion == "happiness city"){

            happinessCityArray[th] = pmk
            happinessCityArray[th].setVisibility(false);
            createLine(lat, lng, emotion, intensity, content, th)

        }else if(emotion != "scenic point" && emotion != "happiness city"){

            flickrPointsArray[th] = pmk
            createLine(lat, lng, emotion, intensity, "", th)

         }//end if
         
}



 /* -------------------------------------------------------------------------------------- */


// Place all flickr markers on map
function placeFlickrMarker(){
    
    flickrPointsArray = new Array(flickrPointsInfo.length)
    flickrLineArray = new Array(govPointsInfo.length)
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


// Place all happiness city markers on map
function placeHappinessCityMarkers(){

    happinessCityArray = new Array(happinessCities.length)
    for(var i=0; i<happinessCities.length; i++){

        var content = happinessCities[i].happy + ',' +
            happinessCities[i].sad + ',' +
            happinessCities[i].worried + ',' +
            happinessCities[i].angry + ',' +
            happinessCities[i].disappoint + ',' +
            happinessCities[i].peace + ',' +
            happinessCities[i].nervous + ',' +
            happinessCities[i].afraid + ',' +
            happinessCities[i].touched + ',' +
            happinessCities[i].hope + ',' +
            happinessCities[i].love + ',' +
            happinessCities[i].hate + ',' +
            happinessCities[i].credit

            placeMarker(
                happinessCities[i].lat, // latitude
                happinessCities[i].lng, // longitude
                "happiness city", // emotion - happiness city without emotion
                "no intensity", // intensity - happiness city without intensity
                content, // html content
                happinessCities[i].name, // title
                0, // publish time
                null, // image1
                null, // image2 - for government point
                null, // image3 - for government point
                i // the Nth record
            )

    }// end for

}




// Place all goverment markers on map
function placeGovMarkers(){

    govPointsArray = new Array(govPointsInfo.length)
    govPointsLineArray = new Array(govPointsInfo.length * 2)
    for(var i=0; i<govPointsInfo.length; i++){

        var positiveAndNegative = govPointsInfo[i].positive + "," + govPointsInfo[i].negative
        placeMarker(
            govPointsInfo[i].lat, // latitude
            govPointsInfo[i].lng, // longitude
            "scenic point", // emotion - government city without emotion
            positiveAndNegative, // government city without intensity
            govPointsInfo[i].description, // html content
            govPointsInfo[i].name, // title
            0,
            govPointsInfo[i].imageUrl1, // image1
            govPointsInfo[i].imageUrl2, // image2
            govPointsInfo[i].imageUrl3, // image3
            i // the Nth record
        )
        

    }// end for

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


// Change the camera
function changeLookAt2(downtilt, range){

    // Move the camera. (set the map center)
    var la = ge.createLookAt('');
    la.set(
        25.008462, // latitude
        121.540959, // longitude
        1800, // altitude
        ge.ALTITUDE_RELATIVE_TO_GROUND,
        -6.541, // heading
        downtilt, // straight-downtilt 視角
        range // range
    );
    ge.getView().setAbstractView(la);

}


function hideEmotion(theEmotion){

    if(isClickedFlickr == 1){
        for(var j=0;j<flickrPointsArray.length;j++){
            if(flickrPointsInfo[j].emotion == emotionGroup[theEmotion-1].name){
                flickrPointsArray[j].setVisibility(true);
                flickrLineArray[j].setVisibility(true);
            }else{
                flickrPointsArray[j].setVisibility(false);
                flickrLineArray[j].setVisibility(false);
            }// end if
        }// end for
    }// end if

    if(isClickedHappy == 1){
        for(var k=0;k<happinessCityArray.length;k++){
            for(var l=0; l<happinessCityLineArray[k].length; l++){
                if( (theEmotion-1) == l)
                    happinessCityLineArray[k][l].setVisibility(true);
                else
                    happinessCityLineArray[k][l].setVisibility(false);
            }// end for
        }// end for
    }//end if

}