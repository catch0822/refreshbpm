var PI2 = Math.PI * 2;

var themes = [ [ "#10222B", "#95AB63", "#BDD684", "#E2F0D6", "#F6FFE0" ],
		[ "#362C2A", "#732420", "#BF734C", "#FAD9A0", "#736859" ],
		[ "#0D1114", "#102C2E", "#695F4C", "#EBBC5E", "#FFFBB8" ],
		[ "#2E2F38", "#FFD63E", "#FFB54B", "#E88638", "#8A221C" ],
		[ "#121212", "#E6F2DA", "#C9F24B", "#4D7B85", "#23383D" ],
		[ "#343F40", "#736751", "#F2D7B6", "#BFAC95", "#8C3F3F" ],
		[ "#000000", "#2D2B2A", "#561812", "#B81111", "#FFFFFF" ],
		[ "#333B3A", "#B4BD51", "#543B38", "#61594D", "#B8925A" ] ];


function createHeartShape(){
	/** test the shape of heart */
	var offsetX = 150;
	var offset = 50;
	var wallDep = 10;
	createBox2(world,  50+offsetX, offset+200, wallDep, 600 ,-Math.PI/4);
	createBox2(world, 200+offsetX, offset, wallDep, 300 ,Math.PI/4);
	createBox2(world, 300+offsetX, offset, wallDep, 100 ,Math.PI/2);
	createBox2(world, 400+offsetX, offset, wallDep, 80 ,-Math.PI/4);
	createBox2(world, 500+offsetX, offset, wallDep, 80 ,Math.PI/4);
	createBox2(world, 600+offsetX, offset, wallDep, 100 ,Math.PI/2);
	createBox2(world, 700+offsetX, offset, wallDep, 300 ,-Math.PI/4);
	createBox2(world, 900+offsetX, offset+200, wallDep, 600 ,Math.PI/4);	
}

function getRandomV(){
	return (Math.random()>0.5) ? -(Math.random()*20 + 50)  : (Math.random()*20 + 50);
}

function getRadius(intensity){
	return (intensity*10)+20;
}

function reset() {
	var i;
	if ( bodies ) {
		for ( i = 0; i < bodies.length; i++ ) {
			var body = bodies[ i ]
			if(body!=null){
    			try {
					console.log("  canvas  : " + canvas + " " +body.GetUserData().element);
					if(body.GetUserData().element != null)
						canvas.removeChild( body.GetUserData().element );
						world.DestroyBody( body );
						body = null;
				}catch(err) {}
			}
		}
	}
	// color theme
	theme = themes[ Math.random() * themes.length >> 0 ];
	//document.body.style[ 'backgroundColor' ] = theme[ 0 ];
	bodies = [];
	elements = [];
	//createInstructions();
}

function onDocumentMouseDown() {
	isMouseDown = true;
	return false;
}

function onDocumentMouseUp() {
	isMouseDown = false;
	return false;
}

function onDocumentMouseMove( event ) {
	mouse.x = event.clientX;
	mouse.y = event.clientY;
}

/*function onDocumentDoubleClick() {
	reset();
}*/

function onDocumentTouchStart( event ) {
	if( event.touches.length == 1 ) {
		event.preventDefault();
		// Faking double click for touch devices
		var now = new Date().getTime();
		if ( now - timeOfLastTouch  < 250 ) {
			reset();
			return;
		}
		timeOfLastTouch = now;
		mouse.x = event.touches[ 0 ].pageX;
		mouse.y = event.touches[ 0 ].pageY;
		isMouseDown = true;
	}
}

function onDocumentTouchMove( event ) {
	if ( event.touches.length == 1 ) {
		event.preventDefault();
		mouse.x = event.touches[ 0 ].pageX;
		mouse.y = event.touches[ 0 ].pageY;
	}
}

function onDocumentTouchEnd( event ) {
	if ( event.touches.length == 0 ) {
		event.preventDefault();
		isMouseDown = false;
	}
}

function onWindowDeviceOrientation( event ) {
	if ( event.beta ) {
		gravity.x = Math.sin( event.gamma * Math.PI / 180 );
		gravity.y = Math.sin( ( Math.PI / 4 ) + event.beta * Math.PI / 180 );
	}
}

