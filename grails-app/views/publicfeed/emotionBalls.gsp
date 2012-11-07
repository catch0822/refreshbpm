<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="refreshbpm.EmotionGroup"%> 
<html xmlns="http://www.w3.org/1999/xhtml"/>
<html>
  <head>
    <meta name="layout" content="main"/>    
    <g:javascript src='prototype-1.6.0.2.js'/>
        <!-- box2djs--> 
    <g:javascript src='box2d/common/b2Settings.js'/>
    <g:javascript src='box2d/common/math/b2Vec2.js'/>
    <g:javascript src='box2d/common/math/b2Mat22.js'/>
    <g:javascript src='box2d/common/math/b2Math.js'/>
    <g:javascript src='box2d/collision/b2AABB.js'/>
    <g:javascript src='box2d/collision/b2Bound.js'/>
    <g:javascript src='box2d/collision/b2BoundValues.js'/>
    <g:javascript src='box2d/collision/b2Pair.js'/>
    <g:javascript src='box2d/collision/b2PairCallback.js'/>
    <g:javascript src='box2d/collision/b2BufferedPair.js'/>
    <g:javascript src='box2d/collision/b2PairManager.js'/>
    <g:javascript src='box2d/collision/b2BroadPhase.js'/>
    <g:javascript src='box2d/collision/b2Collision.js'/>
    <g:javascript src='box2d/collision/Features.js'/>
    <g:javascript src='box2d/collision/b2ContactID.js'/>
    <g:javascript src='box2d/collision/b2ContactPoint.js'/>
    <g:javascript src='box2d/collision/b2Distance.js'/>
    <g:javascript src='box2d/collision/b2Manifold.js'/>
    <g:javascript src='box2d/collision/b2OBB.js'/>
    <g:javascript src='box2d/collision/b2Proxy.js'/>
    <g:javascript src='box2d/collision/ClipVertex.js'/>
    <g:javascript src='box2d/collision/shapes/b2Shape.js'/>
    <g:javascript src='box2d/collision/shapes/b2ShapeDef.js'/>
    <g:javascript src='box2d/collision/shapes/b2BoxDef.js'/>
    <g:javascript src='box2d/collision/shapes/b2CircleDef.js'/>
    <g:javascript src='box2d/collision/shapes/b2CircleShape.js'/>
    <g:javascript src='box2d/collision/shapes/b2MassData.js'/>
    <g:javascript src='box2d/collision/shapes/b2PolyDef.js'/>
    <g:javascript src='box2d/collision/shapes/b2PolyShape.js'/>
    <g:javascript src='box2d/dynamics/b2Body.js'/>
    <g:javascript src='box2d/dynamics/b2BodyDef.js'/>
    <g:javascript src='box2d/dynamics/b2CollisionFilter.js'/>
    <g:javascript src='box2d/dynamics/b2Island.js'/>
    <g:javascript src='box2d/dynamics/b2TimeStep.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2ContactNode.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2Contact.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2ContactConstraint.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2ContactConstraintPoint.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2ContactRegister.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2ContactSolver.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2CircleContact.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2Conservative.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2NullContact.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2PolyAndCircleContact.js'/>
    <g:javascript src='box2d/dynamics/contacts/b2PolyContact.js'/>
    <g:javascript src='box2d/dynamics/b2ContactManager.js'/>
    <g:javascript src='box2d/dynamics/b2World.js'/>
    <g:javascript src='box2d/dynamics/b2WorldListener.js'/>
    <g:javascript src='box2d/dynamics/joints/b2JointNode.js'/>
    <g:javascript src='box2d/dynamics/joints/b2Joint.js'/>
    <g:javascript src='box2d/dynamics/joints/b2JointDef.js'/>
    <g:javascript src='box2d/dynamics/joints/b2DistanceJoint.js'/>
    <g:javascript src='box2d/dynamics/joints/b2DistanceJointDef.js'/>
    <g:javascript src='box2d/dynamics/joints/b2Jacobian.js'/>
    <g:javascript src='box2d/dynamics/joints/b2GearJoint.js'/>
    <g:javascript src='box2d/dynamics/joints/b2GearJointDef.js'/>
    <g:javascript src='box2d/dynamics/joints/b2MouseJoint.js'/>
    <g:javascript src='box2d/dynamics/joints/b2MouseJointDef.js'/>
    <g:javascript src='box2d/dynamics/joints/b2PrismaticJoint.js'/>
    <g:javascript src='box2d/dynamics/joints/b2PrismaticJointDef.js'/>
    <g:javascript src='box2d/dynamics/joints/b2PulleyJoint.js'/>
    <g:javascript src='box2d/dynamics/joints/b2PulleyJointDef.js'/>
    <g:javascript src='box2d/dynamics/joints/b2RevoluteJoint.js'/>
    <g:javascript src='box2d/dynamics/joints/b2RevoluteJointDef.js'/>
        <!--=============================-->
        <!-- Copy this part to your app. -->
        <!-- END                         -->
        <!--=============================-->

    <script type="text/javascript">
      jQuery.noConflict();
      </script>              
  </head>
  <body>

    <!-- facebook app id -->
    <div id="facebookAppIdInfo" style="display:none;">${grailsApplication.config.refreshbpm.facebook_appId}</div>
    
    <!-- body layout -->
    <div id="outwrapper">

        <!-- Path Navigator -->
  <g:javascript src="emotionChart/jquery-1.7.2.min.js"/> 
  <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery-ui-1.8.20.custom.min.js')}"/></script>
  <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery.menu.js')}"/></script>
  <link rel="stylesheet" href="${resource(dir: 'path/css', file: 'main.css')}"/>
  <link rel="stylesheet" href="${resource(dir: 'path/css', file: 'piemenu.css')}"/>
  <script type="text/javascript">
     // Path Navigator effect

        function PieMenuInit(){
        	jQuery('#outer_container').PieMenu({
                'starting_angel':320,
                'angel_difference' : 90,
                'radius': 100
            });
        }

        function reset2(){
            if(jQuery(".menu_button").hasClass('btn-rotate'))
            	jQuery(".menu_button").trigger('click');

            jQuery("#info").fadeIn("slow").fadeOut("slow");
            PieMenuInit();
        }


        jQuery(function(){
            
            // Path navigator control
            jQuery("#submit_button").click(function() {
                reset2();
            });
            jQuery( "#outer_container" ).draggable();
            PieMenuInit();

        });

        function changeEmotion(emotionGroupNameId){
        	jQuery('#canvas').html("<img id=\"loadingImg\" src=\"../images/emotionGroup/loading.gif\" style=\"display: block; margin-left: auto; margin-right: auto;\"/>")
			  jQuery.ajax({
                                  url: "getRandomFeed?howMany=100&emotionGroupNameId="+emotionGroupNameId,
                                  type: "GET",
                                  dataType: "json",
                                  success: function(Jdata) {
                                    reset();
                                    addEmotions2(Jdata[0]);
                                    jQuery('#loadingImg').hide()
                                  },
                                  error: function() {
                                	jQuery('#loadingImg').hide()
                                    //alert("ERROR!!!");
                                  }
                        });
        }
	

