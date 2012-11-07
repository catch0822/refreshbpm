package refreshbpm

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import grails.converters.*
import java.text.SimpleDateFormat

class FacebookParserService {

    private static String mefeedApi   = "https://graph.facebook.com/me/posts?limit=400&access_token="
    private static String photoApi    = "https://graph.facebook.com/me/photos?limit=400&access_token="
    private static String checkinApi  = "https://graph.facebook.com/me/checkins?limit=400&access_token="

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+0000'")

    def backgroundService
    def emotionDetectorService


	def getUserInfoOnce(user){
	
        backgroundService.execute("Get user facebook info once", {

                println "Start to background Service for user: ${user.id}"

                String apiMe =  mefeedApi + user.accessToken
                String apiPhoto =  photoApi + user.accessToken
				String apiCheckin = checkinApi + user.accessToken
				queryAndStoreMeFeed(apiMe, user)
				queryAndStoreMePhotos(apiPhoto, user)
				queryAndStoreMeCheckins(apiCheckin, user)

            })
	
	}
	
	
    def getUserInfo(user){

        backgroundService.execute("Get user facebook info", {

                println "Start to background Service for user: ${user.id}"

                String apiMe =  mefeedApi + user.accessToken
                String apiPhoto =  photoApi + user.accessToken
		String apiCheckin = checkinApi + user.accessToken
				
				
                def next = queryAndStoreMeFeed(apiMe, user)
                while(next != ""){
                    String apiMe2 = next
                    next = queryAndStoreMeFeed(apiMe2, user)
                }// end while

                def next2 = queryAndStoreMePhotos(apiPhoto, user)
                while(next2 != ""){
                    String apiPhoto2 = next2
                    next2 = queryAndStoreMePhotos(apiPhoto2, user)
                }// end while

                def next3 = queryAndStoreMeCheckins(apiCheckin, user)
                while(next3 != ""){
                    String apiCheckin2 = next3
                    next3 = queryAndStoreMeCheckins(apiCheckin2, user)
                }// end while

            })


    }
  
    private def queryAndStoreMeFeed(apiMe, user){
        
        
        println "queryAndStoreMeFeed: " + apiMe
        
        def resultFeed = connect(apiMe)
        def resultFeedData = resultFeed.data
        println "result data: " + resultFeedData

        if(resultFeedData!=null){

                for(int i=0;i<resultFeedData.size();i++){

                    // Make sure post come from user and not duplicate
                    def mefeed = Feed.findByFlickrPhotoId(resultFeedData[i].id)
                    println "mefeed is null or not: " + mefeed;
                    //def from = resultFeedData[i].from.id
                    //println resultFeedData[i].from.id

                    if( mefeed == null){

                        if(resultFeedData[i].message != null && resultFeedData[i].message != "null" &&
                            resultFeedData[i].message != ""){

                            def emotion = emotionDetectorService.analyser(resultFeedData[i].message)
                            if(emotion!=null){

                                mefeed = new Feed();
                                mefeed.sourcePath = ""
                                mefeed.imageThumbnailPath = ""
                                mefeed.flickrPhotoId = resultFeedData[i].id

                                // Save title and content
                                println "name:" + resultFeedData[i].name
                                println "message:" + resultFeedData[i].message
                                println "description:" + resultFeedData[i].description
                                println "created_time:" + resultFeedData[i].created_time
                                println "icon:" + resultFeedData[i].picture
                                println "picture source: " + resultFeedData[i].source
                                println "link:" + resultFeedData[i].link
                                println "place:" + resultFeedData[i].place


                                if(resultFeedData[i].name!=null)
                                mefeed.title =  resultFeedData[i].name
                                if(resultFeedData[i].message!=null)
                                mefeed.content =  resultFeedData[i].message

                                // Save publish time
                                mefeed.publishTime = parseTimeToLong(resultFeedData[i].created_time)

                                // Save link
                                if(resultFeedData[i].picture!=null)
                                mefeed.imagePath = resultFeedData[i].picture
                                if(resultFeedData[i].icon!=null)
                                mefeed.imageThumbnailPath = resultFeedData[i].icon
                                if(resultFeedData[i].link!=null)
                                mefeed.sourcePath = resultFeedData[i].link

                                // Save location
                                if(resultFeedData[i].place!=null){
                                    mefeed.lat = resultFeedData[i].place.location?.latitude
                                    mefeed.lng = resultFeedData[i].place.location?.longitude
                                    mefeed.country = resultFeedData[i].place.location?.country
                                    mefeed.city = resultFeedData[i].place.location?.city
                                    mefeed.address = resultFeedData[i].place.location?.street
                                }// end if

                                // Save feed
                                mefeed.mostEmotion = emotion
                                mefeed.addToEmotions(emotion)
                                mefeed.type = 0;
                                mefeed.facebookUser = user;
                                mefeed.save(flush:true)

                                // Add emotions
                                if (mefeed.hasErrors())
                                    mefeed.errors.each { println it }
                                else
                                    new FeedEmtionMapped(feed:mefeed,emotion:emotion).save()

                            }//end if

                        }//end if

                    }// end if

                }// end for
                
        }// end if
        println "paging: " + resultFeed.paging


        if(resultFeed.paging == null || resultFeed.paging == "" || resultFeed.paging == "undefined")
        return ""
        else
        return resultFeed.paging.next

    }

	 
	
