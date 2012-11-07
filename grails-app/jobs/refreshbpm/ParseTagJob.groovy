package refreshbpm



class ParseTagJob {
  static triggers = {
      simple repeatInterval: 90*1000 
    }

    def parserService

    def execute() {
    	println "execute job"
     	parserService.parseFlickrNoGeoLimited()
     	
    	//parserService.flickrByTagSearch2()
    }
}
