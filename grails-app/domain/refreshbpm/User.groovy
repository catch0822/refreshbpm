package refreshbpm

class User {
	
    String accessToken
    String facebookUid

    static hasMany = [feeds: Feed]

    static constraints = {
    }
}
