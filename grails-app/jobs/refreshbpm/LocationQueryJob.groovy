package refreshbpm
import grails.converters.*;

class LocationQueryJob {
    static triggers = {
      // The total times of geocoder query, since google api has some limitation (query three record by once)
      // Limit: one ip 2500 per day
      simple repeatInterval: 100000
    }

    def execute() {
        println "start to location query job .."

        //Get the latest location record
        def fd = Feed.createCriteria();
        def feeds = fd.list{
            isNull("address")
            ne("lat", "0")
            ne("lng", "0")
            eq("isQueryAddress", false)
            maxResults(3)
            order("publishTime", "desc")
        }
        for(int i=0; i<feeds.size(); i++){
            Feed.withTransaction {
                def result = googleGeoCoder(feeds[i].lat, feeds[i].lng);
                if(result != "error"){
                    def resultJ = JSON.parse(result);
                    if(resultJ.status.toLowerCase() == "ok"){
                        String address = resultJ["results"].formatted_address[0].toString()

                        def theSizeOfAddress = resultJ["results"].formatted_address.size()
                        def theSizeOfAddress2 = theSizeOfAddress
                        if(theSizeOfAddress >= 2){

                            // Set country
                            String country = ""
                            for(int f=theSizeOfAddress-1; f>=0; f--){
                                if(resultJ["results"].formatted_address[f] != null ||
                                    resultJ["results"].formatted_address[f] != "null" ||
                                    resultJ["results"].formatted_address[f] != ""){
                                    country = resultJ["results"].formatted_address[f].toString()
                                    country = country.toLowerCase()
                                    country = country.replaceAll("city", "")
                                    country = country.replaceAll(",", "")
                                    country = country.trim()
                                    if(country.indexOf("taiwan")!=-1)
                                        country = "taiwan"
                                    if(country.indexOf("china")!=-1)
                                        country = "china"
                                    if(country.indexOf("united")!=-1)
                                        country = "united states"
                                    if(country.indexOf("indonesia")!=-1)
                                        country = "indonesia"
                                    if(country.indexOf("brazil")!=-1)
                                        country = "brazil"
                                    if(country.indexOf("mexico")!=-1)
                                        country = "mexico"
                                    break;
                                }// end if
                            }// end for

                            // Set city
                            String city = ""
                            for(int f=theSizeOfAddress2-2; f>=0; f--){

                                if(resultJ["results"].formatted_address[f] != null ||
                                    resultJ["results"].formatted_address[f] != "null" ||
                                    resultJ["results"].formatted_address[f] != ""){
                                    
                                    city = resultJ["results"].formatted_address[f].toString()
                                    city = city.toLowerCase()
                                    city = city.replaceAll("city", "")
                                    city = city.replaceAll("country", "")
                                    city = city.replaceAll(",", "")
                                    if(city.indexOf("taipei")!=-1){
                                        city = "taipei"
                                    }else if(city.indexOf("kaohsiung")!=-1){
                                        city = "kaohsiung"
                                    }else if(city.indexOf("yilan")!=-1){
                                        city = "yilan"
                                    }else if(city.indexOf("taoyuan")!=-1){
                                        city = "taoyuan"
                                    }else if(city.indexOf("hsinchu")!=-1){
                                        city = "hsinchu"
                                    }else if(city.indexOf("miaoli")!=-1){
                                        city = "miaoli"
                                    }else if(city.indexOf("taichung")!=-1){
                                        city = "taichung"
                                    }else if(city.indexOf("changhua")!=-1){
                                        city = "changhua"
                                    }else if(city.indexOf("nantou")!=-1){
                                        city = "nantou"
                                    }else if(city.indexOf("yunlin")!=-1){
                                        city = "yunlin"
                                    }else if(city.indexOf("chiayi")!=-1){
                                        city = "chiayi"
                                    }else if(city.indexOf("tainan")!=-1){
                                        city = "tainan"
                                    }else if(city.indexOf("pingtung")!=-1){
                                        city = "pingtung"
                                    }else if(city.indexOf("hualien")!=-1){
                                        city = "hualien"
                                    }else if(city.indexOf("keelung")!=-1){
                                        city = "keelung"
                                    }else{}
                                    
                                    break;
                                }// end if

                            }// end for

                            feeds[i].address = address
                            feeds[i].country = country
                            feeds[i].city = city

                        }// end if
                        
                        feeds[i].isQueryAddress = true;
                        feeds[i].save(flush:true)
                        
                    }else{

                        feeds[i].isQueryAddress = true;
                        feeds[i].save(flush:true);
                        
                    }// end if
                }//end if
            }
        }// end for

    }

    //Query the address by goolge geo-coder api.
    private static String googleGeoCoder(String lat, String lng)  throws Exception {

        // Define related resource
        String url = "http://maps.google.com/maps/api/geocode/json?latlng=";
        String latlng = lat + "," + lng;
        url = url + latlng + "&sensor=false" + "&language=en_US"; //en_US zh_TW

        HttpURLConnection c = null;
        StringBuffer b;
        try {
                URL myurl;
                int rc;

                myurl = new URL(url);
                c = (HttpURLConnection)myurl.openConnection();
                c.setDoOutput(true);
                c.setRequestMethod("GET");
                c.setRequestProperty("Accept", "*/*");
                c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                c.connect();
                rc = c.getResponseCode();
                //println("Response Code: " + rc + "\n");
                if (rc != 200) {
                    println("Response Message: " + c.getResponseMessage() + "\n\n");
                    return "error"
                }else{

                    //Get Response
                    InputStream is = c.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();
                    while((line = rd.readLine()) != null) {
                      response.append(line);
                      response.append('\r');
                    }
                    rd.close();
                    //println response.toString();
                    return  response.toString();
                
                }//end if

          }catch(Exception e){
            e.printStackTrace();
            return null;
          }finally{
            if(c != null) {
              c.disconnect();
            }
          }
    }

}