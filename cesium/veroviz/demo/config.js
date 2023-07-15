// This .js file is auto-generated by `createCesium()` from VeRoViz
// The configs for cesium application go to here

function setConfigs() {
    viewer.camera.flyTo({
        destination: Cesium.Rectangle.fromDegrees(-78.808997, 42.994246, -78.685068, 43.092456) 
    });
    viewer.clock.currentTime = Cesium.JulianDate.addSeconds('2023-07-16T08:00:00Z', 0, new Cesium.JulianDate());
    allIDs = [
        'o-Truck0-/veroviz/models/ub_truck.gltf-stationary', 
        'o-truck package 1-/veroviz/models/box_blue.gltf-stationary', 
        'o-truck package 2-/veroviz/models/box_blue.gltf-stationary', 
        'o-Truck1-/veroviz/models/ub_truck.gltf-stationary', 
        'o-Truck0-/veroviz/models/ub_truck.gltf-move', 
        'o-Truck1-/veroviz/models/ub_truck.gltf-move'    
    ];
    orientationIDs = [
        'o-Truck0-/veroviz/models/ub_truck.gltf-move', 
        'o-Truck1-/veroviz/models/ub_truck.gltf-move'    
    ];
    czmlRouteFile = '/veroviz/demo/routes.czml';
    runRoutes(czmlRouteFile, allIDs, orientationIDs);
objectInfo['Truck0-/veroviz/models/ub_truck.gltf'] = {
    label : 'Truck0 (/veroviz/models/ub_truck.gltf)', 
    childModels : ['o-Truck0-/veroviz/models/ub_truck.gltf-stationary', 'o-Truck0-/veroviz/models/ub_truck.gltf-move'],
    scale : 100, 
    minPxSize : 75 
}; 
objectInfo['truck package 1-/veroviz/models/box_blue.gltf'] = {
    label : 'truck package 1 (/veroviz/models/box_blue.gltf)', 
    childModels : ['o-truck package 1-/veroviz/models/box_blue.gltf-stationary'],
    scale : 100, 
    minPxSize : 75 
}; 
objectInfo['truck package 2-/veroviz/models/box_blue.gltf'] = {
    label : 'truck package 2 (/veroviz/models/box_blue.gltf)', 
    childModels : ['o-truck package 2-/veroviz/models/box_blue.gltf-stationary'],
    scale : 100, 
    minPxSize : 75 
}; 
objectInfo['Truck1-/veroviz/models/ub_truck.gltf'] = {
    label : 'Truck1 (/veroviz/models/ub_truck.gltf)', 
    childModels : ['o-Truck1-/veroviz/models/ub_truck.gltf-stationary', 'o-Truck1-/veroviz/models/ub_truck.gltf-move'],
    scale : 100, 
    minPxSize : 75 
}; 
    registerObjects(objectInfo); 
}