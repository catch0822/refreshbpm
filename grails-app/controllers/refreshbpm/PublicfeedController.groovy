package refreshbpm

import grails.converters.JSON
import java.text.SimpleDateFormat
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class PublicfeedController {

    static defaultAction = "emotionBalls"

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+0000'")
    
    def emotionBalls() {
		if(params.mode=="global")
			session.mapMode=null
		else if(params.mode=="personal")
			session.mapMode="personal"
	}
    
    def emotionBallsTunnel() {}

    def emotionChart(){
        
    }
	
def changeEmptionGroup(){
			
		def dadeList=[]
		def amountList=[]
		
//		def firstDate=new Date(Feed.createCriteria().get{
//			ne("publishTime",0.toLong())
//			order("publishTime","asc")
//			maxResults(1)
//			projections{
//				property("publishTime")
//			}
//		})
		def dateFormat =new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -2);
		def formatDate=cal.getTime().format("yyyy-MM-dd HH")
		def firstDate=dateFormat.parse(formatDate)
		
		
		def lastDate=new Date(Feed.createCriteria().get{
			if(session.facebookUser&&session.mapMode=="personal")
				eq('facebookUser',session.facebookUser)
			ne("publishTime",0.toLong())
			lt('publishTime',new Date().getTime())
			order("publishTime","desc")
			maxResults(1)
			projections{
				property("publishTime")
			}
		})
		
		def formatFirstDate=dateFormat.parse(firstDate.format("yyyy-MM-dd")).getTime()
		def formatLastDate=dateFormat.parse(lastDate.format("yyyy-MM-dd")).getTime()
		// one hour diff=3600000
		// one day diff=86400000
		long diff=86400000
		def first=formatFirstDate
		def last=formatFirstDate+diff
		def emotionGroup=EmotionGroup.get(Integer.parseInt(params.emotionGroupNameId))

		def emotionList=new ArrayList(Emotion.createCriteria().list{
			createAlias("emotionGroup", "e")
				eq("e.id",emotionGroup.id)
			projections {
				property "name"
			}
		})
		
		while(1){

			def feedList=new ArrayList(Feed.createCriteria().list{
				if(session.facebookUser&&session.mapMode=="personal")
					eq('facebookUser',session.facebookUser)
				ne("publishTime",0.toLong())
				gt('publishTime',first.toLong())
				lt('publishTime',last.toLong())
				mostEmotion{
					projections {
						property "name"
					}
				}
			})
			
			feedList.retainAll(emotionList)
			def amount=feedList.size()
			if (first>=formatLastDate)
				break
			else {
				dadeList.add(first-3600000*8) // parse to GMT
				amountList.add(amount)
				first+=diff
				last+=diff
		}
		}
	
		def ret=[]
		ret.add("date":dadeList,"amount":amountList,"title":"Emotion ("+emotionGroup.name+")")
		
		render ret as JSON
		
	}
	
	def getRandomFeed(){
		
		def emotionIdList=new ArrayList(Emotion.createCriteria().list{
			order("intensity","desc")
			createAlias("emotionGroup", "e")
				eq("e.id",Long.parseLong(params.emotionGroupNameId))
			projections {
				property "id"
			}
		})
		def howMany=null //for Prada
		if(params.howMany)
			howMany=Integer.parseInt(params.howMany)
		
		def titleList=[], contentList=[], imagePathList=[], imageThumbnailPathList=[], intensityList=[], first, last
		if(params.date!="null"&&params.date){
			 first=params.date.toLong()+(3600000*8) // parse to GMT+8 timeZone
			 last=first+86400000 // add one day
		}
		else{
			Calendar cal = Calendar.getInstance();		
			def dateFormat =new SimpleDateFormat("yyyy-MM-dd");
			
			def formatDate1=cal.getTime().format("yyyy-MM-dd");
			last=dateFormat.parse(formatDate1).getTime()
			cal.add(Calendar.YEAR, -2); // recent 2 years
			def formatDate2=cal.getTime().format("yyyy-MM-dd")
			first=dateFormat.parse(formatDate2).getTime()
		}
		//if(session.facebookUser&&session.mapMode=="personal")
		//eq('facebookUser',session.facebookUser)
		def feedList=Feed.createCriteria().list{
			if(session.facebookUser&&session.mapMode=="personal")
				eq('facebookUser',session.facebookUser)
			gt('publishTime',first)
			lt('publishTime',last)
			or{
				for(int i=0;i<emotionIdList.size();i++){
					eq('mostEmotion',Emotion.get(emotionIdList[i]))	
				}		
			}
			order ('publishTime', "desc")
			if(howMany) //for Prada
				maxResults(howMany)
			else
			maxResults(18)
			projections {
				property('title')
				property('content')
				property('imagePath')
				property('imageThumbnailPath')
				mostEmotion{
					property('intensity')
				}
			}
		}.each{ f->
			if(f[0]&&f[0]!=""){
				def tmp=f[0]
				titleList.add(f[0])
			}
			else
				titleList.add("")
			
			def content=f[1]
			def resContent
			if(content&&content!=""){
				resContent = content.replaceAll("\\<.*?\\>","");
				resContent= resContent.replaceAll("\"","");
				contentList.add(resContent)
			}
			else
				contentList.add("")
			imagePathList.add(f[2])
			if(f[3])
				imageThumbnailPathList.add(f[3])
			else 
				imageThumbnailPathList.add(f[2])
			intensityList.add(f[4])
		}
		println "titleList___"+titleList
		def ret=[]
		def eg = EmotionGroup.get(Long.parseLong(params.emotionGroupNameId))
		ret.add("titleList":titleList, 
				"contentList":contentList, 
				"imagePathList":imagePathList, 
				"imageThumbnailPathList":imageThumbnailPathList, 
				"amount":titleList.size(),
				"red":eg.red,
				"green":eg.green,
				"blue":eg.blue,
				"density":eg.density,
				"restitution":eg.restitution,
				"friction":eg.friction,
				"intensityList":intensityList)
		render ret as JSON	
	}
	
	def getEmotionGroupColor(){
		
		def emotionGroupNameId= Integer.parseInt(params.emotionGroupNameId)
		def color=EmotionGroup.get(emotionGroupNameId).color
		render color
	}


    def emotionMap(){

//        session.facebookUser = User.get(1);
//        session.mapMode = "personal"

        // map mode
        if(params.mapMode!=null)
            session.mapMode = params.mapMode

        // Get emotion group
        def emotionGroup = []
        def eg = EmotionGroup.list()
        for(int i=0; i<eg.size(); i++){
            def emdescirption = ["name":"", "color":""]
            emdescirption.name = eg[i].name
            emdescirption.color = eg[i].color
            emotionGroup.push(emdescirption)
        }// end for


        if(session.mapMode != "personal"){

                // Get all emotion points
                def fd = Feed.createCriteria();
                def feeds = fd.list{
                    like("country", "taiwan")
                    isNotNull("city")
                    isNotNull("content")
                    ne("lat", "0")
                    ne("lng", "0")
                    order("publishTime", "desc")
                    maxResults(300)
                }
                //println feeds
                def flickrPoints = []
                def dateFormat =new SimpleDateFormat("yyyy-MM-dd");
                for(int i=0; i<feeds.size(); i++){

                    def theFeed = ["lat":0, "lng":0, "content":"", "imagePath":"", "publishTime":0,
                        "emotion":"", "intensity":0, "title":""]

                    // Save lat lng
                    theFeed.lat = Double.parseDouble(feeds[i].lat)
                    theFeed.lng = Double.parseDouble(feeds[i].lng)

                    // Save content, imagePath and publish time
                    if(feeds[i].content != null)
                        theFeed.content = feeds[i].content
                    if(feeds[i].imagePath != null)
                        theFeed.imagePath = feeds[i].imagePath
                    if(feeds[i].publishTime != 0)
                        theFeed.publishTime = dateFormat.format(feeds[i].publishTime)
                    if(feeds[i].title != null)
                        theFeed.title = feeds[i].title

                    //　Save emotion and intensity
                    def feedEmotions = FeedEmtionMapped.findAllByFeed(feeds[i])
                    //def feedEmotions = feeds[i].emotions.toArray()
                    //println feedEmotions
                    def feedEmotionName
                    def feedEmotionIntensity = 0
                    for(int j=0; j<feedEmotions.size(); j++){

                        //println feedEmotions[j].emotion.name
                        //println feedEmotions[j].emotion.intensity
                        if(j==0){
                            feedEmotionName = feedEmotions[j].emotion.emotionGroup.name
                            feedEmotionIntensity = feedEmotions[j].emotion.intensity
                        }else{
                            if(feedEmotions[j].intensity > feedEmotionIntensity){
                                feedEmotionName = feedEmotions[j].emotion.emotionGroup.name
                                feedEmotionIntensity = feedEmotions[j].emotion.intensity
                            }// end if
                        }// end if
                    }// end for

                    //println feedEmotionIntensity
                    theFeed.emotion = feedEmotionName
                    theFeed.intensity = feedEmotionIntensity

                    flickrPoints.push(theFeed)

                }// end for


                // Get all goverment points
                def govSenicPoints = GovermentTouristInfo.list()
                int theSize = govSenicPoints.size()
                def govPoints = []
                for(int i=0; i<theSize; i++){
                    def govInfo = ["name":"", "address":"", "description":"", "lat":"", "lng":"", "imageUrl1":"", "imageUrl2":"",
                        "imageUrl3":"", "positive":0, "negative":0]
                    if(govSenicPoints[i].name != null)
                        govInfo.name = govSenicPoints[i].name
                    if(govSenicPoints[i].address != null)
                        govInfo.address = govSenicPoints[i].address
                    if(govSenicPoints[i].description != null)
                        govInfo.description = govSenicPoints[i].description
                    if(govSenicPoints[i].lat != null)
                        govInfo.lat = govSenicPoints[i].lat
                    if(govSenicPoints[i].lng != null)
                        govInfo.lng = govSenicPoints[i].lng
                    if(govSenicPoints[i].imageUrl1 != null)
                        govInfo.imageUrl1 = govSenicPoints[i].imageUrl1
                    if(govSenicPoints[i].imageUrl2 != null)
                        govInfo.imageUrl2 = govSenicPoints[i].imageUrl2
                    if(govSenicPoints[i].imageUrl3 != null)
                        govInfo.imageUrl3 = govSenicPoints[i].imageUrl3

                    govInfo.positive = govSenicPoints[i].positive
                    govInfo.negative = govSenicPoints[i].negative
                    govPoints.push(govInfo)

                }// end for
                //println "government info: " + govPoints


                // Get happiness city points
                def emotionGroupHappyCity = []
                def happinessCities = []
                def hc = HappinessCity.list()
                int theNumber = hc.size()
                for(int i=0; i<hc.size(); i++){

                    def hcp = ["name":"", "country":"", "lat":"", "lng":"", "happy":"", "sad":"", "worried":"", "angry":"",
                        "disappoint":"", "peace":"", "nervous":"", "afraid":"", "touched":"", "hope":"", "love":"", "hate":"", "credit":""]
                    hcp.name = hc[i].name
                    hcp.country = hc[i].country
                    hcp.lat = hc[i].lat
                    hcp.lng = hc[i].lng
                    hcp.happy = hc[i].happy
                    hcp.sad = hc[i].sad
                    hcp.worried = hc[i].worried
                    hcp.angry = hc[i].angry
                    hcp.disappoint = hc[i].disappoint
                    hcp.peace = hc[i].peace
                    hcp.nervous = hc[i].nervous
                    hcp.afraid = hc[i].afraid
                    hcp.touched = hc[i].touched
                    hcp.hope = hc[i].hope
                    hcp.love = hc[i].love
                    hcp.hate = hc[i].hate

                    def credit = 0;
                    credit = hc[i].happy - hc[i].sad - hc[i].worried + hc[i].angry -hc[i].disappoint +
                        hc[i].peace -hc[i].nervous -hc[i].afraid + hc[i].touched + hc[i].hope + hc[i].love - hc[i].hate

                    hcp.credit = credit

                    happinessCities.push(hcp)

                }// end for

                def config = ConfigurationHolder.config
                def googleEarthKey = config.refreshbpm.googleEarthKey
                session.googleEarthKey = googleEarthKey

                [govPoints: govPoints as JSON, flickrPoints: flickrPoints as JSON, happinessCities:happinessCities as JSON,
                    emotionGroup: emotionGroup as JSON]

        }else{

            
                // Get all emotion points
                def fd = Feed.createCriteria();
                def feeds = fd.list{
                    //like("country", "taiwan")
                    //isNotNull("city")
                    //isNotNull("content")
                    ne("lat", "0")
                    ne("lng", "0")
                    eq("facebookUser", session.facebookUser)
                    order("publishTime", "desc")
                    maxResults(300)
                }
                //println feeds
                def flickrPoints = []
                def dateFormat =new SimpleDateFormat("yyyy-MM-dd");
                for(int i=0; i<feeds.size(); i++){

                    def theFeed = ["lat":0, "lng":0, "content":"", "imagePath":"", "publishTime":0,
                        "emotion":"", "intensity":0, "title":""]

                    // Save lat lng
                    theFeed.lat = Double.parseDouble(feeds[i].lat)
                    theFeed.lng = Double.parseDouble(feeds[i].lng)

                    // Save content, imagePath and publish time
                    if(feeds[i].content != null)
                        theFeed.content = feeds[i].content
                    if(feeds[i].imagePath != null)
                        theFeed.imagePath = feeds[i].imagePath
                    if(feeds[i].publishTime != 0)
                        theFeed.publishTime = dateFormat.format(feeds[i].publishTime)
                    if(feeds[i].title != null)
                        theFeed.title = feeds[i].title

                    //　Save emotion and intensity
                    theFeed.emotion = feeds[i].mostEmotion.name
                    theFeed.intensity = feeds[i].mostEmotion.intensity

                    flickrPoints.push(theFeed)

                }// end for

                [govPoints: null, flickrPoints: flickrPoints as JSON, happinessCities:null,
                    emotionGroup: emotionGroup as JSON]
                
        }// end if


    }

}
