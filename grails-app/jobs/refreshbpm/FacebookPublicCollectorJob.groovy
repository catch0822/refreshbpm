package refreshbpm



class FacebookPublicCollectorJob {

	def facebookParserService
    static triggers = {
      simple repeatInterval: 90*1000 
    }

    def execute() {
        facebookParserService.getPublicPost()
    }
}
