package refreshbpm
import grails.converters.*
class PandoraController {


	/**
	shape : 0 = circle , 1 = rectage , 2 = triangle
	*/
    def index() { 
        def result = []
        def tmp
        //def m = 100
        def feed
        println " session.facebookUser " + (session.facebookUser!=null)
        println " session.mapMode " + (session.mapMode=="personal")
        Feed.createCriteria().list{
            if(session.mapMode=="personal"){
               eq('facebookUser',session.facebookUser)
               isNotNull('facebookUser')
            }
            order ('publishTime', "desc")
            isNotNull('mostEmotion')
            maxResults(100)
        }.each{
                println " feed " + it.facebookUser
                def me = it.mostEmotion
                def meg = me.emotionGroup
                //println "~~~ me.intensity = ${me.intensity}"
                tmp = [ title:it.title , intensity : me.intensity , color : meg.color ,  description : it.content ,
                            red : meg.red , green : meg.green , blue:meg.blue,
                            density : meg.density , friction : meg.friction , restitution : meg.restitution ,  
                            imgSrc : it.imagePath ,shape : 0 , thumbImg : it.imageThumbnailPath]
                result.add(tmp)
        }
/*
        println params
        //for(int i = 0 ; i < Feed.Count(); i++){
        	Feed.list(max:100 , sort: "publishTime", order: "asc").each{
				println " it = " + it
        		def me = it.mostEmotion
                if(me!=null){
        		    def meg = me.emotionGroup
            		//println "~~~ me.intensity = ${me.intensity}"
            		tmp = [ title:it.title , intensity : me.intensity , color : meg.color ,  description : it.content ,
                            red : meg.red , green : meg.green , blue:meg.blue,
            		        density : meg.density , friction : meg.friction , restitution : meg.restitution ,  
            		        imgSrc : it.imagePath ,shape : 0 , thumbImg : it.imageThumbnailPath]
            		result.add(tmp)
                }
        }
  */      
        return render(contentType:"application/json",text:result as JSON)
    }

}