    private def queryAndStoreMePhotos(apiMe, user){
        
        
        println "queryAndStoreMePhotos: " + apiMe
        
        def resultFeed = connect(apiMe)
        def resultFeedData = resultFeed.data
        println "result data: " + resultFeedData
        
        if(resultFeedData!=null){

                for(int i=0;i<resultFeedData.size();i++){

                    // Make sure post come from user and not duplicate
                    def mefeed = Feed.findByFlickrPhotoId(resultFeedData[i].id)
                    println "mefeed is null or not: " + mefeed;
                    if( mefeed == null){

                        if(resultFeedData[i].name != null && resultFeedData[i].name != "null" &&
                            resultFeedData[i].name != ""){

                            def emotion = emotionDetectorService.analyser(resultFeedData[i].name)
                            if(emotion!=null){

                                mefeed = new Feed();
                                mefeed.sourcePath = ""
                                mefeed.imageThumbnailPath = ""
                                mefeed.flickrPhotoId = resultFeedData[i].id

                                // Save title and content
                                println "name:" + resultFeedData[i].name
                                println "created_time:" + resultFeedData[i].created_time
                                println "icon:" + resultFeedData[i].picture
                                println "source: " + resultFeedData[i].source
                                println "place:" + resultFeedData[i].place


                                if(resultFeedData[i].name!=null)
                                mefeed.title =  resultFeedData[i].name

                                // Save publish time
                                mefeed.publishTime = parseTimeToLong(resultFeedData[i].created_time)

                                // Save link
                                if(resultFeedData[i].source!=null)
                                mefeed.imagePath = resultFeedData[i].source
                                if(resultFeedData[i].picture!=null)
                                mefeed.imageThumbnailPath = resultFeedData[i].picture

                                // Save location
                                if(resultFeedData[i].place!=null){
                                    mefeed.lat = resultFeedData[i].place.location?.latitude
                                    mefeed.lng = resultFeedData[i].place.location?.longitude
                                    mefeed.country = resultFeedData[i].place.location?.country
                                    mefeed.city = resultFeedData[i].place.location?.city
                                    mefeed.address = resultFeedData[i].place.location?.street
                                }// end if

                                // Save feed
                                mefeed.mostEmotion = emotion
                                mefeed.addToEmotions(emotion)
                                mefeed.type = 1;
                                mefeed.facebookUser = user;
                                mefeed.save(flush:true)
                                                              
                                // Add emotions
                                if (mefeed.hasErrors())
                                    mefeed.errors.each { println it }
                                else
                                    new FeedEmtionMapped(feed:mefeed,emotion:emotion).save()

                            }//end if

                        }// end if

                    }// end if

                }// end for

        }// end if
        println "paging: " + resultFeed.paging


        if(resultFeed.paging == null || resultFeed.paging == "" || resultFeed.paging == "undefined")
        return ""
        else
        return resultFeed.paging.next

    }


