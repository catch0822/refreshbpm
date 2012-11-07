package refreshbpm

class EmotionDetectorService {

    static def emotionKeyWordList
    Emotion analyser(String content) {
        if(emotionKeyWordList==null){
            emotionKeyWordList = Emotion.findAllByIntensity(0)
            println " emotionKeyWordList = " + emotionKeyWordList.size()
        }
    	//def result = [:]

    	//Emotion.findAllByIntensity(0).each { e->
    	//Emotion.list().each{ e->
        def result
        emotionKeyWordList.each{ e-> 
    		if(content.indexOf(e.name)!=-1){
                result = e
    		}
    	}
        return result
    	//println 'content = ' + content
    	//println 'result = ' + result
    	//println '--------------------------'
    }
}