function createInstructions() {
	var size = 250;
	var element = document.createElement( 'div' );
	element.width = size;
	element.height = size;	
	element.style.position = 'absolute';
	//element.style.left = -200 + 'px';
	//element.style.top = -200 + 'px';
	element.style.cursor = "default";

	canvas.appendChild(element);
	elements.push( element );

	var circle = document.createElement( 'canvas' );
	circle.width = size;
	circle.height = size;

	var graphics = circle.getContext( '2d' );

	graphics.fillStyle = theme[ 3 ];
	graphics.beginPath();
	graphics.arc( size * .5, size * .5, size * .5, 0, PI2, true );
	graphics.closePath();
	graphics.fill();

	element.appendChild( circle );

	text = document.createElement( 'div' );
	text.onSelectStart = null;
	text.innerHTML = '<span style="color:' + theme[0] + ';font-size:40px;">Hello!</span><br /><br /><span style="font-size:15px;"><strong>This is how it works:</strong><br /><br />1. Drag a ball.<br />2.&nbsp;Click&nbsp;on&nbsp;the&nbsp;background.<br />3. Shake your browser.<br />4. Double click.<br />5. Play!</span>';
	text.style.color = theme[1];
	text.style.position = 'absolute';
	text.style.left = '0px';
	text.style.top = '0px';
	text.style.fontFamily = 'Georgia';
	text.style.textAlign = 'center';
	element.appendChild(text);

	text.style.left = ((250 - text.clientWidth) / 2) +'px';
	text.style.top = ((250 - text.clientHeight) / 2) +'px';	

	var b2body = new b2BodyDef();

	var circle = new b2CircleDef();
	circle.radius = size / 2;
	circle.density = 1;
	circle.friction = 0.0;
	circle.restitution = 0.3;
	b2body.AddShape(circle);
	b2body.userData = {element: element};

	b2body.position.Set( Math.random() * stage[2], Math.random() );
	b2body.linearVelocity.Set( Math.random() * 400 , Math.random() * 400  );
	bodies.push( world.CreateBody(b2body) );	
}

function createBox(world, x, y, width, height, fixed) {
	if (typeof(fixed) == 'undefined') {
		fixed = true;
	}
	var boxSd = new b2BoxDef();
	if (!fixed) {
		boxSd.density = 1.0;
	}
	boxSd.extents.Set(width, height);
	var boxBd = new b2BodyDef();
	boxBd.AddShape(boxSd);
	boxBd.position.Set(x,y);
	return world.CreateBody(boxBd);
}


function createBox2(world, x, y, width, height, angel) {
	var boxSd = new b2BoxDef();
	boxSd.extents.Set(width, height);
	boxSd.m_groupIndex = 999;
	console.debug("  set angel : " + angel);
	var boxBd = new b2BodyDef();
	boxBd.AddShape(boxSd);
	boxBd.position.Set(x,y);
	boxBd.rotation = angel; 
	return world.CreateBody(boxBd);
}

function scaleCanvas(color , r , graphics) {
	//graphics.fillStyle = "rgba("+Math.floor(Math.random()*255)+", "+Math.floor(Math.random()*255)+", "+Math.floor(Math.random()*255)+", 0.8)";
	graphics.fillStyle = color;
	graphics.beginPath();
	graphics.arc(r, r, r, 0, PI2, true); 
	graphics.closePath();
	graphics.fill();
}

function sortByRect(){
	var initX = 100;
	var initY = 100;
	var x = initX;
	var y = initY;
	var gapW = 30;
	var gapH = 30;
	var maxX = 35;
	var idx = 0;
	for (var b = world.m_bodyList; b; b = b.m_next) {
		if(b.GetShapeList()!=null && b.m_userData!=null) {
			//b.position.Set( x, y );
			b.m_position.x = x;
			b.m_position.y = y;
			b.m_linearVelocity.x = 0;
			b.m_linearVelocity.y = 0;
			idx ++;
			x+=gapW;
			if(idx>maxX){
				y+=gapH;
				x = initX;
				idx = 0;
			}
			console.log(" idx " + idx);
		}
	}
}

