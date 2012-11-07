<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"/>
<html>
  <head>
    <g:javascript src='prototype-1.6.0.2.js'/>
    <script type="text/javascript" src="${resource(dir: 'path/js', file: 'jquery.min.js')}"></script>
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

         <g:javascript src="jquery/jquery.qtip-1.0.0-rc3.min.js"/>
<style>
div.preview
  {
  width:100%;
  height:100%;
  z-index:-1;
  position: absolute;
  }
h1 {
    color: #48802c;
    font-size: 2.5em;
    margin:0px 20px;
    position: absolute;
}
h3 {
    color: #488ffc;
    width:50%;
    font-size: 1.5em;
    margin:50px 20px;
    position: absolute;
}
img
{
opacity:0.4;
max-width: 1000px;
margin:auto;
display:block;
filter:alpha(opacity=40); /* For IE8 and earlier */
}
</style>

  </head>
  <body bgcolor="#000000">
        <div id="canvas" name="canvas" style="width:100%;height:100%; ">
            <div class="preview" id="img_preview" name="img_preview"></div>
            <canvas id="draw_canvas" width="1200" height="1000" style="z-index: 1;" />
        </div>
        <g:javascript src='baseGameMain.js'/>
        <g:javascript src='game_tunnel.js'/>
  </body>
</html>
