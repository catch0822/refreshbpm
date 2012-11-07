package refreshbpm

class TwitterJob {

	def twitterService
    static triggers = {
     	//simple repeatInterval: 10*1000 
    }

    def execute() {
        twitterService.getRecentTweet()
    }
}
