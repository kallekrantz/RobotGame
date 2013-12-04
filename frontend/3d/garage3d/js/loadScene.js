var changeInProgress = false;
var chassi = parent.parent.components[0];
var wheel = parent.parent.components[1];
var weapon = parent.parent.components[2];
var changedPart;
var oldPart;

function updatedPart(){
		//HÄMTAR IN DATAN FRÅN GLOBAL VARIABLES
		if(chassi != parent.parent.components[0]){
			oldPart = chassi;
			chassi = parent.parent.components[0];
			return ;
		}
		if(wheel != parent.parent.components[1]){
			oldPart = wheel;
			wheel = parent.parent.components[1];
			return ;
		}
		if(weapon != parent.parent.components[2]){
			oldPart = weapon;
			weapon = parent.parent.components[2];
			return ;
		}
		//alert(changed);
		//if(changed == "body")
		//changeInProgress = false;
}

function hasChanged(changedVal){
	updatedPart();
	changedPart = changedVal;
	changeInProgress = true;
}

function getGoo(goo){
	globalGoo = goo;
	//console.log(globalGoo.entityManager.getEntities());
}


require([
	'goo/entities/GooRunner',
	'goo/entities/EntityUtils',
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
	'goo/renderer/shaders/ShaderLib'

], function (
	GooRunner,
	EntityUtils,
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
	ShaderLib
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
		
		//var entityUtils = new EntityUtils();
		var worldUp = new Vector3(0,1,0);
		var cameraLookAt = new Vector3(0,0,0);
		var cameraPos = new Vector3(0, 140, 220);
		var entityStrings = new Array();
		entityStrings[0] = "Chassi1";
		entityStrings[1] = "Chassi2";
		entityStrings[2] = "Laser";
		entityStrings[3] = "Wheels";
		//entityStrings[2] = "Box";
		
		// The Loader takes care of loading data from a URL...
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
						
						//gets the entity by its referance
						var objectReference = "entities/Cylinder.entity";
						getGoo(goo.world);
						var cog = loader.getCachedObjectForRef("CogWheel/entities/RootNode.entity");
						if(entityName == "CogWheel"){
							cog.setComponent(new ScriptComponent({
							run: function (entity) {
								entity.transformComponent.setRotation(
								0,
								goo.world.time*1.2,
								0);
								entity.transformComponent.setUpdated();
							}}));
						}
						else{
							var ent = loader.getCachedObjectForRef(entityName + "/entities/RootNode.entity");
							
							//console.log(entityName);
							//console.log(chassi);
							EntityUtils.hide(ent);
							if(entityName == chassi || entityName == weapon || entityName == wheel){
							EntityUtils.show(ent);
							}
							cog.transformComponent.attachChild(ent.getComponent('transformComponent'));
						}
						//console.log(objectReference);
						// push to the entity array
						//loadedEntities.push(entity);
						
						//console.log(loader.getCachedObjectForRef(entityName+"/entities/RootNode.entity"));
						
						
						//console.log(entity);
						
					})
					.then(null, function(e) {
					// The second parameter of 'then' is an error handling function.
					// We just pop up an error message in case the scene fails to load.
					alert('Failed to load scene: ' + e);
					});
				}
			}
		}());
		
		var controlVision = (function() {
			return{ // exposed to public
			
				//Returns the array of entities that have been loaded
				showEntity: function(entity){
					EntityUtil.show(entity);
				},
				
				hideEntity: function(entity){
					EntityUtils.hide(entity);
				}
			}
		});
		
		var camera = new Camera(35, 1, 0.1, 1000);
		//camera.lookAt(cameraLookAt, worldUp);

		var cameraEntity = goo.world.createEntity('Camera');
		
		cameraEntity.setComponent(new CameraComponent(camera));
		cameraEntity.transformComponent.transform.translation.set(cameraPos);
		cameraEntity.transformComponent.lookAt(cameraLookAt, worldUp);

		
		//var camScript = new OrbitCamControlScript();
		//cameraEntity.setComponent(new ScriptComponent(camScript));
		
						
		cameraEntity.addToWorld();
		
				loaderModule.importEntity("CogWheel");
					for(var i = 0; i < entityStrings.length; i++){
						loaderModule.importEntity(entityStrings[i]);
					}
		
		var meshData = ShapeCreator.createBox(1, 1, 1); 
		var boxEntity = EntityUtils.createTypicalEntity(goo.world, meshData); 
		var material = Material.createMaterial(ShaderLib.texturedLit, 'BoxMaterial'); 
		boxEntity.meshRendererComponent.materials.push(material); 
		boxEntity.transformComponent.setTranslation(0,7,0);
		boxEntity.addToWorld();
		EntityUtils.hide(boxEntity);
		boxEntity.setComponent(new ScriptComponent({
					run: function (boxEntity) {
						
							if(changeInProgress){
								//alert("oldPart: "+oldPart);
								//alert("changedPart: "+changedPart);
								if(loader.getCachedObjectForRef(changedPart+"/entities/RootNode.entity").hidden){
									
									EntityUtils.show(loader.getCachedObjectForRef(changedPart+"/entities/RootNode.entity"));
									if(oldPart != null){
										EntityUtils.hide(loader.getCachedObjectForRef(oldPart+"/entities/RootNode.entity"));
									}
								}
								else{
									//EntityUtils.hide(loader.getCachedObjectForRef(changedPart+"/entities/RootNode.entity"));
									//alert("wrong input");
								}
								changeInProgress = false;
							}
						}
					}));
		
		if(changeInProgress){
			alert("ww");
		}
		
		
		/*
		$(document).keydown(function(e) {
			
			if(e.keydown == 49){
			
					alert("h2j");
			
					console.log(loader);
					var snurra = loader.getCachedObjectForRef("entities/Cylinder.entity");
					snurra.setComponent(new ScriptComponent({
						run: function (entity) {
							 entity.transformComponent.setRotation(
							 0,
							 goo.world.time*1.2,
							 0);
							 entity.transformComponent.setUpdated();

						}
					}));
			}
		
		
			switch (window.event.keyCode) {
				case 49:
					console.log(loader);
					var snurra = loader.getCachedObjectForRef("entities/Cylinder.entity");
					snurra.setComponent(new ScriptComponent({
						run: function (entity) {
							 entity.transformComponent.setRotation(
							 0,
							 goo.world.time*1.2,
							 0);
							 entity.transformComponent.setUpdated();

						}
					}));
					break;
				case 50:
					
					alert("hej");
			
					break;
				case 51:
					var cyl = loader.getCachedObjectForRef("entities/Cylinder.entity");
					EntityUtils.hide(cyl);
					break;
				case 52:
					var cyl = loader.getCachedObjectForRef("entities/Cylinder.entity");
					EntityUtils.show(cyl);
					break;
			}
			
		});
		*/
		
	}

	init();
});

