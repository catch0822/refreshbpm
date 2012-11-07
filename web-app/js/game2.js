var canvas;
var worldPanel;
var delta = [ 0, 0 ];
var stage = [800,400,400,400];
//getBrowserDimensions();

var theme;

var worldAABB, world, iterations = 1, timeStep = 1 / 15;

var walls = [];
var wall_thickness = 200;
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
          },
          error: function() {
            alert("ERROR!!!");
          }
});

function init() {
	canvas = document.getElementById( 'canvas' );
	document.onmousedown = onDocumentMouseDown;
	document.onmouseup = onDocumentMouseUp;
	document.onmousemove = onDocumentMouseMove;
	//document.ondblclick = onDocumentDoubleClick;

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
	setWalls2();
	reset();
}

function play() {
	setInterval( loop, 1000 / 20 );
}

function addEmotions(json){
	for( i = 0; i < json.length; i++ ) {
		createWorldBall(json[i]);
	}
}
function createWorldBall(json){
		var x = (Math.floor((Math.random()*400)+700));
		var y = (Math.floor((Math.random()*200)+500));
		var vy = getRandomV() ;
		var vx = getRandomV() ;
		var color = "rgba("+json.red+", "+json.green+", "+json.blue+", 0.8)";
		_createBall(x,y,
				json , color, getRadius(json.intensity), 0.00001, 1 , json.friction , vx , vy );
}

function bomb(){
	for (var b = world.m_bodyList; b; b = b.m_next) {
		if(b.GetShapeList()!=null && b.m_userData!=null) {
			if(Math.random()<0.25){
				b.m_linearVelocity.x =getRandomV();
				b.m_linearVelocity.y =getRandomV();
			}
		}
	}
}

var count = 0;
function loop() {
	if(((count)%100) == 1 ){ 
		bomb();
	}
	count++;
	delta[0] += (0 - delta[0]) * .5;
	delta[1] += (0 - delta[1]) * .5;
	mouseDrag();
	world.Step(timeStep, iterations);
	for (i = 0; i < bodies.length; i++) {
		var body = bodies[i];
		var element = elements[i];
		if(body.m_userData.isClick==true){
			body.m_position.x = mouse.x-250;
			body.m_position.y = mouse.y+35;
		}
		element.style.left = (body.m_position0.x - (element.width)) + 'px';
		element.style.top = (body.m_position0.y - (element.height)) + 'px';
		if (element.tagName == 'DIV') {
			var style = 'rotate(' + (body.m_rotation0 * 57.2957795) + 'deg) translateZ(0)';
			text.style.WebkitTransform = style;
			text.style.MozTransform = style;
			text.style.OTransform = style;
			text.style.msTransform = style;
			text.style.transform = style;

		}
	}
}

