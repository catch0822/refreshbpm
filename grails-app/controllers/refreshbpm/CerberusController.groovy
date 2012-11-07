package refreshbpm



class CerberusController {

    def parserService

    def index() { }

    

    // This is flickr public info parsing interface (don't put other parsing here)
    def flickr(){

    	parserService.flickr()
        
    }
    /*
    {"query":{"count":3,"created":"2012-10-06T03:01:03Z","lang":"en-US","results":{"
    photo":[{"farm":"9","id":"8058341538","isfamily":"0","isfriend":"0","ispublic":"
    1","owner":"86402790@N02","secret":"36aeac46b3","server":"8454","title":"IMG_112
    9"},{"farm":"9","id":"8058341542","isfamily":"0","isfriend":"0","ispublic":"1","
    owner":"37578728@N00","secret":"a2fd40310f","server":"8176","title":"DSC03350"},
    {"farm":"9","id":"8058341604","isfamily":"0","isfriend":"0","ispublic":"1","owne
    r":"71064477@N05","secret":"36f007098b","server":"8042","title":"嚗??鳴??
    ??潘?嚗??銨嚗??怒嚗?}]}}}
     */
  


}
