//var robots = new Array(new NetworkRobot(0,0,0,0),new NetworkRobot(0,0,0,0));
//match = new NetworkMatch(robots);

var chassi;
var wheels;
var weapon;
var opponentChassi;
var opponentWheels;
var opponentWeapon;



if(parent.parent.yourIndex == 0){

	chassi = parent.parent.allRobotBluePrints[0].components[0];
	wheels = parent.parent.allRobotBluePrints[0].components[0];
	weapon = parent.parent.allRobotBluePrints[0].components[0];
	opponentChassi = parent.parent.allRobotBluePrints[1].components[0];
	opponentWheels = parent.parent.allRobotBluePrints[1].components[1];
	opponentWeapon = parent.parent.allRobotBluePrints[1].components[2];
}
else{
	chassi = parent.parent.allRobotBluePrints[1].components[0];
	wheels = parent.parent.allRobotBluePrints[1].components[1];
	weapon = parent.parent.allRobotBluePrints[1].components[2];
	opponentChassi = parent.parent.allRobotBluePrints[0].components[0];
	opponentWheels = parent.parent.allRobotBluePrints[0].components[1];
	opponentWeapon = parent.parent.allRobotBluePrints[0].components[2];

}

console.log(chassi);
console.log(wheels);
console.log(weapon);


//function loadMyRobot(){
//makeMyRobot3d(chassi,wheels,weapon);
//}

