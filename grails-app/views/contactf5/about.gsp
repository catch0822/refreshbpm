<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="refreshbpm.EmotionGroup"%> 
<html xmlns="http://www.w3.org/1999/xhtml"/>
<html>
  <head>
    <meta name="layout" content="main"/>
    <style type="text/css">
      .personalInfo{
          width:1000px;
      }
      .personalInfo .personalImage{
         display:inline-block;
         float: right;  
      }
      .personalInfo .personalDetail{
          display: inline-block;
          margin-left: 70px;
          font-family: Century Gothic;
          width:400px;
         vertical-align: middle;
         display: inline-block;
       float:left;
      }
      .personalInfo .personalDetail p{
          margin-left: 20px;
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
      </ul>
    </div>

    <div id="maincontent">
      <br/>
      <div id="page" class="container" >
        <!-- emotion selector -->
        <div id="colorBar">
          <ul>
            <g:each var="i" in="${ (1..<13) }">
              <li><a id="emotionBar${i}"><span>${EmotionGroup.get(i)?.name}</span><img src="${createLinkTo(dir:'images/emotionGroup',file:i+'.png')}"/></a></li>
            </g:each>
          </ul>
        </div>
        
        <center>
                    <div class="personalInfo">


                      <div class="personalDetail">
                        <h2>About Refresh bpm: </h2>
                        <p style="text-align:left;font-size:25px;">
Have you been ever as happy as making other comfortable?!
Have you ever thought the meaning of happiness?! 	
Refresh Bpm is a emotion social website that try to make you realize the 
all events with touched emotion like you in the whole world, and record all history of your
personal emotion that can social and share with others. Refresh Bpm provide three 
different modes to help you melt with this emotional world and bring happiness, sadness, ...
different feeling to others, then make you more understand what you really pursue.  
                        </p>
                      </div>          
                      <div class="personalImage">
                        <img alt="refresh bpm" width="500px" style="margin:30px 10px 40px 10px;"  src="${createLinkTo(dir:'css/images',file:'logo.png')}"/><br/>
                        <img alt="refresh bpm" width="500px"  src="${createLinkTo(dir:'images',file:'colorCircle.png')}"/>
                      </div>

                    </div>
        </center>
          
          
      </div>
    </div>

  </body>
</html>
