<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="refreshbpm.EmotionGroup"%> 
<html xmlns="http://www.w3.org/1999/xhtml"/>
<html>
  <head>
    <meta name="layout" content="main"/>
    <style type="text/css">
      .personalInfo{
          width:850px;
      }
      .personalInfo .personalImage{
         display:inline-block;
         margin-left: 200px;
         width:300px;
         max-height: 400px;
         margin-top: 50px;
      }
      .personalInfo .personalDetail{
          display: inline-block;
          margin-left: 70px;
          font-family: Century Gothic;
         max-height: 300px;
         vertical-align: middle;
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
  <center>
    <div id="maincontent" style="margin-right:70px;">
      <br/>
      <div id="page" class="container" width="1024px;">
        <!-- emotion selector -->
        <div id="colorBar">
          <ul>
            <g:each var="i" in="${ (1..<13) }">
              <li><a id="emotionBar${i}"><span>${EmotionGroup.get(i)?.name}</span><img src="${createLinkTo(dir:'images/emotionGroup',file:i+'.png')}"/></a></li>
            </g:each>
          </ul>
        </div>
            
        <!-- Prada -->
        <div class="personalInfo">
          <div class="personalImage">
            <img alt="prada" src="${createLinkTo(dir:'images',file:'prada.jpg')}"/>
          </div>
          <div class="personalDetail">
            <b>name: </b><p>Hsiung, Chun-Kuei</p>
            <b>company: </b><p style="margin-left: 20px;">Aplix iasolution</p></p>
            <b>email: </b> <p>bear.prada@gmail.com </p>
          </div>
        </div>
        
        <!-- Frank -->
        <div class="personalInfo">
          <div class="personalImage">
            <img alt="frank"  src="${createLinkTo(dir:'images',file:'frank.jpg')}"/>
          </div>
          <div class="personalDetail">
            <b>name: </b><p>Hsu, Chin-Yen</p>
            <b>company: </b><p style="margin-left: 20px;">Aplix iasolution</p></p>
            <b>email: </b> <p>noway55m@gmail.com</p>
          </div>
        </div>

        <!-- Wei -->
        <div class="personalInfo">
          <div class="personalImage">
            <img alt="tyler" width="300px " style="inline;" src="${createLinkTo(dir:'images',file:'wei.jpg')}"/>
          </div>
          <div class="personalDetail">
            <b>name: </b><p>Chang, Yu-Wei</p>
            <b >company: </b><p style="margin-left: 20px;">Aplix iasolution</p></p>
            <b>email: </b> <p>catch0822@gmail.com</p>
          </div>
        </div>        
        
        <!-- Tyler -->
        <div class="personalInfo">
          <div class="personalImage">
                <img alt="tyler" width="300px" src="${createLinkTo(dir:'images',file:'tyler.jpg')}"/>          
          </div>
          <div class="personalDetail">
            <b>name: </b><p>Chang, Keng-Wei</p>
            <b >company: </b><p style="margin-left: 20px;">Aplix iasolution</p></p>
            <b>email: </b> <p style="margin-left: 20px;">eleca30@gmail.com</p> 
          </div>
        </div>        
        
        
        
        
        
      </div>
    </div>
    </center>
  </body>
</html>
