package refreshbpm

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import grails.converters.*
import java.text.SimpleDateFormat

class ParserService {

   	private static String FLICKR_KEY = "c588daa85452f53eb3babe7a893a359d"
    private static String FLICKR_SECRET = "b5cdb800bb1cf994"
    private static String YQL_SERVER_SITE = "http://query.yahooapis.com/v1/public/yql"
    private static String FLICKR_SITE="http://api.flickr.com/services/rest/"

    private static boolean isRunning =false;
    private static boolean isRunning2 =false;

    private def getPhotoSrc(String id){
        def q = URLEncoder.encode("select * from flickr.photos.sizes where api_key='${FLICKR_KEY}' AND photo_id='${id}'")
        def requestPath =  "${YQL_SERVER_SITE}?q=${q}&format=json"
        def p = connect(requestPath)
    	def result;
        p.query.results.size.each{
        	result = it.source
        }
        return result
	}

    private def connect(String url){
        DefaultHttpClient httpclient = new DefaultHttpClient()
        HttpGet httpGet = new HttpGet(url)
        HttpResponse response1 = httpclient.execute(httpGet)
        try {
            //println response1.getStatusLine()
            HttpEntity entity1 = response1.getEntity()
            return JSON.parse(EntityUtils.toString(entity1))
        } finally {
            httpGet.releaseConnection()
        }
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private Date string2date(String d){
        return sdf.parse(d);
    }

    private void _saveFeed(def f , def emotion , def content){
             def feed = Feed.findByFlickrPhotoId(f.id);
                if(feed == null){
                 //   println "[_parseFlickrRestResult] ${f}"
                    feed = new Feed()
                    feed.flickrPhotoId = f.id
                    feed.title = f.title
                    //feed.content = (f.description?._content != null ) ? f.description?._content : f.description
                    feed.content = content
                    feed.publishTime = string2date(f.datetaken).getTime()
                    feed.userId = f.owner
                    //feed.imagePath = r.urls.url.content //TODO
                    //feed.sourcePath=getPhotoSrc(f.id)
                    getPhotoSrc(f.id,feed)
                    
                    feed.lat= f.latitude
                    feed.lng= f.longitude
                    // Process locaiton data
                   // def locationInfo = r.owner.location //TODO
                    if(emotion!=null){
                        feed.mostEmotion = emotion
                        feed.addToEmotions(emotion)
                    }
                    feed.save(flush:true)
                    println " @@@@@@@@@@@@@@@@@@@ feed " + feed
                    if (feed.hasErrors())
                        feed.errors.each { println it }
                    else
                        new FeedEmtionMapped(feed:feed,emotion:emotion).save()
                }else{
                    println "This photo is already in database!!!"
                }// end if
    }

  private void _listParseFromYQL(def rp,def emotion){
        def qr = connect(rp)
       // println "[_listParseFromYQL] ${qr.query.count} "
        if(qr.query.count>0){
            qr.query.results?.photo?.each{
                try{
                    _saveFeed(it,emotion,it.description)
                }catch(Exception e){ println "[EXCEPTION] ${e}" }
            }
        }
    }

    private int _listParseFromFlickrApi(def requestPath,def emotion){
        def qr = connect(requestPath)
        qr.photos?.photo.each{
            try{
                if(emotion==null){
                    def s = it.title  + "" + it.description._content
                    emotion =   emotionDetectorService.analyser(s)
                    //println "*********** emotionDetectorService.analyser " + emotion + " in " + s
                }
                _saveFeed(it,emotion,it.description._content)
            }catch(Exception e){ println "[EXCEPTION] ${e}" }
        }
        return qr.photos?.pages
    }

    private String getPlaceId(def place){
        def rp =  "${FLICKR_SITE}?method=flickr.places.find&query=${place}&format=json&nojsoncallback=1&api_key=${FLICKR_KEY}"
       // println "[getPlaceId] ${rp}"
        def json = connect(rp)
       // println "[getPlaceId] ${json}"
        return json.places.place[0].place_id
    }
/*
{ "sizes": { "canblog": 0, "canprint": 0, "candownload": 0, 
    "size": [
      { "label": "Square", "width": 75, "height": 75, "source": "http:\/\/farm9.staticflickr.com\/8471\/8089829974_1afa260fa3_s.jpg", "url": "http:\/\/www.flickr.com\/photos\/digital_trance\/8089829974\/sizes\/sq\/", "media": "photo" },
      { "label": "Large Square", "width": "150", "height": "150", "source": "http:\/\/farm9.staticflickr.com\/8471\/8089829974_1afa260fa3_q.jpg", "url": "http:\/\/www.flickr.com\/photos\/digital_trance\/8089829974\/sizes\/q\/", "media": "photo" },
      { "label": "Thumbnail", "width": 70, "height": 100, "source": "http:\/\/farm9.staticflickr.com\/8471\/8089829974_1afa260fa3_t.jpg", "url": "http:\/\/www.flickr.com\/photos\/digital_trance\/8089829974\/sizes\/t\/", "media": "photo" },
      { "label": "Small", "width": "167", "height": "240", "source": "http:\/\/farm9.staticflickr.com\/8471\/8089829974_1afa260fa3_m.jpg", "url": "http:\/\/www.flickr.com\/photos\/digital_trance\/8089829974\/sizes\/s\/", "media": "photo" },
      { "label": "Small 320", "width": "223", "height": "320", "source": "http:\/\/farm9.staticflickr.com\/8471\/8089829974_1afa260fa3_n.jpg", "url": "http:\/\/www.flickr.com\/photos\/digital_trance\/8089829974\/sizes\/n\/", "media": "photo" },
      { "label": "Medium", "width": "349", "height": "500", "source": "http:\/\/farm9.staticflickr.com\/8471\/8089829974_1afa260fa3.jpg", "url": "http:\/\/www.flickr.com\/photos\/digital_trance\/8089829974\/sizes\/m\/", "media": "photo" },
      { "label": "Medium 640", "width": "446", "height": "640", "source": "http:\/\/farm9.staticflickr.com\/8471\/8089829974_1afa260fa3_z.jpg", "url": "http:\/\/www.flickr.com\/photos\/digital_trance\/8089829974\/sizes\/z\/", "media": "photo" },
      { "label": "Medium 800", "width": "558", "height": "800", "source": "http:\/\/farm9.staticflickr.com\/8471\/8089829974_1afa260fa3_c.jpg", "url": "http:\/\/www.flickr.com\/photos\/digital_trance\/8089829974\/sizes\/c\/", "media": "photo" },
      { "label": "Large", "width": "714", "height": "1024", "source": "http:\/\/farm9.staticflickr.com\/8471\/8089829974_1afa260fa3_b.jpg", "url": "http:\/\/www.flickr.com\/photos\/digital_trance\/8089829974\/sizes\/l\/", "media": "photo" }
    ] }, "stat": "ok" }
*/

    private def getPhotoSrc(String id , def feed){
        def rp =  "${FLICKR_SITE}?method=flickr.photos.getSizes&photo_id=${id}&format=json&nojsoncallback=1&api_key=${FLICKR_KEY}"
       // println "[getPhotoSrc] ${rp}"
        def json = connect(rp)
       // println "[getPhotoSrc] ${json}"
        def thumbnail
        int c = 0
        json.sizes.size.each{
            if(it.label == "Thumbnail" )
                thumbnail = it.source
            c++
        }
      //  println "@@@@@ json.sizes.size.size() = ${json.sizes.size.size()}"
        feed.sourcePath = json.sizes.size[json.sizes.size.size()-1].url
        feed.imagePath = json.sizes.size[json.sizes.size.size()-1].source
        feed.imageThumbnailPath = thumbnail
    }


    /** YQL */
	def parseFlickrByYql() {
        if(isRunning==false){
            isRunning = true;
            println("------------------------------ exec flickrByKeywordSearch---------------")
		    Emotion.list().each{ emotion->
			    try{
    			    def q = URLEncoder.encode("select * from flickr.photos.search where text='${emotion.name}' and api_key='${FLICKR_KEY}' and extras='geo,description,date_taken'")
                    //def q = URLEncoder.encode("select * from flickr.photos.search where has_geo='true' and tags='${emotion.name}' and api_key='${FLICKR_KEY}' and extras='geo,description,date_taken'")
        		    def requestPath =  "${YQL_SERVER_SITE}?q=${q}&format=json"
        		   // println "[flickrByKeywordSearch]] ${requestPath}"
            	    _listParseFromYQL(requestPath,emotion)
            	}catch(Exception e){ println e }
		    }
            isRunning=false
        }
	}

    /** YQL */
    def flickr() {
		def q = URLEncoder.encode("select * from flickr.photos.recent where api_key='${FLICKR_KEY}' and extras='geo,description,date_taken'")
    	def rp =  "${YQL_SERVER_SITE}?q=${q}&format=json"
   //     println "[flickr] ${rp}"
    	_listParseFromYQL(rp,null)
    }

    def parseRencentFlick(){
        int page = 1
        int maxPage = -1
        while(page<=maxPage||maxPage==-1){
            def rp =  "${FLICKR_SITE}?method=flickr.photos.getRecent&format=json&nojsoncallback=1" +
                      "&api_key=${FLICKR_KEY}&page=${page}&extras=geo%2Ctags%2Cdate_taken%2Cdescription%2C"
           // println rp
            if(maxPage==-1)
                maxPage = _listParseFromFlickrApi(rp,null)
            else
                _listParseFromFlickrApi(rp,null)
            page++
            println "@@@@@@@@@ ${page} / ${maxPage}"
        }
    }

    def parseFlickrByApi() {
         _parseFlickrByApi('text',true)
    }
    def parseFlickrByApi2() {
         _parseFlickrByApi('tags',true)
    }

    def parseFlickrNoGeoLimited() {
         _parseFlickrByApi('text',false)
    }

    private _parseFlickrByApi(def searchField , boolean needGeo) {
         if(isRunning2==false){
            isRunning2 = true;
            println("------------------------------ exec flickrByTagSearch2 -------------------")

            //Emotion.list(sort: "id", order: "desc").each{ emotion->
            Emotion.createCriteria().list{
                order("intensity","desc")
            }.each{ emotion->
                println " proc ${emotion.name} = ${emotion.intensity}"
//             Emotion.findAll("from Emotion as b order by DESC").each{ emotion ->
                    try{
                        int page = 1
                        int maxPage = -1
                        while(page<=maxPage||maxPage==-1){
                            def rp = ""
                            if(needGeo)
                                rp =  "${FLICKR_SITE}?method=flickr.photos.search&format=json&nojsoncallback=1&has_geo=true&" +
                                               "place_id=${getPlaceId('taiwan')}&api_key=${FLICKR_KEY}&${searchField}=${emotion.name}&page=${page}&"+
                                               "extras=geo%2Ctags%2Cdate_taken%2Cdescription%2C"
                            else
                                rp =  "${FLICKR_SITE}?method=flickr.photos.search&format=json&nojsoncallback=1&has_geo=true&" +
                                               "api_key=${FLICKR_KEY}&${searchField}=${emotion.name}&page=${page}&"+
                                               "extras=geo%2Ctags%2Cdate_taken%2Cdescription%2C"
                           // println rp
                            if(maxPage==-1)
                                maxPage = _listParseFromFlickrApi(rp,emotion)
                            else
                                _listParseFromFlickrApi(rp,emotion)
                            println "@@@@@@@@@ ${page} / ${maxPage}"
                            page++
                            
                        }
                    }catch(Exception e){ println e }
            }
            isRunning2=false
        }
    }

    def emotionDetectorService

    def getCurrentPhotos() {
         if(isRunning2==false){
            isRunning2 = true;
            try{
                int page = 1
                int maxPage = -1
                while(page<=maxPage||maxPage==-1){
                    def rp =  "${FLICKR_SITE}?method=flickr.photos.getRecent&format=json&nojsoncallback=1&" +
                                               "api_key=${FLICKR_KEY}&page=${page}&"+
                                               "extras=geo%2Ctags%2Cdate_taken%2Cdescription%2C"
                    if(maxPage==-1)
                        maxPage = _listParseFromFlickrApi(rp,null)
                    else
                        _listParseFromFlickrApi(rp,null)
                    page++
                    println "@@@@@@@@@ ${page} / ${maxPage}"
                }
            }catch(Exception e){ println e }
            isRunning2=false
        }
    }
}
