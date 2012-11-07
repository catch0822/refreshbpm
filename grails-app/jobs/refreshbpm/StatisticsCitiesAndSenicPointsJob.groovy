package refreshbpm

class StatisticsCitiesAndSenicPointsJob {
    static triggers = {
      simple repeatInterval: 1200000 // execute job once in 5 seconds
    }

    def execute() {

       println "start to statistic happiness city and gov points .."

        // Get lots of feeds
        def fd = Feed.createCriteria();
        def feeds = fd.list{
            isNotNull("address")
            like("country", "taiwan")
            isNotNull("city")
            ne("lat", "0")
            ne("lng", "0")
            eq('isCheckedHappinessCity', false)
            eq('isCheckedGovPoint', false)
            order("publishTime", "desc")
            maxResults(1500)
        }

        // Get happyness city info
        def happinessCities = HappinessCity.list()

        // Get government point info
        def govPoints = GovermentTouristInfo.list()
        int evaluateRadius = 1500 // meter


        for(int i=0;i<feeds.size();i++){

            // Get emotions
            def emotions = FeedEmtionMapped.findAllByFeed(feeds[i])
            
            // Recognize city
            def city = feeds[i].city.toLowerCase()
            for(int j=0; j<happinessCities.size(); j++){
                if(city.indexOf(happinessCities[j].name)!=-1){


                    // Calculate Happiness city statistic
                    for(int k=0;k<emotions.size();k++){

                        def feedEmotionName = emotions[k].emotion.emotionGroup.name

                        //println emotions[k].emotion
                        //println feedEmotionName

                        if(feedEmotionName == "高興")
                            happinessCities[j].happy = happinessCities[j].happy + 1
                        if(feedEmotionName == "悲傷")
                            happinessCities[j].sad = happinessCities[j].sad + 1
                        if(feedEmotionName == "憂愁")
                            happinessCities[j].worried = happinessCities[j].worried + 1
                        if(feedEmotionName == "憤怒")
                            happinessCities[j].angry = happinessCities[j].angry + 1
                        if(feedEmotionName == "失望")
                            happinessCities[j].disappoint = happinessCities[j].disappoint + 1
                        if(feedEmotionName == "平靜")
                            happinessCities[j].peace = happinessCities[j].peace + 1
                        if(feedEmotionName == "慌張")
                            happinessCities[j].nervous = happinessCities[j].nervous + 1
                        if(feedEmotionName == "害怕")
                            happinessCities[j].afraid = happinessCities[j].afraid + 1
                        if(feedEmotionName == "感動")
                            happinessCities[j].touched = happinessCities[j].touched + 1
                        if(feedEmotionName == "希望")
                            happinessCities[j].hope = happinessCities[j].hope + 1
                        if(feedEmotionName == "愛")
                            happinessCities[j].love = happinessCities[j].love + 1
                        if(feedEmotionName == "恨")
                            happinessCities[j].hate = happinessCities[j].hate + 1

                        happinessCities[j].save(flush:true)

                    }// end for


                }// end if
            }// end for


            for(int j=0; j<govPoints.size(); j++){

                if(govPoints[j].city == feeds[i].city){

                        def govLat = govPoints[j].lat
                        def gobLng = govPoints[j].lng
                        def lat = Double.parseDouble(feeds[i].lat)
                        def lng = Double.parseDouble(feeds[i].lng)
                        def distance = calculateDistance(govLat, gobLng, lat, lng)
                        if(distance <= evaluateRadius){

                            // Calculate Gov points statistic
                            for(int k=0;k<emotions.size();k++){

                                def feedEmotionName = emotions[k].emotion.emotionGroup.name

                                if(feedEmotionName == "高興" ||
                                   feedEmotionName == "平靜" ||
                                   feedEmotionName == "感動" ||
                                   feedEmotionName == "希望" ||
                                   feedEmotionName == "愛"){

                                    govPoints[j].positive = govPoints[j].positive + 1

                                }else{

                                    govPoints[j].negative = govPoints[j].negative + 1

                                }// end if

                                govPoints[j].save(flush:true)

                            }// end for

                        }// end for

                }// end if

            }// end for

            
            feeds[i].isCheckedHappinessCity = true
            feeds[i].isCheckedGovPoint = true
            feeds[i].save(flush:true)
            
        }// end for

        
        // Statistic happy city


        // Statistic gov point


    }

    // Calculate the distance between two latitude and longitude
    def calculateDistance(double lat1, double lng1, double lat2, double lng2){
        
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        //println "distance between nodes: " + String.valueOf(dist * meterConversion)
        
        return dist * meterConversion;

    }




}
