var robots = new Array(new NetworkRobot(0,0,0,0),new NetworkRobot(0,0,0,0));
match = new NetworkMatch(robots);

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
	'goo/entities/components/LightComponent'

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
	LightComponent
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
		
		var speed = 1.5;
		var moveForward = false;
		var turnRight = false;
		var turnLeft = false;
		var moveBackward = false;
		//var robotPos = new Vector3(0,0,0);
		var robotPos = new Vector3(match.robots[0].getX(), 0, match.robots[0].getY());
		var robotLookAt = new Vector3(0,0,-10);
		var camLookAt = new Vector3(robotPos.x,robotPos.y,robotPos.z-200);
		var worldUp = new Vector3(0,1,0);
		var robotRotation = 0;
		var rotationSpeed = 0.01;
		var camera = new Camera(35, 1, 0.1, 1000);
		var cameraEntity = goo.world.createEntity('Camera');
		cameraEntity.setComponent(new CameraComponent(camera));
		cameraEntity.addToWorld();
		
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
					
			}
		}

		// The Loader takes care of loading data from a URL...
		var loader = new DynamicLoader({world: goo.world, rootPath: 'res'});
		loader.loadFromBundle('project.project', 'arena.bundle').then(function(configs) {
			loader.loadFromBundle('project.project', 'robot.bundle').then(function(configs) {
				// This function is called when the project has finished loading.
				
				//console.log(loader.getCachedObjectForRef("FullTank2/entities/RootNode.entity"));
				var arena = loader.getCachedObjectForRef("Arena/entities/RootNode.entity");
				var robot = loader.getCachedObjectForRef("FullTank2/entities/RootNode.entity");
	
				robot.transformComponent.setTranslation(robotPos);
				arena.transformComponent.setTranslation(0,-29,0);
				goo.renderer.domElement.id = 'goo';
				document.body.appendChild(goo.renderer.domElement);
				
				goo.startGameLoop();
				
			
				
				console.log(cameraEntity);
				cameraEntity.transformComponent.setTranslation(0,80,200);
				cameraEntity.transformComponent.lookAt(camLookAt,worldUp);
				robot.transformComponent.attachChild(cameraEntity.getComponent('transformComponent'));
				
				robot.setComponent(new ScriptComponent({
					 run: function (entity) {
						 if(moveForward){
							 entity.transformComponent.setTranslation(
							 robotPos.x,
							 robotPos.y,
							 robotPos.z);
			
							 robotPos.z = robotPos.z-(speed*Math.cos(robotRotation));
							 robotPos.x = robotPos.x-(speed*Math.sin(robotRotation));
							 entity.transformComponent.setUpdated();
							 
						 }
							 
						 if(moveBackward){
							 entity.transformComponent.setTranslation(
							 robotPos.x,
							 robotPos.y,
							 robotPos.z);
	
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
						 }
					}
				}));
				
				//var camScript = new OrbitCamControlScript();
				//cameraEntity.setComponent(new ScriptComponent(camScript));

				
				//console.log(goo.getComponent('cameraComponent'));
				
			})
		})
		.then(null, function(e) {
			// The second parameter of 'then' is an error handling function.
			// We just pop up an error message in case the scene fails to load.
			alert('Failed to load scene: ' + e);
		});
	}

	init();
	
		
	
	
	
});