    private def queryAndStoreMeCheckins(apiMe, user){
        
        
        println "queryAndStoreMeCheckins: " + apiMe
        
        def resultFeed = connect(apiMe)
        def resultFeedData = resultFeed.data
        println "result data: " + resultFeedData

        if(resultFeedData!=null){

                for(int i=0;i<resultFeedData.size();i++){

                    // Make sure post come from user and not duplicate
                    def mefeed = Feed.findByFlickrPhotoId(resultFeedData[i].id)
                    println "mefeed is null or not: " + mefeed;
                    if( mefeed == null){

                        if(resultFeedData[i].message != null && resultFeedData[i].message != "null" &&
                            resultFeedData[i].message != ""){

                            def emotion = emotionDetectorService.analyser(resultFeedData[i].message)
                            if(emotion!=null){

                                mefeed = new Feed();
                                mefeed.sourcePath = ""
                                mefeed.imageThumbnailPath = ""
                                mefeed.flickrPhotoId = resultFeedData[i].id

                                // Save title and content
                                println "created_time:" + resultFeedData[i].created_time
                                println "message:" + resultFeedData[i].message
                                println "place:" + resultFeedData[i].place
                                if(resultFeedData[i].message!=null)
                                mefeed.content =  resultFeedData[i].message
                                
                                // Save publish time
                                mefeed.publishTime = parseTimeToLong(resultFeedData[i].created_time)

                                // Save location
                                if(resultFeedData[i].place!=null){
                                    mefeed.lat = resultFeedData[i].place.location?.latitude
                                    mefeed.lng = resultFeedData[i].place.location?.longitude
                                    mefeed.country = resultFeedData[i].place.location?.country
                                    mefeed.city = resultFeedData[i].place.location?.city
                                    mefeed.address = resultFeedData[i].place.location?.street
                                }// end if

                                // Save feed
                                mefeed.mostEmotion = emotion
                                mefeed.addToEmotions(emotion)
                                mefeed.type = 2;
                                mefeed.facebookUser = user;
                                mefeed.save(flush:true)

                                // Add emotions
                                if (mefeed.hasErrors())
                                    mefeed.errors.each { println it }
                                else
                                    new FeedEmtionMapped(feed:mefeed,emotion:emotion).save()
                                    
                            }//end if

                        }//end if

                    }// end if

                }// end for

        }//end if
        println "paging: " + resultFeed.paging


        if(resultFeed.paging == null || resultFeed.paging == "" || resultFeed.paging == "undefined")
        return ""
        else
        return resultFeed.paging.next

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

    private long parseTimeToLong(jtdate){
        def date = sdf.parse(jtdate);
        return date.getTime()
    }

    private static String FACEBOOK_SEARCH_URL = "https://graph.facebook.com/search"
    private static boolean isWorking = false
    private static final MAX_PER_REQUEST = 20
    private Random random = new Random()

///q=%E9%96%8B%E5%BF%83&type=post
    def getPublicPost() {
         if(isWorking==false){
            isWorking = true
            try{
                for(int i=0;i<MAX_PER_REQUEST;i++){  /** becuase the twitter has limit for request 180/windows?! */
                    int randomId = random.nextInt(Emotion.count)
                    def emotion = Emotion.get(randomId)
                   // def emotion = Emotion.get(1)
                    if(emotion != null){
                        boolean hasNext = true
                        def s = FACEBOOK_SEARCH_URL + "?type=post&q=${emotion.name}" 
                        while(hasNext){
                            def d = connect(s)
                            println "[GET PUBLIC POST] ${s} num : ${d.data?.size()}"
                            d.data?.each{
                                try{
                                    _saveFeed(it,emotion)
                                }catch(Exception e){
                                    println "[Exception] ${e}"
                                }
                            }
                            //println "[PAGING] ${d.paging}"
                            if(d.paging!=null){
                                //FIXME the chinese query will error after translate into unicode
                                s = FACEBOOK_SEARCH_URL + "?type=post&q=${emotion.name}&until="+ getNextUrl(d.paging.next)
                            }else{
                                hasNext = false
                            }
                        }
                    }
                }
                isWorking = false
            }catch(Exception e){ 
                println e
                isWorking = false 
            }
        }
    }

    private String getNextUrl(def next){
        return next[(next.indexOf("until=")+6)..-1]
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ",Locale.ENGLISH)
    private Date string2date(String d){
        return sdf.parse(d);
    }
    //"2012-11-04T03:35:03+0000"

    private void _saveFeed(def f , def emotion ){
        def feed = Feed.findByFacebookId(f.id)
       // println "[PROC FACEBOOK PUBLIC] ${f}"
                if(feed == null){
                    feed = new Feed()
                    feed.facebookId = f.id
                    feed.title = f.caption
                    feed.content = f.message
                    feed.publishTime = string2date(f.created_time).getTime()
                    feed.userId = f.from.id
                    feed.imagePath = (f.picture == null) ? "" : f.picture
                    feed.sourcePath= (f.link == null) ? "" : f.link
                    feed.imageThumbnailPath= (f.picture == null) ? "" : f.picture //TODO replace by facebook user icon
                    
                  //  feed.lat= f.latitude
                  //  feed.lng= f.longitude
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
}
