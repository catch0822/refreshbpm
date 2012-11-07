package refreshbpm

class Feed {

    // Basic info
    String title
    String content
    long publishTime = 0

    // Photo and media info
    SocialMedia socialMedia
    String imagePath
    String imageThumbnailPath
    String sourcePath
    String flickrPhotoId
    String facebookId
    String tweetId

    // Personal info (ex. facebook, twitter, flickr ...) the different service will get the different result
    String userId
    User facebookUser;
    int type = 0; // 0:post 1:photo 2:checkin

    // Job info
    JobTitle job

    // Location info
    String lat = 0  // latitude
    String lng = 0  // longitude
    String country
    String city
    String address

    Emotion mostEmotion

    static hasMany = [emotions: Emotion]
    //static belongsTo =Emotion

    // Happines city and government point check tag
    boolean isCheckedHappinessCity = false
    boolean isCheckedGovPoint = false

    // make sure address query before since there ares some with no address
    boolean isQueryAddress = false

    static constraints ={
        title(nullable:true)
        content(nullable:true)
        socialMedia(nullable:true)
        imagePath(nullable:true)
        flickrPhotoId(nullable:true)
        tweetId(nullable:true)
        userId(nullable:true)
        job(nullable:true)
        country(nullable:true)
        city(nullable:true)
        address(nullable:true)
        mostEmotion(nullable:true)
        facebookUser(nullable:true)
        facebookId(nullable:true)
    }

    static mapping = {
        publishTime  index : 'publish_time_idx'
        country index : 'country_idx'
        title type: 'text'
        content type: 'text'
    }
}