function _createBall(x, y , imgJson , color, r , density, restitution , friction , initVX , initVY){
	var element = document.createElement('a');
	element.setAttribute("href", imgJson.imgSrc);
	element.setAttribute("rel", "lightbox");
	var name = "ball_"+x+"_"+y;
	element.setAttribute("id", name);
	element.setAttribute('title', imgJson.title + "\n" + imgJson.description );
	var e = document.createElement("canvas");
	e.width=r*2;
	e.height=r*2;
	//var e = document.createElement("canvas");
	element.width = r*2;
	element.height = r*2;
	//element.style.position = 'relative';
	element.style.position = 'absolute';
	//element.style.left = size/2 + 'px';
	//element.style.top = size/2 + 'px';
	element.style.WebkitTransform = 'translateZ(0)';
	element.style.MozTransform = 'translateZ(0)';
	element.style.OTransform = 'translateZ(0)';
	element.style.msTransform = 'translateZ(0)';
	element.style.transform = 'translateZ(0)';

	var graphics = e.getContext("2d");
	graphics.clearRect(0, 0, r,r);
	scaleCanvas(color, r , graphics);

	element.appendChild(e);
	canvas.appendChild(element);

	elements.push(element);
	
	var b2body = new b2BodyDef();
	var circle = new b2CircleDef();
	circle.radius = r ;//FIXME
	circle.density =  density;
	//circle.density =  0.001;
	circle.friction = 1.0;
	circle.restitution = restitution;
	//circle.restitution = 1;
	b2body.AddShape(circle);
	b2body.userData = { element : element , imgSrc : imgJson , isClick:false };
	b2body.position.Set( x, y );
	b2body.linearVelocity.Set( initVX ,initVY );
	bodies.push( world.CreateBody(b2body) );

	element.onclick = function () { 
		b2body.userData.isClick = true;
		freezeAllButThis();
	};

	element.onmouseover= function () { 
		//if(b2body.userData.isClick==false){
			b2body.userData.isClick = true;
			//scaleCanvas(color, size*2 , graphics);
			freezeAllButThis();
		//}
	};
	element.onmouseout= function () { 
		//if(b2body.userData.isClick==false){
			//scaleCanvas(color, size , graphics);
			wakeupAll();
	};

	jQuery("#"+name).qtip({
   		content: '<img src ='+imgJson.thumbImg+' /><br><span>'+(imgJson.title + "\n" + imgJson.description)+'</span>',
   		style: { 
      		  width: 200,
		      padding: 5,
		      background: '#A2D959',
		      color: 'black',
		      textAlign: 'center',
		      border: {
		         width: 7,
		         radius: 5,
		         color: '#A2D959'
		      },
      	tip: 'topLeft',
      	name: 'dark' // Inherit the rest of the attributes from the preset dark style
   		}
	});
}

function wakeupAll(){
	for (var b = world.m_bodyList; b; b = b.m_next) {
		if(b.m_userData!=null ) {
			b.m_linearVelocity.x = getRandomV() ;
			b.m_linearVelocity.y = getRandomV();
			b.GetShapeList().m_restitution= 1;	
			if( b.m_userData.isClick==true ){
				b.m_userData.isClick = false;
			}
		}
	}
	world.m_gravity = lightGravity;
}
//var isFreezeMode = false;

var heaveGravity = new b2Vec2(0,1000);
var lightGravity = new b2Vec2(0,0);
function freezeAllButThis(){
	for (var b = world.m_bodyList; b; b = b.m_next) {
		if(b.GetShapeList()!=null && b.m_userData!=null ) {
			if( b.m_userData.isClick!=true ){
				//b.Freeze();
				b.GetShapeList().m_restitution= 0;
			}else{
				b.m_linearVelocity.x = 0;
				b.m_linearVelocity.y = -20;
			}
		}
	}
	world.m_gravity = heaveGravity;
}

function moveDownAllButThis(body){
	for (var b = world.m_bodyList; b; b = b.m_next) {
		if(b.GetShapeList()!=null && b.m_userData!=null ) {
			if( b.m_userData.isClick!=true ){
				b.m_angularVelocity = 10;
				b.m_linearVelocity.x = 0;
				b.m_linearVelocity.y = 50;
				b.m_mass = 100;
			}else{
				b.m_angularVelocity = 30;
				b.m_linearVelocity.x = 0;
				b.m_linearVelocity.y = -100;
				b.m_userData.isClick = false;
			}
		}
	}
}

function createBall( json ) {
	var x =  Math.random() * stage[2];
	var y =  Math.random();
	createBall(json,x,y);
}

function createBall( json , x , y ) {
	_createBall( x , y , json , json.color, (json.intensity*5 +100) , json.density, json.restitution , json.friction ,Math.random() * 400, Math.random() * 400 );
	//_createBall( x , y , json , json.color, (json.intensity*5 +20) , json.density, json.restitution , 0,0 );
}

function showImg(json){
	console.debug('show img ' + json.imgSrc);
	var content = '<li><a href='+json.imgSrc+' title='+json.title+'><img src='+json.imgSrc+' alt="" /></a></li>';
	//jQuery('#img_preview').html('<h1>'+json.title+'<h1><br>' +
	//							  '<h3>'+json.description+'</h3><br>'+
	//							  '<img src='+json.imgSrc+' alt="" />');
	jQuery('#img_preview').html(content);
	//var elem = document.getElementById(“elementID”);
	//jQuery('#img_preview').requestFullScreen();
}


