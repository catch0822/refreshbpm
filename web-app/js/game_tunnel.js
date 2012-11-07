var canvas;
var worldPanel;

var delta = [ 0, 0 ];
//var stage = [ window.screenX + 200, window.screenY+200, window.innerWidth-200, window.innerHeight-200 ];
//var stage = [ window.screenX, window.screenY, window.innerWidth, window.innerHeight ];
var stage = [200,200,200,200];
//getBrowserDimensions();

var theme;

var worldAABB, world, iterations = 1, timeStep = 1 / 15;

var walls = [];
var wall_thickness = 20;
var wallsSetted = false;

var bodies, elements, text;

var createMode = false;
var destroyMode = false;

var isMouseDown = false;
var mouseJoint;
var mouse = { x: 0, y: 0 };
var gravity = { x: 0, y: 0 };

var timeOfLastTouch = 0;

init();
play();

jQuery.ajax({
          url: "../pandora",
          type: "GET",
          dataType: "json",
          success: function(Jdata) {
            //alert("SUCCESS!!!" + Jdata);
             addEmotions(Jdata);
             //sortByRect();

          },
          error: function() {
            alert("ERROR!!!");
          }
});

/*function Object.prototype.equals(x) {
  var p;
  for(p in this) {
      if(typeof(x[p])=='undefined') {return false;}
  }
  for(p in this) {
      if (this[p]) {
          switch(typeof(this[p])) {
              case 'object':
                  if (!this[p].equals(x[p])) { return false; } break;
              case 'function':
                  if (typeof(x[p])=='undefined' ||
                      (p != 'equals' && this[p].toString() != x[p].toString()))
                      return false;
                  break;
              default:
                  if (this[p] != x[p]) { return false; }
          }
      } else {
          if (x[p])
              return false;
      }
  }
  for(p in x) {
      if(typeof(this[p])=='undefined') {return false;}
  }
  return true;
}
function Object.prototype.equals(obj){
        if(this == obj)return true;
        if(typeof(obj)=="undefined"||obj==null||typeof(obj)!="object")return false;
        var length = 0; var length1=0;
        for(var ele in this) length++;for(var ele in obj) length1++;
        if(length!=length1) return false;
        if(obj.constructor==this.constructor){
                for(var ele in this){
                        if(typeof(this[ele])=="object") {if(!this[ele].equals(obj[ele]))return false;}
                        else if(typeof(this[ele])=="function"){if(!this[ele].toString().equals(obj[ele].toString())) return false;}
                        else if(this[ele]!=obj[ele]) return false;
                }
                return true;
        }
        return false;
}*/

function init() {
	canvas = document.getElementById( 'canvas' );
	//var element = document.createElement("canvas");
	//element.style.position = 'absolute';
	worldPanel = document.getElementById('draw_canvas').getContext('2d');
	//canvas.appendChild(element);

	document.onmousedown = onDocumentMouseDown;
	document.onmouseup = onDocumentMouseUp;
	document.onmousemove = onDocumentMouseMove;

	document.addEventListener( 'touchstart', onDocumentTouchStart, false );
	document.addEventListener( 'touchmove', onDocumentTouchMove, false );
	document.addEventListener( 'touchend', onDocumentTouchEnd, false );

	window.addEventListener( 'deviceorientation', onWindowDeviceOrientation, false );

	// init box2d
	var worldAABB = new b2AABB();
	worldAABB.minVertex.Set(0, 0);
	worldAABB.maxVertex.Set(2000, 2000);
	var gravity = new b2Vec2(0, 0);
	var doSleep = true;
	world = new b2World(worldAABB, gravity, doSleep);

//_createBall(0,0,null,0,15,0,0,0,0,0);
/*
	var bd = createBoxShapeBody(650,250,30,30,0);
	world.CreateBody(bd);

	console.log('--------------- bd.shapes ' + bd.shapes.length + ' ' +bd.shapes);

	var listener = new b2CollisionFilter();
	listener.ShouldCollide = function(sp1 , sp2){ 
		if(sp1.m_position.x == 650 && sp1.m_position.y==250){
			console.log( sp2.m_position.x + '  , ' + sp2.m_position.y +  " >> " + 
					 sp2.m_body.m_userData.imgSrc);
			showImageOnDiv(sp2.m_body.m_userData.imgSrc);
		}
	}
	world.SetFilter(listener);
*/
	//createBox2(world, 450, 200, 10, 350 ,-1);
	//createBox2(world, 1075, 200, 10, 350 ,1);
	setWalls2();
	reset();
}

function showImageOnDiv(imgPath){
	jQuery('#img_preview').html('<img src='+imgPath+' alt="" />')
}

function createBoxShapeBody( x, y, width, height, angel) {
	var boxSd = new b2BoxDef();
	boxSd.extents.Set(width, height);
	//boxSd.m_groupIndex = 999;
	console.debug("  set angel : " + angel);
	var boxBd = new b2BodyDef();
	boxBd.AddShape(boxSd);
	boxBd.position.Set(x,y);
	boxBd.rotation = angel;
	boxBd.m_flags=1000;
	return boxBd;
	//return world.CreateBody(boxBd)
}


function play() {
	setInterval( loop, 1000 / 40 );
}

function addEmotions(json){
	for( i = 0; i < json.length; i++ ) {
		createHeavyBall(json[i]);
	}
}

function createHeavyBall(json){
	_createBall(Math.random() * 400, Math.random() * 400,json , json.color, (json.intensity*5 +15) , /*json.density*/ 0.001, 0.003 , json.friction , Math.random() * 400, Math.random() * 400 );
}


function loop() {
	worldPanel.clearRect(0, 0, window.innerWidth, window.innerHeight);
	draw(worldPanel);
	//if (getBrowserDimensions()) {setWalls();	}
	mouseDrag();

	delta[0] += (0 - delta[0]) * .5;
	delta[1] += (0 - delta[1]) * .5;
	world.Step(timeStep, iterations);
	for (i = 0; i < bodies.length; i++) {
		var body = bodies[i];
		var element = elements[i];
		element.style.left = (body.m_position0.x/* - (element.width )*/) + 'px';
		element.style.top = (body.m_position0.y /*- (element.height )*/) + 'px';
		if (element.tagName == 'DIV') {
			var style = 'rotate(' + (body.m_rotation0 * 57.2957795) + 'deg) translateZ(0)';
			text.style.WebkitTransform = style;
			text.style.MozTransform = style;
			text.style.OTransform = style;
			text.style.msTransform = style;
			text.style.transform = style;
		}
	}
	//
	/*for (var b = world.m_contactList; b; b = b.m_next) {
		console.log("[cantact]" + b.GetShape1().m_position.x + ','+ b.GetShape1().m_position.y);
		//for (var s = b.GetShapeList(); s != null; s = s.GetNext()) {
		//	drawShape(s, c);
		//}
	}*/
	//var ct = world.GetContactList();

	//console.log(" [contect ] " + ct.GetShape1().m_groupIndex + " " + ct.GetShape2().m_groupIndex);
	//for(i = 0 ; i < world.GetContactList().length ; i++){

	//}
}