<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="refreshbpm.EmotionGroup"%> 
<html xmlns="http://www.w3.org/1999/xhtml"/>
<html>
  <head>
    <meta name="layout" content="main"/>

    <!-- google map and chart lib -->
    <script type="text/javascript" src="https://www.google.com/jsapi?key=${session.googleEarthKey}"></script>

    <!-- google earth color parser -->
    <script type="text/javascript" src="http://earth-api-utility-library.googlecode.com/svn/trunk/extensions/dist/extensions.js"></script>
    <g:javascript src="emotionmap/colorParserGoogleEarth.js"/>

    <!-- map control -->
    <g:if test="${session.mapMode == 'personal'}">
      <script type="text/javascript">
        var flickrPointsInfo = ${flickrPoints};
        var emotionGroup = ${emotionGroup};
        for(var i=0;i<emotionGroup.length;i++){
          emotionGroup[i].color = GEarthExtensions.prototype.util.parseColor(emotionGroup[i].color, 0.4)
        }// end for

  //      console.log(flickrPointsInfo)
  //      console.log(govPointsInfo)
  //      console.log(happinessCities)
  //      console.log(emotionGroup)
      </script>
    </g:if>
    <g:else>
      <script type="text/javascript">
        var flickrPointsInfo = ${flickrPoints};
        var govPointsInfo = ${govPoints};
        var happinessCities = ${happinessCities};
        var emotionGroup = ${emotionGroup};
        for(var i=0;i<emotionGroup.length;i++){
          emotionGroup[i].color = GEarthExtensions.prototype.util.parseColor(emotionGroup[i].color, 0.4);
        }// end for

  //      console.log(flickrPointsInfo)
  //      console.log(govPointsInfo)
  //      console.log(happinessCities)
  //      console.log(emotionGroup)
      </script>
    </g:else>
    <g:if test="${session.mapMode == 'personal'}">
      <g:javascript src="emotionmap/mapPersonal.js"/>
    </g:if>
    <g:else>
      <g:javascript src="emotionmap/map.js"/>
    </g:else>



    <style type="text/css">
      /* for flickr*/
      .popWrapper{
        width:400px;
        text-transform: lowercase;
        font: normal 11px Arial, Helvetica, sans-serif;
        color: #5D781D;
        background-color: #F0F0F0;
      }
      .popWrapper .popupTime{
        width: 380px;
        height:15px;
      }
      .popWrapper .popupContent{
        width: 380px;
      }
      /* for happiness city*/
      .popWrapper2{
        width:250px;
        text-transform: lowercase;
        font: normal 11px Arial, Helvetica, sans-serif;
        font-size: 15px;
        color: #5D781D;
        background-color: #F0F0F0;
      }
      .happyPercentage{
        text-align: right;
        margin-right: 20px;
        display: inline;
      }
      /* for gov scenic point*/
      .popWrapper3{
        width:420px;
        text-transform: lowercase;
        font: normal 11px Arial, Helvetica, sans-serif;
        font-size: 15px;
        color: #5D781D;
        background-color: #F0F0F0;
      }
      .popWrapper3 .popupTime{
        width: 400px;
        height:15px;
      }
      .popWrapper3 .popupContent{
        width: 400px;
      }
      .positive, .negative{
        display:inline;
        margin-left:10px;
        margin-right:20px;
      }
      .happy{
        color: #FFCC33;
        text-align: center;
      }
      .sad{
        color: #1c4aa8;
        text-align: center;
      }
      .worried{
        color: #663399;
        text-align: center;
      }
      .angry{
        color: #de2121;
        text-align: center;
      }
      .disappoint{
        color: #b4cc66;
        text-align: center;
      }
      .peace{
        color: #E0E0E0;
        text-align: center;
      }
      .nervous{
        color: #0cad34;
        text-align: center;
      }
      .afraid{
        color: #33ccb8;
        text-align: center;
      }
      .touched{
        color: #FF9933;
        text-align: center;
      }
      .hope{
        color: #f5721b;
        text-align: center;
      }
      .love{
        color: #FF3399;
        text-align: center;
      }
      .hate{
        color: #000000;
        text-align: center;
      }


      /* Button effect */
      #menu-css li {
        display: inline;
        list-style: none;
      }

      #menu-css li a {

        /* Border Radius */
        -webkit-border-radius: 15px;
        -moz-border-radius: 15px;
        border-radius: 15px;

        /* Border Shadow */
        -webkit-box-shadow: 1px 2px 2px rgba(0,0,0,0.6);
        -moz-box-shadow: 1px 2px 2px rgba(0,0,0,0.6);
        box-shadow: 1px 2px 2px rgba(0,0,0,0.6);

        color: #ffffff;
        background: rgba(0,0,0,0.2);
        display: inline-block;
        padding: 5px 15px;
        outline: none;
        text-decoration: none;
        width: 160px;
        text-align: center;
      }
      #menu-css li a:hover {
        background: rgba(0,0,0,0.5);
        padding: 5px 25px;
      }

      #menu-css li a:active {
        background: rgba(0,0,0,0.1);
        -webkit-box-shadow: 1px 1px 1px rgba(0,0,0,0.4);
        -moz-box-shadow: 1px 1px 1px rgba(0,0,0,0.4);
        box-shadow: 1px 1px 1px rgba(0,0,0,0.4);
      }
      #menu-css li a {

        /* Animation (Webkit, Gecko & Mozilla) */
        -webkit-transition-duration: 0.20s;
        -webkit-transition-timing-function: ease-out;
        -moz-transition-duration: 0.20s;
        -moz-transition-timing-function: ease-out;
      }

    </style>

  </head>
  <body>

    <!-- Path Navigator -->
    <script type="text/javascript" src="${resource(dir: 'path', file: 'control.js')}"/></script>
    <div id='outer_container' class="outer_container" >
      <a class="menu_button" href="#" title="Toggle"><span></span></a>
      <ul class="menu_option">
        <li id="emotionballsItemOuter"><g:link controller="publicfeed" action="emotionBalls"><span id="emotionballsItem" title="show emotion balls">emotionballs</span></g:link></li>
        <li id="emotionchartItemOuter" ><g:link controller="publicfeed" action="emotionChart"><span id="emotionchartItem" title="show emotion chart">linechart</span></g:link></li>
        <li id="emotionmapItemOuter"><g:link controller="publicfeed" action="emotionMap" class="selected"><span id="emotionmapItem" title="show emotion map">map</span></g:link></li>               
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

    <div id="maincontent">
      <div class="innertube">
      <br/>
      <div id="page" class="container" style="width:1024px;">
        
        <div style="width:95%;height:920px;">
        
        <!-- emotion selector -->
        <div id="colorBar" style="padding-left:10px;">
          <ul>
            <g:each var="i" in="${ (1..<13) }">
              <li><a id="emotionBar${i}"><span>${EmotionGroup.get(i)?.name}</span><img src="${createLinkTo(dir:'images/emotionGroup',file:i+'.png')}"/></a></li>
            </g:each>
          </ul>
        </div>
        <!-- Map content -->
        <g:if test="${session.mapMode != 'personal'}">
          <ul id="menu-css">
            <center>
              <li><a href="#" id="footprint">Footprint</a></li>
              <li><a href="#" id="happinessCity">Happiness City</a></li>
              <li><a href="#" id="senicPointsEvaluation">Happiness spot</a></li>
            </center>
          </ul>
        </g:if>
        <div id="map_convas" style="width:100%;height:800px;"></div>
      
      </div>  
      
      </div>
      </div>
    </div>

  </body>
</html>
