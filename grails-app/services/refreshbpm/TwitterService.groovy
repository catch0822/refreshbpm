package refreshbpm

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import grails.converters.*
import java.text.SimpleDateFormat
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;


class TwitterService {

	private static String TWITTER_CONSUMER_KEY="JbfjZuKuo8sAUGycT1DWOw"
	private static String TWITTER_CONSUMER_SECRET="6OWFwN8E1I8aajcfvaIPfDMHEIQLgSzyJktIl4VE"
	private static String TWITTER_TOKEN="7119682-7j1M3AwaUAprKAGk2EOBJ6RyRZQYcK3IJybgMU"
	private static String TWITTER_TOKEN_SECRET="ewYvan0PRuzkJdj4DdHNR2jtYNCJ8MnFt8SycV6Vqik"
    private static DefaultOAuthConsumer consumer = new DefaultOAuthConsumer(
                TWITTER_CONSUMER_KEY,
                TWITTER_CONSUMER_SECRET)
    private static def random = new Random()
    private static boolean isWorking = false
    def getRecentTweet() {
        if(isWorking==false){
            isWorking = true
            try{
        consumer.setTokenWithSecret(TWITTER_TOKEN,TWITTER_TOKEN_SECRET)
        for(int i=0;i<5;i++){  /** becuase the twitter has limit for request 180/windows?! */
        int randomId = random.nextInt(Emotion.count)
        def emotion = Emotion.get(randomId)
        if(emotion!=null){
       // Emotion.createCriteria().list{
       //         order("intensity","asc")
        //    }.each{ emotion->
                println " proc : ${emotion.name}"
                URL url = new URL("https://api.twitter.com/1.1/search/tweets.json?q=${java.net.URLEncoder.encode(emotion.name)}")
                HttpURLConnection request = (HttpURLConnection) url.openConnection()
                consumer.sign(request)
                request.connect()
               // System.out.println("Response: " + request.getResponseCode() + " "
                //        + request.getResponseMessage())
               getConnectionContent(request).statuses.each{
                    try{
                        saveFeed(it,emotion)
                    }catch(Exception e){ println "[EXCEPTION] ${e}"}
                }
                request.disconnect()
        }
        }
            isWorking = false
            }catch(Exception e){isWorking=false}
        }
    }

    // "created_at": "Mon Sep 24 03:35:21 +0000 2012",
    private static SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy",Locale.ENGLISH)
    private def string2date(String d){
      //  println "[string to data ] "+d
        return sdf.parse(d);
    }

    private void saveFeed(def f , def emotion ){
        def feed = Feed.findByTweetId(f.id)
        //println "proc : " + f
        if(feed == null){
            feed = new Feed()
            feed.tweetId = f.id
            feed.title = f.user.name
            println "[ get content ] ${f.text}"
            try{
              feed.content = f.text
            }catch(Exception e){ feed.content = "x" }
            feed.publishTime = string2date(f.created_at).getTime() 
            feed.userId = f.user.id
            feed.imagePath = f.user.profile_image_url
            feed.imageThumbnailPath = f.user.profile_image_url
            feed.sourcePath=f.source
                
            if(emotion!=null){
                feed.mostEmotion = emotion
                feed.addToEmotions(emotion)
            }
            feed.save(flush:true)
            if (feed.hasErrors())
                feed.errors.each { println it }
            else
                new FeedEmtionMapped(feed:feed,emotion:emotion).save()
            println "save feed from twitter : ${feed}"
        }else{
            println "This photo is already in database!!!"
        }// end if
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
    private def getConnectionContent(def request){
        InputStream is = request.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
       // println " content : " + response
        return JSON.parse(response.toString())
    }
}
