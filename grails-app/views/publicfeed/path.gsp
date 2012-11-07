<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <!-- jquery lib -->
    <g:javascript src="jquery/jquery-1.8.0-min.js"/>
    <g:javascript scr="jquery/jquery-ui-1.8.20.custom.min.js"/>
    <g:javascript src="jquery/jquery.menu.js"/>


    <!-- css -->
    <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery-ui-1.8.20.custom.min.js')}"/></script>
    <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery.menu.js')}"/></script>
    <link rel="stylesheet" href="${resource(dir: 'path/css', file: 'main.css')}">
    <link rel="stylesheet" href="${resource(dir: 'path/css', file: 'piemenu.css')}">

    <title>Sample title</title>
  </head>
  <body>
      <div class='demo'>
              <p id='info'>Updated..</p>
              <div id='outer_container' class="outer_container" >
                      <a class="menu_button" href="#" title="Toggle"><span>Menu Toggle</span></a>
                      <ul class="menu_option">
                        <li><a href="#" ><span id="me">me</span></a></li>
                        <li><a href="#" ><span id="emotionballsItem">emotionballs</span></a></li>
                        <li><a href="#" ><span id="linechartTimeline">linechart</span></a></li>
                        <li><a href="#" id="linechartMap"><span>map</span></a></li>
                      </ul>
              </div>
      </div>
      <div class='drag'>* Draggable Element</div>
      <div class='clearfix'></div>

<script type="text/javascript" src="${resource(dir: 'path', file: 'control.js')}"/></script>
  </body>
</html>