function draw(c){
	for (var b = world.m_bodyList; b; b = b.m_next) {
		for (var s = b.GetShapeList(); s != null; s = s.GetNext()) {
			drawShape(s, c);
		}
	}

	for (var b = world.m_groundBody; b; b = b.m_next) {
		for (var s = b.GetShapeList(); s != null; s = s.GetNext()) {
			drawShape(s, c);
		}
	}
}

function drawShape(shape, context) {
	
	context.beginPath();
	switch (shape.m_type) {
	case b2Shape.e_circleShape:
		{
			context.strokeStyle = '#ffffff';
			var circle = shape;
			var pos = circle.m_position;
			var r = circle.m_radius;
			var segments = 16.0;
			var theta = 0.0;
			var dtheta = 2.0 * Math.PI / segments;
			// draw circle
			context.moveTo(pos.x + r, pos.y);
			for (var i = 0; i < segments; i++) {
				var d = new b2Vec2(r * Math.cos(theta), r * Math.sin(theta));
				var v = b2Math.AddVV(pos, d);
				context.lineTo(v.x, v.y);
				theta += dtheta;
			}
			context.lineTo(pos.x + r, pos.y);
	
			// draw radius
			context.moveTo(pos.x, pos.y);
			var ax = circle.m_R.col1;
			var pos2 = new b2Vec2(pos.x + r * ax.x, pos.y + r * ax.y);
			context.lineTo(pos2.x, pos2.y);
		}
		break;
	case b2Shape.e_polyShape:
		{
			var poly = shape;
			var tV = b2Math.AddVV(poly.m_position, b2Math.b2MulMV(poly.m_R, poly.m_vertices[0]));
			context.moveTo(tV.x, tV.y);
			for (var i = 0; i < poly.m_vertexCount; i++) {
				var v = b2Math.AddVV(poly.m_position, b2Math.b2MulMV(poly.m_R, poly.m_vertices[i]));
				context.lineTo(v.x, v.y);
			}
			context.lineTo(tV.x, tV.y);
			context.font = "12pt Calibri";
        	context.fillStyle = "red";
        	context.fillText(""+poly.m_groupIndex, tV.x, tV.y);
		}
		break;
	}
	context.stroke();
}

function drawJoint(joint,context) {
	var b1 = joint.m_body1;
	var b2 = joint.m_body2;
	var x1 = b1.m_position;
	var x2 = b2.m_position;
	var p1 = joint.GetAnchor1();
	var p2 = joint.GetAnchor2();
	context.strokeStyle = '#00eeee';
	context.beginPath();
	switch (joint.m_type) {
	case b2Joint.e_distanceJoint:
		context.moveTo(p1.x, p1.y);
		context.lineTo(p2.x, p2.y);
		break;

	case b2Joint.e_pulleyJoint:
		// TODO
		break;

	default:
		if (b1 == world.m_groundBody) {
			context.moveTo(p1.x, p1.y);
			context.lineTo(x2.x, x2.y);
		}
		else if (b2 == world.m_groundBody) {
			context.moveTo(p1.x, p1.y);
			context.lineTo(x1.x, x1.y);
		}
		else {
			context.moveTo(x1.x, x1.y);
			context.lineTo(p1.x, p1.y);
			context.lineTo(x2.x, x2.y);
			context.lineTo(p2.x, p2.y);
		}
		break;
	}
	context.stroke();
}

function mouseDrag()
{
	// mouse press
	if (createMode) {
		//createBall( mouse.x, mouse.y );
	} else if (isMouseDown && !mouseJoint) {
		var body = getBodyAtMouse();
		if (body) {
			var md = new b2MouseJointDef();
			md.body1 = world.m_groundBody;
			md.body2 = body;
			md.target.Set(mouse.x, mouse.y);
			md.maxForce = 30000 * body.m_mass;
			// md.timeStep = timeStep;
			mouseJoint = world.CreateJoint(md);
			body.WakeUp();
		} else {
			createMode = true;
		}
	}

	// mouse release
	if (!isMouseDown) {
		createMode = false;
		destroyMode = false;
		if (mouseJoint) {
			world.DestroyJoint(mouseJoint);
			mouseJoint = null;
		}
	}
	// mouse move
	if (mouseJoint) {
		var p2 = new b2Vec2(mouse.x, mouse.y);
		mouseJoint.SetTarget(p2);
	}
}

