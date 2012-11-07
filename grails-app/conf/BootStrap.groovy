import java.text.SimpleDateFormat
import refreshbpm.*

class BootStrap {

    def init = { servletContext ->

/* Happiness city taiwan */
if(HappinessCity.count == 0){
   new HappinessCity(name:'taipei', country:'taiwan', lat:24.983568, lng:121.545868).save(flush:true)
   new HappinessCity(name:'keelung', country:'taiwan', lat:25.111715, lng:121.742249).save(flush:true)
   new HappinessCity(name:'taoyuan', country:'taiwan', lat:24.99726, lng:121.276703).save(flush:true)
   new HappinessCity(name:'hsinchu', country:'taiwan', lat:24.750573, lng:121.083069).save(flush:true)
   new HappinessCity(name:'miaoli', country:'taiwan', lat:24.574602, lng:120.882568).save(flush:true)
   new HappinessCity(name:'taichung', country:'taiwan', lat:24.225676, lng:120.771332).save(flush:true)
   new HappinessCity(name:'changhua', country:'taiwan', lat:23.951116, lng:120.412903).save(flush:true)
   new HappinessCity(name:'yunlin', country:'taiwan', lat:23.742612, lng:120.374451).save(flush:true)
   new HappinessCity(name:'chiayi', country:'taiwan', lat:23.488439, lng:120.484314).save(flush:true)
   new HappinessCity(name:'tainan', country:'taiwan', lat:23.137834, lng:120.30304).save(flush:true)
   new HappinessCity(name:'kaohsiung', country:'taiwan', lat:22.962187, lng:120.565338).save(flush:true)
   new HappinessCity(name:'pingtung', country:'taiwan', lat:22.545538, lng:120.585938).save(flush:true)
   new HappinessCity(name:'yilan', country:'taiwan', lat:24.65201, lng:121.698303).save(flush:true)
   new HappinessCity(name:'hualien', country:'taiwan', lat:23.644524, lng:121.434631).save(flush:true)
   new HappinessCity(name:'taitung', country:'taiwan', lat:22.806567, lng:121.014404).save(flush:true)
   new HappinessCity(name:'nantou', country:'taiwan', lat:23.787858, lng:120.989685).save(flush:true)
}// end if



    
/*
    g01 高興 oragnge
    go2 悲傷 deep blue
    g03 憂愁 暗紫
    g04 憤怒 紅
    g05 失望 青綠
    g06 平靜 灰色
    g07慌張 綠
    g08害怕 藍綠
    g09感動 橙色 葡萄柚
    g10 希望 太陽色
    g11 愛 桃紅色
    g12 恨 黑色
 */
    	if(EmotionGroup.count==0){
            new EmotionGroup(name:'高興', color:'#FFCC33', red:255 , green:204, blue:51).save(flush:true)
            new EmotionGroup(name:'悲傷', color:'#1c4aa8', red:28 , green:74, blue:168).save(flush:true)
            new EmotionGroup(name:'憂愁', color:'#663399', red:102 , green:51, blue:153).save(flush:true)
            new EmotionGroup(name:'憤怒', color:'#de2121', red:222 , green:33, blue:33).save(flush:true)
            new EmotionGroup(name:'失望', color:'#b4cc66', red:180 , green:204, blue:102).save(flush:true)
            new EmotionGroup(name:'平靜', color:'#E0E0E0', red:224 , green:224, blue:224).save(flush:true)
            new EmotionGroup(name:'慌張', color:'#0cad34', red:12 , green:173, blue:52).save(flush:true)
            new EmotionGroup(name:'害怕', color:'#33ccb8', red:51 , green:204, blue:184).save(flush:true)
            new EmotionGroup(name:'感動', color:'#FF9933', red:255 , green:153, blue:51).save(flush:true)
            new EmotionGroup(name:'希望', color:'#f5721b', red:245 , green:114, blue:27).save(flush:true)
            new EmotionGroup(name:'愛', color:'#FF3399', red:255 , green:51, blue:153).save(flush:true)
            new EmotionGroup(name:'恨', color:'#000000', red:0 , green:0, blue:0).save(flush:true)
        }
		if(Emotion.count==0){
/**
第一級:
[x]稍
稍微
有些
有點
[x]一點點

第二級:
好
十分
[x]分外
格外
過於
更
[x]更加

第三級:
太
很
相當
超
[x]特
特別

第四級:
最
超級
非常
[x]極
極度
[x]極其
無敵
*/
			def level =["":0,
						/*"稍" : 1 ,*/"稍微" : 1,"有些":1,"有點" : 1,/*"一點點" :1 ,*/
						"好":2,"十分":2,/*"分外":2,*/"格外":2,"過於":2,"更":2/*,"更加":2*/,
						"太":3,"很":3,"相當":3,"超":3,/*"特":3,*/"特別":3,
						"最":4,"超級":4,"非常":4,/*"極":4,*/"極度":4,/*"極其":4,*/"無敵":4]

			def g01 = ["爽","哈哈","ㄏㄏ","西西","嘻嘻","高興","開心","愉快","快活","快樂","歡樂","歡快","歡娛","歡愉","歡欣","歡喜","欣喜","欣然","怡然","愉悅","愉愉","喜滋滋","喜洋洋","狂喜","歡躍","欣幸","喜幸","慶幸"]
			

			def g02 =["QQ","哭哭","不高興","不開心","不快樂","不歡樂","不愉快","不歡喜","悲傷","傷悲","傷心","傷感","難過","難受","哀傷","悲哀","悲愁","哀愁","憂傷","愴然","悲痛","哀痛","沈痛","痛心","悲憤","痛切","腸斷","痛苦","慘痛","傷痛","悲苦","悲辛","辛酸","心酸","淒慘"]
			
			def g3 =["哀哀","憂愁","發愁","犯愁","憂勞成疾","憂傷","憂戚","憂思","憂慮","焦慮","憂患","擔憂","憂懼","憂悶","愁悶","愁苦","憂鬱","抑鬱","憂憤","煩悶","煩心","煩亂","煩躁","煩惱","煩擾","憂煩","憂悶","沈悶","苦悶","鬱悶","愁悶","憋悶"]
		
			def g4 = ["不爽","發火","憤怒","惱怒","氣惱","氣憤","憤激","憤慨","憤然","忿忿","憤憤","慪氣","動肝火","惱火","冒火","光火","發火","上火","動怒","發怒","生氣","動氣","眼紅","發飆","發脾氣","鬧脾氣"]
	
			def g5 = ["灰心","喪氣","洩氣","氣餒","再接再厲","絕不自餒","心涼了","沮喪","消沈","低沈","黯然","感傷","精神頹喪","頹廢","頹敗","頹靡","失望","大失所望","掃興","殺風景","可惜","好衰","絕望","心寒","繼續加油","心灰意冷","沒興趣","沒興致"]
	
			def g6 =["鎮靜","沈著","心靜","平靜","安靜","寧靜","恬靜","坦然","安然","平心靜氣","心平氣和","淡定","釋懷","寂靜","不緊張","好險"]
		
			def g7 =["慌張","心慌","驚慌","驚惶","失措","慌亂","手忙腳亂","不知所措","驚魂未定","慌了","完蛋","怎麼辦","挫賽"]
		
			def g8 =["害怕","懼怕","畏懼","恐怖","膽寒","心寒","喪膽","毛骨悚然","提心吊膽","憂懼","心有餘悸","驚恐","惶恐","失措","受驚","吃驚","退避","怕生","怯場","恐怕"]
		
			def g9 =["興奮","感動","觸動","動心","觸景生情","感激","感同身受","感恩","感謝"]

			def g10=["希望","盼望","期望","仰望","期許","期待","渴望","巴不得","嚮往","祈求","期求","希冀","求得","苛求","謀生"]

			def g11 =["喜歡","喜愛","心愛","鍾愛","熱愛","熱戀","戀愛","溺愛","寵愛","寵倖","得寵","慣養","鍾情","相愛"]

			def g12 =["討厭","厭惡","憎惡","痛惡","厭煩","膩味","厭倦","恨","惱怒","仇視","憎恨","憎惡"]

			saveEmotions(g01,1,level)
			saveEmotions(g02,2,level)
			saveEmotions(g3 ,3,level)
			saveEmotions(g4 ,4,level)
			saveEmotions(g5 ,5,level)
			saveEmotions(g6 ,6,level)
			saveEmotions(g7 ,7,level)
			saveEmotions(g8 ,8,level)
			saveEmotions(g9 ,9,level)
			saveEmotions(g10,10,level)
			saveEmotions(g11,11,level)
			saveEmotions(g12,12,level)
		}
    }

    private void saveEmotions(def datalist , int gid , def level ){
    	def count = 0
    	datalist.each{ 
			level.each{ l->
				Emotion e = new Emotion(name:l.key+it,intensity:l.value,emotionGroup:EmotionGroup.get(gid))
				e.save()
				EmotionGroup.get(gid).addToEmotions(e)
				count++
			}
		}
		println "save Emotions : ${count}"
    }
    def destroy = {
    }
}
