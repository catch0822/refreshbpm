package refreshbpm
import grails.converters.JSON
class LockiController {

    def treemap() {}

    def treemapsvg(){}

    def thor(){
    	def result = [name:"emotions",children:[]]
    	EmotionGroup.list().each{ eg->
    		def childs = []
    		eg.emotions.each{ e->
    			int c = Feed.countByMostEmotion(e)
    			if(c>10)
    				childs.add([name:e.name,size:c])
    		}
    		if(childs.size()>0)
    			result.children.add([name:eg.name,children:childs])
    	}
    	render result as JSON
    }
}
