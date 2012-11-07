<!doctype html>

<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Refresh Bpm</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'css/images', file: 'logo-title.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">

                <!-- Path navigator -->
                <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery.min.js')}"></script>
                <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery-ui-1.8.20.custom.min.js')}"/></script>
                <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery.menu.js')}"/></script>
                <link rel="stylesheet" href="${resource(dir: 'path/css', file: 'main.css')}"/>
                <link rel="stylesheet" href="${resource(dir: 'path/css', file: 'piemenu.css')}"/>
                <!-- color bar add by Wei -->
				<link rel="stylesheet" href="${resource(dir: 'css', file: 'colorBar.css')}" type="text/css">
                <!-- Qtip -->
                <!-- <g:javascript src="jquery/jquery.qtip-1.0.0-rc3.min.js"/> -->

                <!-- style -->
                <link rel="stylesheet" href="${resource(dir:'css', file:'style.css')}" type="text/css" media="screen" />
                <link href="http://fonts.googleapis.com/css?family=Dancing+Script|Open+Sans+Condensed:300" rel="stylesheet" type="text/css" />

                <!-- Facebook lib -->
                <script type="text/javascript" src='http://connect.facebook.net/en_US/all.js'></script>
                <g:javascript src="facebook.js"/>
              

		<g:layoutHead/>
                <r:layoutResources />
	</head>
	<body>
			<!-- facebook app id -->
		    <div id="facebookAppIdInfo" style="display:none;">${grailsApplication.config.refreshbpm.facebook_appId}</div>
		    
			<div id="framecontentLeft">
				<div class="innertube">
					<a id="logo">
	  					<img src="${createLinkTo(dir:'css/images',file:'logo.png')}" alt="RefreshBpm" style="width:340px;height:170px;margin-top:12px;"/>
					</a>
				</div>	
						
				<div class="divider"></div>
				
				<div class="left_menu" id="menu-wrapper">
                    <div id="menu" class="container">
                            <ul>
                                 <li><a href="/refreshbpm/contactf5/about">About Refresh Bpm&nbsp&nbsp<img src="${createLinkTo(dir:'css/images',file:'img10.png')}"></a>
                                 <a href="/refreshbpm/contactf5/contactus">Contact Us&nbsp&nbsp<img src="${createLinkTo(dir:'css/images',file:'img10.png')}"></a></li>     
                            </ul>
                    </div>
            	</div>
				
				<div class="left_bottom">
				   <div>
					 <g:if test="${session.facebookUser == null}">
                     	<a href="#"><img style="cursor:pointer;margin-top:20px;float:right;" id="facebook" alt="facebook login" src="${resource(dir:'images', file:'facebook-login.png')}" /></a>
                     </g:if>
                     <g:else>
                     	<a href="#"><img style="cursor:pointer;margin-top:20px;float:right;" id="facebookLogout" alt="facebook Logout" src="${resource(dir:'images', file:'facebook-logout.jpg')}"/></a>
                     </g:else>
                     </div>	
				</div>
			</div>
			
            <g:layoutBody/>            
            <r:layoutResources />
	</body>
</html>