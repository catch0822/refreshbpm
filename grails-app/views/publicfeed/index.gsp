<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <!-- google map -->
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
    
    <!-- jquery core lib -->
    <g:javascript src="jquery/jquery-1.8.0-min.js"/>
    <g:javascript scr="jquery/jquery-ui-1.8.20.custom.min.js"/>

    <!-- main logical process -->
    <g:javascript src="kmlprocess.js"/>


    <title>Sample title</title>
  </head>
  <body>
  <div id="map_canvas" style="width:512px; height:400px"></div>
  <div id="layers">
  <div id="heading">Layers</div>
  <table>
    <tr>
      <td><input onclick="traffic()" type="radio" name="layer" id="trButton" value="traffic"/></td>
      <td align="left">Traffic</td>
    </tr>
    <tr>
      <td><input onclick="bicycling()" type="radio" name="layer" id="bcButton" value="bicycling"/></td>
      <td align="left">Bicycling</td>
    </tr>
    <tr>
      <td><input checked onclick="kml()" type="radio" name="layer" id="kmlButton" value="kml"/></td>
      <td align="left">KML:
        <input type="text" size="60" id="kmlUrl" onkeyup="kmlKeyUp()" value="http://services.google.com/earth/kmz/california_median_ages_n.kmz" />
      </td>
    </tr>
  </table>
  </body>
</html>