/*function loadOpponentRobot(){
chassi = parent.parent.opponentComponent[0];
wheels = parent.parent.opponentComponent[1];
weapon = parent.parent.opponentComponent[2];

makeOpponentRobot3d(chassi,wheels,weapon);
}*/


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
	
		//loadMyRobot();

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
		
		var meshData2 = ShapeCreator.createBox(1, 1, 1); 
		var robot2 = EntityUtils.createTypicalEntity(goo.world, meshData2); 
		var material2 = Material.createMaterial(ShaderLib.texturedLit, 'BoxMaterial'); 
		robot2.meshRendererComponent.materials.push(material2); 
		robot2.addToWorld();
		EntityUtils.hide(robot2);
		// ------------------------------------------------------------------------------
		
		var speed = 1.5;
		var projectileSpeed = 10;
		var moveForward = false;
		var turnRight = false;
		var turnLeft = false;
		var moveBackward = false;
		var robotPos = new Vector3(0,0,0);
		//var robotPos = new Vector3(match.robots[0].getX(), 0, match.robots[0].getY());
		var robotLookAt = new Vector3(0,0,-10);
		var camLookAt = new Vector3(robotPos.x,robotPos.y,robotPos.z-200);
		var audienceCameraPos = new Vector3(500,200,0);
		var behindCameraPos = new Vector3(0,80,200);
		var behindCamLookAt = new Vector3(0,0,-200);
		var worldUp = new Vector3(0,1,0);
		var robotRotation = 0;
		var rotationSpeed = 0.01;
		var T = (new Date).getTime();
		
		// 		Creating the Camera
		var camera = new Camera(35, 1, 0.1, 1000);
		var cameraEntity = goo.world.createEntity('Camera');
		cameraEntity.setComponent(new CameraComponent(camera));
		cameraEntity.addToWorld();
		
		
		// This switch case is to test driving the robot
		/*document.onkeydown = function() {
			switch (window.event.keyCode) {
				case 49:
					if(moveForward){
						console.log(moveForward);
						moveForward = false;
						}
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
		}*/
		
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
						var ent2 = EntityUtils.clone(goo.world, ent);
						ent2.addToWorld();
						
						robot2.transformComponent.attachChild(ent2.getComponent('transformComponent'));	
						
						robot.transformComponent.attachChild(ent.getComponent('transformComponent'));	
						
						//Show only the components that has been added to robot #1
						if(entityName == chassi || entityName == weapon || entityName == wheels || entityName == "Arena")
							EntityUtils.show(ent);
						else
							EntityUtils.hide(ent);

						//Show only the components that has been added to robot #2
						if(entityName == opponentChassi || entityName == opponentWeapon || entityName == opponentWheels)
							EntityUtils.show(ent2);
						else
							EntityUtils.hide(ent2);
							
						if(entityName == "Arena"){
							robot.transformComponent.detachChild(ent.getComponent('transformComponent'));
							ent.transformComponent.setTranslation(0,-19,0);
						}
						
						if(entityName == "Wheels")
							parent.parent.setReady();
						
					})
					.then(null, function(e) {
					// The second parameter of 'then' is an error handling function.
					// We just pop up an error message in case the scene fails to load.
					alert('Failed to load scene: ' + e);
					});
				}
			}
		}());
		
		
		function fire(robot){
			
			var projectileRotation = robot.getRotation();
			var projectilePos = new Vector3(robot.getX()-(35*Math.sin(projectileRotation)), robot.getY()+27, robot.getZ()-(35*Math.cos(projectileRotation)));
			var meshData = ShapeCreator.createSphere(50, 50, 2, 0); 
			var projectile = EntityUtils.createTypicalEntity(goo.world, meshData); 
			var material = Material.createMaterial(ShaderLib.texturedLit, 'BoxMaterial'); 
			projectile.meshRendererComponent.materials.push(material); 
			projectile.transformComponent.setTranslation(projectilePos);
			projectile.addToWorld();
			
			projectile.setComponent(new ScriptComponent({
				run: function (entity) {
					entity.transformComponent.setTranslation(projectilePos.x,projectilePos.y,projectilePos.z)
					
					projectilePos.z = projectilePos.z-(projectileSpeed*Math.cos(projectileRotation));
					projectilePos.x = projectilePos.x-(projectileSpeed*Math.sin(projectileRotation));
				}
			}));
			
			robot.setFire(false);
	
			//EntityUtils.hide(projectile);
		}
		
		// Runtime translations and rotations to the "robot"
		
		//var camScript = new OrbitCamControlScript();
		//cameraEntity.setComponent(new ScriptComponent(camScript));

		
		
		// Load all entitys
		for(var i = 0; i < entityStrings.length; i++){
			loaderModule.importEntity(entityStrings[i]);
		}

		
		robot.setComponent(new ScriptComponent({
			 run: function (entity) { 
					
					if(cameraMode.getView()=="sideView"){
						cameraMode.lookAtRobot();
					}
					
					var currentT = (new Date).getTime();
					var dT = (currentT - T)/1000;
					T = currentT;
					
					for( var i=0;i<parent.parent.currentMatchState.robots.length;i++){
						// console.log("en");
						parent.parent.currentMatchState.robots[i].setdX(parent.parent.nextMatchState.robots[i].getdX()/100);
						parent.parent.currentMatchState.robots[i].setdZ(parent.parent.nextMatchState.robots[i].getdZ()/100);
						parent.parent.currentMatchState.robots[i].setAngularVelocity(parent.parent.nextMatchState.robots[i].getAngularVelocity());
						parent.parent.currentMatchState.robots[i].setX(parent.parent.currentMatchState.robots[i].getX()+dT*parent.parent.currentMatchState.robots[i].getdX());
						parent.parent.currentMatchState.robots[i].setZ(parent.parent.currentMatchState.robots[i].getZ()+dT*parent.parent.currentMatchState.robots[i].getdZ());
						parent.parent.currentMatchState.robots[i].setRotation(parent.parent.currentMatchState.robots[i].getRotation()*dT*parent.parent.currentMatchState.robots[i].getAngularVelocity());
						
						if(parent.parent.currentMatchState.robots[i].getFire())
							fire(parent.parent.currentMatchState.robots[i]);
						
						if(i==parent.parent.yourIndex){

							entity = robot;
						}
						else{
							entity = robot2;
						}
							
						entity.transformComponent.setTranslation(parent.parent.currentMatchState.robots[i].getX(),parent.parent.currentMatchState.robots[i].getY(),parent.parent.currentMatchState.robots[i].getZ());
						entity.transformComponent.setRotation(0,parent.parent.currentMatchState.robots[i].getRotation(),0);
						entity.transformComponent.setUpdated();
						
					} 
				 /*
					 if(moveForward){
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
			}));
		
	}

	init();
	
		
	
	
	
});
