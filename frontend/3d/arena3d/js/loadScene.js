//var robots = new Array(new NetworkRobot(0,0,0,0),new NetworkRobot(0,0,0,0));
//match = new NetworkMatch(robots);

/*
function loadMyRobot(){
//eventuellt chassi = parent.parent.robot.component[0];

chassi = parent.parent.component[0];
wheels = parent.parent.component[1];
weapon = parent.parent.component[2];

makeMyRobot3d(chassi,wheels,weapon);
}

function loadOpponentRobot(){
chassi = parent.parent.opponentComponent[0];
wheels = parent.parent.opponentComponent[1];
weapon = parent.parent.opponentComponent[2];

makeOpponentRobot3d(chassi,wheels,weapon);
}
*/

require([
	'goo/entities/GooRunner',
	'goo/statemachine/FSMSystem',
	'goo/addons/howler/systems/HowlerSystem',
	'goo/loaders/DynamicLoader',
	'goo/math/Vector3',

	'goo/renderer/Camera',
	'goo/entities/components/CameraComponent',

	'goo/entities/components/ScriptComponent',
	'goo/scripts/OrbitCamControlScript',

	'goo/renderer/light/DirectionalLight',
	'goo/entities/components/LightComponent',
	'goo/shapes/ShapeCreator',
	'goo/renderer/Material',
	'goo/renderer/shaders/ShaderLib',
	'goo/entities/EntityUtils'

], function (
	GooRunner,
	FSMSystem,
	HowlerSystem,
	DynamicLoader,
	Vector3,

	Camera,
	CameraComponent,

	ScriptComponent,
	OrbitCamControlScript,

	DirectionalLight,
	LightComponent,
	ShapeCreator,
	Material,
	ShaderLib,
	EntityUtils
) {
	'use strict';

	function init() {

		// If you try to load a scene without a server, you're gonna have a bad time
		if (window.location.protocol==='file:') {
			alert('You need to run this webpage on a server. Check the code for links and details.');
			return;

			/*

			Loading scenes uses AJAX requests, which require that the webpage is accessed via http. Setting up 
			a web server is not very complicated, and there are lots of free options. Here are some suggestions 
			that will do the job and do it well, but there are lots of other options.

			- Windows

			There's Apache (http://httpd.apache.org/docs/current/platform/windows.html)
			There's nginx (http://nginx.org/en/docs/windows.html)
			And for the truly lightweight, there's mongoose (https://code.google.com/p/mongoose/)

			- Linux
			Most distributions have neat packages for Apache (http://httpd.apache.org/) and nginx
			(http://nginx.org/en/docs/windows.html) and about a gazillion other options that didn't 
			fit in here. 
			One option is calling 'python -m SimpleHTTPServer' inside the unpacked folder if you have python installed.


			- Mac OS X

			Most Mac users will have Apache web server bundled with the OS. 
			Read this to get started: http://osxdaily.com/2012/09/02/start-apache-web-server-mac-os-x/

			*/
		}

		
		// Create typical goo application
		var goo = new GooRunner({
			antialias: true,
			manuallyStartGameLoop: true
		});
		var fsm = new FSMSystem(goo);
		goo.world.setSystem(fsm);
		goo.world.setSystem(new HowlerSystem());
		
	
		
		//Globals
		
		// This "robot" will be a hided entity that will be the parent of att robotparts
		var meshData = ShapeCreator.createBox(1, 1, 1); 
		var robot = EntityUtils.createTypicalEntity(goo.world, meshData); 
		var material = Material.createMaterial(ShaderLib.texturedLit, 'BoxMaterial'); 
		robot.meshRendererComponent.materials.push(material); 
		robot.addToWorld();
		EntityUtils.hide(robot);
		// ------------------------------------------------------------------------------
		
		var speed = 1.5;
		var moveForward = false;
		var turnRight = false;
		var turnLeft = false;
		var moveBackward = false;
		//var robotPos = new Vector3(0,0,0);
		var robotPos = new Vector3(match.robots[0].getX(), 0, match.robots[0].getY());
		var robotLookAt = new Vector3(0,0,-10);
		var camLookAt = new Vector3(robotPos.x,robotPos.y,robotPos.z-200);
		var audienceCameraPos = new Vector3(500,200,0);
		var behindCameraPos = new Vector3(0,80,200);
		var behindCamLookAt = new Vector3(0,0,-200);
		var worldUp = new Vector3(0,1,0);
		var robotRotation = 0;
		var rotationSpeed = 0.01;
		
		// 		Creating the Camera
		var camera = new Camera(35, 1, 0.1, 1000);
		var cameraEntity = goo.world.createEntity('Camera');
		cameraEntity.setComponent(new CameraComponent(camera));
		cameraEntity.addToWorld();
		
		
		// This switch case is to test driving the robot
		document.onkeydown = function() {
			switch (window.event.keyCode) {
				case 49:
					if(moveForward)
						moveForward = false;
					else
						moveForward = true;
					break;
					
				case 50:
					//console.log(goo.world.entityManager.getEntities()[0].toString());
					
					if(moveBackward)
						moveBackward = false;
					else
						moveBackward = true;
					break;
					
				case 51:
					if(turnLeft)
						turnLeft = false;
					else
						turnLeft = true;
					break;
					
				case 52:
					if(turnRight)
						turnRight = false;
					else
						turnRight = true;
					break;
					
				case 53:
					cameraMode.setSideView();
					cameraMode.lookAtRobot();
					break;
					
				case 54:
					cameraMode.setBehindView();
					cameraMode.lookAtRobot();
					break
					
			}
		}
		
		//@cameraMode
		//A class that allows changing between different camera views
		//Cameraviews:
		// - sideView is setting the camera from an audiance perspective
		//   and it will follow the robot when it moves
		//
		// - behindView is setting the camera as child to the robot
		//   and putting it behind the robot in a 3:d person view
		//   the camera will also move exactly the way the robot moves
		//
		//In the function that makes the robot move these lines need to be added
		//----------------------------------------
		//if(cameraMode.getView()=="sideView")
		//		cameraMode.lookAtRobot();
		//----------------------------------------
		//It will make the sideView mode follow the robot
		var cameraMode = (function(){
			var sideView = false;
			var behindView = false;
			
			return{
			
				setSideView: function(){
					//var robot = loader.getCachedObjectForRef("FullTank2/entities/RootNode.entity");
					robot.transformComponent.detachChild(cameraEntity.getComponent('transformComponent'));
					cameraEntity.transformComponent.setTranslation(audienceCameraPos);
					cameraEntity.transformComponent.lookAt(robotPos, worldUp);
					cameraEntity.transformComponent.setUpdated();
					behindView = false;
					sideView = true;
				},
				
				setBehindView: function(){
					//var robot = loader.getCachedObjectForRef("FullTank2/entities/RootNode.entity");
					cameraEntity.transformComponent.setTranslation(behindCameraPos);
					robot.transformComponent.attachChild(cameraEntity.getComponent('transformComponent'));
					sideView = false;
					behindView = true;
					
				},
				//This method sets the camera lookAt depending on which mode is set
				lookAtRobot: function(){
					if(sideView)
						cameraEntity.transformComponent.lookAt(robotPos, worldUp);
					if(behindView)
						cameraEntity.transformComponent.lookAt(behindCamLookAt,worldUp);
				},
				
				getView: function(){
					if(sideView)
						return "sideView";
					else if(behindView)
						return "behindView";
					else
						return "noView";
				}
			
			}
		}());
		
		// The array with all entitys to load
		var entityStrings = new Array();
		entityStrings[0] = "Arena";
		entityStrings[1] = "Chassi1";
		entityStrings[2] = "Chassi2";
		entityStrings[3] = "Laser";
		entityStrings[4] = "Wheels";

		
		var loader = new DynamicLoader({world: goo.world, rootPath: 'res'});
		goo.renderer.domElement.id = 'goo';
		document.body.appendChild(goo.renderer.domElement);
		goo.startGameLoop();
		var loaderModule = (function() {
	
		var loadedEntities = []; // private array with the loaded entities
			return{ // exposed to public
			
				//Returns the array of entities that have been loaded
				getEntityArray: function(){
					return loadedEntities;
				},
				
				//loads the entity into the world
				//input @entityName = a string with the name of the .project file
				//and the folder that contains the entity, must be the same
				importEntity: function(entityName){
						loader.loadFromBundle('project.project', entityName + '.bundle')
						.then(function(configs) {
						
						cameraMode.setBehindView();
						cameraMode.lookAtRobot();
						
						
						var ent = loader.getCachedObjectForRef(entityName + "/entities/RootNode.entity");
						robot.transformComponent.attachChild(ent.getComponent('transformComponent'));	
						if(entityName == "Arena"){
							robot.transformComponent.detachChild(ent.getComponent('transformComponent'));
							ent.transformComponent.setTranslation(0,-19,0);
						}
						
						parent.setReady();
						
					})
					.then(null, function(e) {
					// The second parameter of 'then' is an error handling function.
					// We just pop up an error message in case the scene fails to load.
					alert('Failed to load scene: ' + e);
					});
				}
			}
		}());
		
		
		// Runtime translations and rotations to the "robot"
		robot.setComponent(new ScriptComponent({
			 run: runfunction(entity, 0)
		}));
		var runfunction=function (entity, index) {
				
				if(cameraMode.getView()=="sideView")
					cameraMode.lookAtRobot();
	 			
				var currentT = (new Date).getTime();
				var dT = (currentT - T)/1000;
				T = currentT;
				//for( var i=0;i<parent.currentMatchState.robots.length;i++){
					// console.log("en");
					var i=index;
					parent.currentMatchState.robots[i].setdX(parent.nextMatchState.robots[i].getdX());
					parent.currentMatchState.robots[i].setdZ(parent.nextMatchState.robots[i].getdZ());
					parent.currentMatchState.robots[i].setAngularVelocity(parent.nextMatchState.robots[i].getAngularVelocity());
					parent.currentMatchState.robots[i].setX(parent.currentMatchState.robots[i].getX()+dT*parent.currentMatchState.robots[i].getdX());
					parent.currentMatchState.robots[i].setZ(parent.currentMatchState.robots[i].getZ()+dT*parent.currentMatchState.robots[i].getdZ());
					parent.currentMatchState.robots[i].setRotation(parent.currentMatchState.robots[i].getRotation()*dT*parent.currentMatchState.robots[i].getAngularVelocity());
					entity.transformComponent.setTranslation(parent.currentMatchState.robots[i].getX(),parent.currentMatchState.robots[i].getY(),parent.currentMatchState.robots[i].getZ());
					entity.transformComponent.setRotation(0,parent.currentMatchState.robots[i].getRotation(),0);
					entity.transformComponent.setUpdated();
				//} 
			 
		/* 		 if(moveForward){
					 entity.transformComponent.setTranslation(
					 robotPos.x,
					 robotPos.y,
					 robotPos.z);
					
					 if(cameraMode.getView()=="sideView")
						cameraMode.lookAtRobot();
					 
					 robotPos.z = robotPos.z-(speed*Math.cos(robotRotation));
					 robotPos.x = robotPos.x-(speed*Math.sin(robotRotation));
					 entity.transformComponent.setUpdated();
		
				 }
					 
				 if(moveBackward){
					 entity.transformComponent.setTranslation(
					 robotPos.x,
					 robotPos.y,
					 robotPos.z);
					
					 if(cameraMode.getView()=="sideView")
						cameraMode.lookAtRobot();

					 robotPos.z = robotPos.z+(speed*Math.cos(robotRotation));
					 robotPos.x = robotPos.x+(speed*Math.sin(robotRotation));
					 entity.transformComponent.setUpdated();

				 }
				 
				 if(turnRight){
					 entity.transformComponent.setRotation(
					 0,
					 robotRotation,
					 0); 

					 robotRotation = robotRotation+(rotationSpeed);
					 entity.transformComponent.setUpdated();
				 }
				 
				 if(turnLeft){
					
					 entity.transformComponent.setRotation(
					 0,
					 robotRotation,
					 0);

					 robotRotation = robotRotation-(rotationSpeed);
					 entity.transformComponent.setUpdated();
				 } */
			}
		//var camScript = new OrbitCamControlScript();
		//cameraEntity.setComponent(new ScriptComponent(camScript));
		
		
		// Load all entitys
		for(var i = 0; i < entityStrings.length; i++){
			loaderModule.importEntity(entityStrings[i]);
		}
	}

	init();
	
		
	
	
	
});
