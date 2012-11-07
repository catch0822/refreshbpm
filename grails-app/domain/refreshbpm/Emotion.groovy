package refreshbpm

class Emotion {
	static belongsTo = [emotionGroup: EmotionGroup]
    String name // The name of emotion, we have following emotions: "happy", "sad", "angry", ...
    int intensity = 0 // The level of emotion
    //static hasMany = [ feeds : Feed ]
    static constraints = {
    	name(unique: true)
    }
}
