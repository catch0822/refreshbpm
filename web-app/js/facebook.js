var accessToken,    // Facebook access token
facebookUid

// Facebook icon event binding and dialog prepare
$(document).ready(function(){

    var facebookAppId = jQuery("#facebookAppIdInfo").html()
    console.log(facebookAppId)

    // Init Facebook SDK
    FB.init({
        appId  : facebookAppId, // Facebook app ID
        status : true, // Check login status
        cookie : true, // Enable cookies to allow the server to access the session
        xfbml  : true  // Parse XFBML
    });

    // Bind click event on facebook icon
    jQuery("#facebook").click(loginFB);

    // Bind facebook logout
    jQuery("#facebookLogout").click(logoutFB);

})


// Login facebook and get access token of oauth
function loginFB() {
    FB.login(function(response) {
        if (response.authResponse) {
            accessToken = response.authResponse.accessToken;
            facebookUid = response.authResponse.userID;
            console.log(accessToken)
            console.log(facebookUid)
            var url = "/refreshbpm/facebookAccess/accessTokenStore?",
            params = "accessToken=" + accessToken + "&" + "facebookUid=" + facebookUid
            jQuery.get(url, params, function(result){
                console.log(result)
                if(result == "ok"){
                    window.location = "/refreshbpm/publicfeed/"
                }
            })
            //doPost2() // Implementation2: eidt and post info by FB.ui
        }
    },{
        scope:'email,user_about_me,read_stream,user_checkins,manage_pages,publish_actions,user_photos,publish_stream'
    });
}


// Logout facebook
function logoutFB(){
    FB.logout(function(response) {
      console.log(response)
      window.location = "/refreshbpm/facebookAccess/logout"
    });
}