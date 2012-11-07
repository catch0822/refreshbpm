package refreshbpm

class EmotionGroup {
	static hasMany = [emotions: Emotion]
	String name
	String color = "0xffffff"
	Float density = 1.0f
	Float friction = 1.0f
	Float restitution = 1.0f
	Integer red
	Integer blue
	Integer green
    static constraints = {
    	name(unique: true)
    }
}
