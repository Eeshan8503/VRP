

import veroviz as vrv

import os
import pandas as pd
import numpy as np
vrv.checkVersion()
DATA_PROVIDER = 'ORS-online'
DATA_PROVIDER_ARGS = {
    'APIkey'       : os.environ['ORSKEY'],
    'databaseName' : None
}
CESIUM_DIR = os.environ['CESIUMDIR']
# Nodes: 
nodesArray = [ 
    {'id': 0, 'lat': 43.001742, 'lon': -78.787034, 'altMeters': 0.0, 'nodeName': 'Depot', 'nodeType': 'depot', 'popupText': 'Depot', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'home', 'leafletColor': 'red', 'leafletIconText': '0', 'cesiumIconType': 'pin', 'cesiumColor': 'red', 'cesiumIconText': '0', 'elevMeters': None},
    {'id': 1, 'lat': 43.015717, 'lon': -78.816851, 'altMeters': 0.0, 'nodeName': 'Cust1', 'nodeType': 'customer', 'popupText': 'Cust1', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'star', 'leafletColor': 'green', 'leafletIconText': '1', 'cesiumIconType': 'pin', 'cesiumColor': 'green', 'cesiumIconText': '1', 'elevMeters': None},
    {'id': 2, 'lat': 43.031084, 'lon': -78.791655, 'altMeters': 0.0, 'nodeName': 'Cust2', 'nodeType': 'customer', 'popupText': 'Cust2', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'star', 'leafletColor': 'green', 'leafletIconText': '2', 'cesiumIconType': 'pin', 'cesiumColor': 'green', 'cesiumIconText': '2', 'elevMeters': None},
    {'id': 3, 'lat': 43.010989, 'lon': -78.749357, 'altMeters': 0.0, 'nodeName': 'Cust3', 'nodeType': 'customer', 'popupText': 'Cust3', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'star', 'leafletColor': 'green', 'leafletIconText': '3', 'cesiumIconType': 'pin', 'cesiumColor': 'green', 'cesiumIconText': '3', 'elevMeters': None},

]
nodesDF = pd.DataFrame(nodesArray)
lat_lon = [(node['lat'], node['lon']) for node in nodesArray]
lat_lon = pd.DataFrame(lat_lon)
lat_lon
[timeSec, distMeters] = vrv.getTimeDist2D(
    nodes        = nodesDF,
    routeType    = 'truck',
    dataProvider = 'ORS-online',
        dataProviderArgs = {
            'APIkey'       : os.environ['ORSKEY']
        })

distMeters

orderedNames = [(i,j) for i in range(4) for j in range(4)]

dataMatrix = np.array([distMeters[i] for i in orderedNames]).reshape(4,-1)

print(dataMatrix)
assignmentsDF = vrv.initDataframe('assignments')


# ### Delivery Truck
# - Clockwise path around the "lower triangle" of nodes;
# - Follows the road network;
# - Starts 30-seconds after the cars;
# - Stops for 30 seconds at customer nodes to deliver blue packages.

# In[20]:


# truck
# lower triangle, following road, stopping to deliver blue packages

truck_route = [0, 1, 3, 0]

myObjectID = 'Truck'
myModel    = 'veroviz/models/ub_truck.gltf'
myColor    = 'darkblue'
myArcStyle = 'dashed'

startTime = 30.0   # We'll delay the truck to let cars get started first.
odID = 0
truckPkgID = 0

for i in range(0, len(truck_route)-1):
    startNode = truck_route[i]
    endNode   = truck_route[i+1]
    
    # Update the assignments associated with this arc
    [assignmentsDF, endTimeSec] = vrv.addAssignment2D(
        initAssignments  = assignmentsDF,
        odID             = odID,
        objectID         = myObjectID, 
        modelFile        = myModel,
        startLoc         = list(nodesDF[nodesDF['id'] == startNode][['lat', 'lon']].values[0]),
        endLoc           = list(nodesDF[nodesDF['id'] == endNode][['lat', 'lon']].values[0]),
        startTimeSec     = startTime,
        routeType        = 'fastest',
        leafletColor     = myColor, 
        leafletStyle     = myArcStyle, 
        cesiumColor      = myColor, 
        cesiumStyle      = myArcStyle, 
        dataProvider     = DATA_PROVIDER,
        dataProviderArgs = DATA_PROVIDER_ARGS) 
        
    odID += 1
    
    # Update the time
    startTime = endTimeSec
    
    # Add loitering for service
    assignmentsDF = vrv.addStaticAssignment(
        initAssignments = assignmentsDF, 
        odID            = odID, 
        objectID        = myObjectID, 
        modelFile       = myModel, 
        loc             = list(nodesDF[nodesDF['id'] == endNode][['lat', 'lon']].values[0]),
        startTimeSec    = startTime,
        endTimeSec      = startTime + 30)
        
    odID += 1
    
    # Update the time again
    startTime = startTime + 30

    # Add a package at all non-depot nodes:
    if (endNode != 0):
        truckPkgID += 1
        assignmentsDF = vrv.addStaticAssignment(
            initAssignments = assignmentsDF, 
            odID            = 0, 
            objectID        = 'truck package %d' % (truckPkgID),
            modelFile       = 'veroviz/models/box_blue.gltf', 
            loc             = list(nodesDF[nodesDF['id'] == endNode][['lat', 'lon']].values[0]),
            startTimeSec    = startTime,
            endTimeSec      = -1)



assignmentsDF.tail()





vrv.createLeaflet(nodes = nodesDF,
                  arcs  = assignmentsDF)


# ---
# ## Generate Cesium
# 
# We will now generate the files necessary to view our solution on a 3D map.
# 
# - The `createCesium()` function will save these files in a sub-directory where the Cesium application is installed on your machine.
# - For example, suppose that `cesiumDir = '/home/user/cesium'`.  If `problemDir = 'veroviz/demo`, then all files will be saved within `/home/user/cesium/veroviz/demo`.  
# - See https://veroviz.org/docs/veroviz.createCesium.html for details.

# In[25]:


vrv.createCesium(assignments = assignmentsDF, 
                 nodes       = nodesDF, 
                 startDate   = None, 
                 startTime   = '08:00:00', 
                 postBuffer  = 30, 
                 cesiumDir   = CESIUM_DIR,        
                 problemDir  = 'veroviz/demo')      # <-- a sub-directory of cesiumDir    


# ---
# ## We are now ready to view our solution.
# 
# 1. Make sure you have a 'node.js' server running:
#     1. Open a terminal window.
#     2. Change directories to the location where Cesium is installed.  For example, `cd ~/cesium`.
#     3. Start a 'node.js' server:  `node server.cjs`
# 2. Visit http://localhost:8080/veroviz in your web browser.
# 3. Use the top left icon to select `;veroviz;demo.vrv`, which will be located in the `veroviz/demo` subdirectory of Cesium.
