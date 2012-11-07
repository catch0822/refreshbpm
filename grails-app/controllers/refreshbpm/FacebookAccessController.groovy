package refreshbpm

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import grails.converters.*
import java.text.SimpleDateFormat
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder


class FacebookAccessController {

    def facebookParserService

    def accessTokenStore() {
        println params;
        if(params.accessToken != null && params.facebookUid != null){

            def user = User.findByFacebookUid(params.facebookUid);
            if(user != null){
                user.accessToken = params.accessToken
				user.save(flush:true)
				facebookParserService.getUserInfoOnce(user);
            }else{
                user = new User();
                user.accessToken = params.accessToken
                user.facebookUid = params.facebookUid
				user.save(flush:true)
				facebookParserService.getUserInfo(user);
            }//end if
            
            session.facebookUser = user
            session.mapMode = "personal" //all, personal
            
            
            render "ok"
            
        }// end if
    }

    def logout = {
        println session.facebookUser
        session.facebookUser = null;
        session.mapMode = null;
        redirect(controller:"publicfeed", action:"emotionBalls")
    }


}