function getBodyAtMouse() {
	// Make a small box.
	var mousePVec = new b2Vec2();
	mousePVec.Set(mouse.x, mouse.y);

	var aabb = new b2AABB();
	aabb.minVertex.Set(mouse.x - 1, mouse.y - 1);
	aabb.maxVertex.Set(mouse.x + 1, mouse.y + 1);
	// Query the world for overlapping shapes.
	var k_maxCount = 10;
	var shapes = new Array();
	var count = world.Query(aabb, shapes, k_maxCount);
	var body = null;

	for (var i = 0; i < count; ++i) {
		if (shapes[i].m_body.IsStatic() == false) {
			if ( shapes[i].TestPoint(mousePVec) ) {
				body = shapes[i].m_body;
				break;
			}
		}
	}
	return body;
}

function setWalls() {
	if (wallsSetted) {
		world.DestroyBody(walls[0]);
		world.DestroyBody(walls[1]);
		world.DestroyBody(walls[2]);
		world.DestroyBody(walls[3]);
		walls[0] = null; 
		walls[1] = null;
		walls[2] = null;
		walls[3] = null;
	}

	walls[0] = createBox(world, stage[2] / 2, - wall_thickness, stage[2], wall_thickness);
	walls[1] = createBox(world, stage[2] / 2, stage[3] + wall_thickness, stage[2], wall_thickness);
	walls[2] = createBox(world, - wall_thickness, stage[3] / 2, wall_thickness, stage[3]);
	walls[3] = createBox(world, stage[2] + wall_thickness, stage[3] / 2, wall_thickness, stage[3]);	

	wallsSetted = true;
}

function setWalls2() {
	if (wallsSetted) {
		world.DestroyBody(walls[0]);
		world.DestroyBody(walls[1]);
		world.DestroyBody(walls[2]);
		world.DestroyBody(walls[3]);
		walls[0] = null; 
		walls[1] = null;
		walls[2] = null;
		walls[3] = null;
	}
	walls[0] = createBox(world, 400, 200, 1500, 50);
	walls[1] = createBox(world, 400, 780, 1500, 50);
	walls[2] = createBox(world, 400-400, 300 , 50, 900);
	walls[3] = createBox(world, 400+1500, 300, 50, 900);
	wallsSetted = true;
}

/** optional impl .. */
function sortByLineChart(){
	var initX = 150;
	var initY = 100;
	var x = initX;
	var y = initY;
	var gapW = 10;
	var gapH = 30;
	var maxX = 20;
	var idx = 0;
	var gapX = 100; 
	var locations = { 1 : {"x":gapX,"y":20,"count":0} , 
					  2 : {"x":gapX*2,"y":20,"count":0} , 
					  3 : {"x":gapX*3,"y":20,"count":0} , 
					  4 : {"x":gapX*4,"y":20,"count":0} , 
					  5 : {"x":gapX*5,"y":20,"count":0} , 
					  6 : {"x":gapX*6,"y":20,"count":0} , 
					  7 : {"x":gapX*7,"y":20,"count":0} , 
					  8 : {"x":gapX*8,"y":20,"count":0} , 
					  9 : {"x":gapX*9,"y":20,"count":0} , 
					  10 : {"x":gapX*10,"y":20,"count":0} , 
					  11 : {"x":gapX*11,"y":20,"count":0} , 
					  12 : {"x":gapX*12,"y":20,"count":0} , 
					};

	for (var b = world.m_bodyList; b; b = b.m_next) {
		if(b.GetShapeList()!=null && b.m_userData!=null) {
			var l = locations[b.m_userData.imgSrc.emotionId];

			b.m_position.x = (l.count%2==0) ?l.x+10 :l.x;
			b.m_position.y = l.y + l.count*gapH;
			b.m_linearVelocity.x = 0;
			b.m_linearVelocity.y = 10;
			idx ++;
			x+=gapW;
			if(idx>maxX){
				y+=gapH;
				x = initX;
				idx = 0;
			}
			l.count = l.count + 1;
			console.log(" idx " + idx);
		}
	}
}


// BROWSER DIMENSIONS
function getBrowserDimensions() {
	var changed = false;
	if (stage[0] != window.screenX) {
		delta[0] = (window.screenX - stage[0]) * 50;
		stage[0] = window.screenX;
		changed = true;
	}
	if (stage[1] != window.screenY) {
		delta[1] = (window.screenY - stage[1]) * 50;
		stage[1] = window.screenY;
		changed = true;
	}
	if (stage[2] != window.innerWidth) {
		stage[2] = window.innerWidth;
		changed = true;
	}
	if (stage[3] != window.innerHeight) {
		stage[3] = window.innerHeight;
		changed = true;
	}
	return changed;
}