function addEmotions2(json){
    //alert(json.intensity);
    for( i = 0; i < json.amount; i++ ) {
        var x = (Math.floor((Math.random()*400)+700));
        var y = (Math.floor((Math.random()*400)+300));
        var vy = getRandomV() ;
        var vx = getRandomV() ;
        var j = {
            title:json.titleList[i],
            description:json.contentList[i],
            thumbImg:json.imageThumbnailPathList[i],
            imgSrc:json.imagePathList[i]
        };
        //console.log(" add emotion : " + i + " json " + j);
        var color = "rgba("+json.red+", "+json.green+", "+json.blue+", 0.8)";

        _createBall(x,y,
                j , color, (json.intensityList[i]*10)+20 , json.density, json.restitution , json.friction , vx , vy );
    }
}
        </script>
        <div id='outer_container' class="outer_container" >
                <a class="menu_button" href="#" title="Toggle"><span></span></a>
                <ul class="menu_option">
                  <li id="emotionballsItemOuter"><g:link controller="publicfeed" action="emotionBalls" class="selected"><span id="emotionballsItem" title="show emotion balls">emotionballs</span></g:link></li>
                  <li id="emotionchartItemOuter" ><g:link controller="publicfeed" action="emotionChart"><span id="emotionchartItem" title="show emotion chart">linechart</span></g:link></li>
                  <li id="emotionmapItemOuter"><g:link controller="publicfeed" action="emotionMap"><span id="emotionmapItem" title="show emotion map">map</span></g:link></li>
               	   <g:if test="${session.facebookUser}">
    			  	  <g:if test="${session.mapMode=="personal"}"> 	
    			    		 <li><g:link controller="publicfeed" action="emotionBalls" params="['mode':'global']"><span id="emotionglobalItem" title="global">global</span></g:link></li>		
    			      </g:if>
    			      <g:else>
    			      		 <li><g:link controller="publicfeed" action="emotionBalls" params="['mode':'personal']"><span id="emotionpersonalItem" title="personal">personal</span></g:link></li>
    			      </g:else>
    			 
				  </g:if>
                </ul>
        </div>
                
      <g:javascript src="facebook.js"/>
     <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery.smooth-scroll.min.js')}"/></script>
     <script type="text/javascript" src="${resource(dir: 'path/js', file: 'lightbox.js')}"/></script>
     <g:javascript src="jquery/jquery.qtip-1.0.0-rc3.min.js"/>
     <link rel="stylesheet" href="${resource(dir: 'path/css', file: 'lightbox.css')}"/>     
     
     
     		<div id="maincontent">
			<div class="innertube">
				<br/>
        
            	<div id="page" class="container" style="width:1024px">		
                	<div style="width:95%;height:920px;">
	                	<div id="colorBar"> 
	                		<ul>
								<g:each var="i" in="${ (1..<13) }">
	   								<li><a onclick='changeEmotion("${EmotionGroup.get(i)?.id}")'><span>${EmotionGroup.get(i)?.name}</span><img src="${createLinkTo(dir:'images/emotionGroup',file:i+'.png')}"/></a></li>
	 							</g:each>
							</ul>
	                	</div>
                	      
					        <div id="mainContnet" style="width:95%;height:700px;">
			                	<div id="canvas" name="canvas"></div>
			                    <g:javascript src='baseGameMain.js'/>
			                    <g:javascript src='game2.js'/>
		                	 </div>					 
             		 </div>
      		   </div>         	
			</div>
		</div>
		</div>
		
		<div id="framecontentBottom">
			<div class="innertube">
		 			<div style="width:180px;height:130px;margin-top:12px;left:0px;positive:fixed;float:left">
	  					<img src="${createLinkTo(dir:'css/images',file:'logo.png')}" alt="RefreshBpm"/>
	  				</div>
	  					<div>
	  						<br/>
	  						<br/>
	  						<br/>
	  							<i><font style="color:#4C4C4C;float:left;font-family:Century Gothic;font-size:22px;">Bring happiness to others,then you will also be happy in return.</font></i><br/>
	  						<!--  <b><font style="float:left;">情緒是人類最美麗的語言，共鳴來自於感同身受的情緒。</font></b><br/>
	  						<b><font style="float:left;">正所謂凡走過必留下痕跡，在情緒這方面當然也是如此。</font></b><br/>
	  						<b><font style="float:left;">隨著時間的腳步回頭看，</font></b><br/>
	  						<b><font style="float:left;">我們留住你每一個深刻的感動以及最微小的幸福。</font></b><br/> -->
	  					</div>
			</div>
		</div>
  </body>
</html>
