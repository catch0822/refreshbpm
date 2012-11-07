package refreshbpm

class SocialMedia {

    String name // The name of social media
    String path // The path of social media

    static constraints = {
    	name(unique: true)
        path(unique: true)
    }
}
