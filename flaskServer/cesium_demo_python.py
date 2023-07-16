
import veroviz as vrv
import os
import pandas as pd
import numpy as np
import warnings
import requests
import subprocess
import socket
import threading
import time
import json
warnings.filterwarnings("ignore")
vrv.checkVersion()
DATA_PROVIDER = 'ORS-online'
DATA_PROVIDER_ARGS = {
    'APIkey'       : os.environ['ORSKEY'],
    'databaseName' : None
}
CESIUM_DIR = os.environ['CESIUMDIR']
# Nodes: 
# [{},{}]
class truck:
    truck_route=[]
    myObjectID='Truck'
    myModel='veroviz/models/ub_truck.gltf'
    myColor='darkblue'
    myArcStyle='dashed'
    startTime=30.0
    odID=0
    truckPkgID=0
    def __init__(self,truck_route=[],myObjectID='Truck',myModel='veroviz/models/ub_truck.gltf',myColor='darkblue',myArcStyle='dashed',startTime=30.0,odID=0,truckPkgID=0):
        self.truck_route=truck_route
        self.myObjectID=myObjectID
        self.myModel=myModel
        self.myColor=myColor
        self.myArcStyle=myArcStyle
        self.startTime=startTime
        self.odID=odID
        self.truckPkgID=truckPkgID

def check_server(host, port):
    try:
        # Create a TCP socket
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.settimeout(2)  # Set a timeout for the connection

        # Attempt to connect to the server
        result = sock.connect_ex((host, port))

        if result == 0:
            print(f"The server at {host}:{port} is up and running")
            return True;
        else:
            print(f"The server at {host}:{port} is not reachable")
            return False;
        # Close the socket
        sock.close()

    except socket.error as e:
        print(f"Error occurred while checking the server: {e}")

def worker():
    print("WORKER")
    command = "cd ../cesium/ && node server.js"
    result = subprocess.run(command, shell=True, capture_output=True, text=True,)

def setup():
    thread = threading.Thread(target=worker)
    print("MAIN")
    thread.start()
    while(check_server('localhost', 8081)!=True):
        print("Waiting for server to start")
        time.sleep(1)

    print("SERVER ACTIVE")
        
    command = "cd ../cesium/ && npm run start-electron"
    result = subprocess.run(command, shell=True, capture_output=True, text=True)

def runner(data):
    nodesArray = [ 
    {'id': 0, 'lat': 43.06043086875781, 'lon': -78.77059936523439, 'altMeters': 0.0, 'nodeName': 'd11', 'nodeType': 'depot', 'popupText': 'd11', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'info-sign', 'leafletColor': 'red', 'leafletIconText': '0', 'cesiumIconType': 'pin', 'cesiumColor': 'red', 'cesiumIconText': '0', 'elevMeters': None},
    {'id': 1, 'lat': 43.05842514826838, 'lon': -78.69506835937501, 'altMeters': 0.0, 'nodeName': 'd22', 'nodeType': 'depot', 'popupText': 'd22', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'info-sign', 'leafletColor': 'red', 'leafletIconText': '1', 'cesiumIconType': 'pin', 'cesiumColor': 'red', 'cesiumIconText': '1', 'elevMeters': None},
    {'id': 2, 'lat': 43.00424589515733, 'lon': -78.72940063476564, 'altMeters': 0.0, 'nodeName': 'c13', 'nodeType': 'customer', 'popupText': 'c13', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'info-sign', 'leafletColor': 'blue', 'leafletIconText': '2', 'cesiumIconType': 'pin', 'cesiumColor': 'blue', 'cesiumIconText': '2', 'elevMeters': None},
    {'id': 3, 'lat': 43.02532128785062, 'lon': -78.78845214843751, 'altMeters': 0.0, 'nodeName': 'c24', 'nodeType': 'customer', 'popupText': 'c24', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'info-sign', 'leafletColor': 'blue', 'leafletIconText': '3', 'cesiumIconType': 'pin', 'cesiumColor': 'blue', 'cesiumIconText': '3', 'elevMeters': None},
]

    myNodes = pd.DataFrame(nodesArray)
    print(nodesArray)
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
    dataMatrix=dataMatrix.tolist()
    print(dataMatrix)
    assignmentsDF = vrv.initDataframe('assignments')
    assignmentsDF1 = vrv.initDataframe('assignments')


    # ### Delivery Truck
    # - Clockwise path around the "lower triangle" of nodes;
    # - Follows the road network;
    # - Starts 30-seconds after the cars;
    # - Stops for 30 seconds at customer nodes to deliver blue packages.

    # In[20]:


    # truck
    # lower triangle, following road, stopping to deliver blue packages
    num_of_trucks=2
    truckArray=[];
    payload = {
           "distanceArray": dataMatrix,  
        }

    response = requests.post('http://localhost:8080/',json=payload)
    print(response.json())
    truck_routes = response.json()['routes']
    for i in range(0,num_of_trucks):
        truckArray.append(truck(truck_route=truck_routes[i],myObjectID='Truck'+str(i),))
    
    
    # truck_route1 = [0, 1, 3, 0]

    # myObjectID1 = 'Truck'
    # myModel1    = 'veroviz/models/ub_truck.gltf'
    # myColor    = 'darkblue'
    # myArcStyle = 'dashed'

    # startTime = 30.0   # We'll delay the truck to let cars get started first.
    # odID = 0
    # truckPkgID = 0

    for j in range(0, num_of_trucks):
        print("Runnig for truck "+str(j)+"\n)")
       
        
        cur_truck=truckArray[j]
        for i in range(len(cur_truck.truck_route)-1):
            print(cur_truck.truck_route)
            startNode = cur_truck.truck_route[i]
            endNode = cur_truck.truck_route[i + 1]

            # Update the assignments associated with this arc
            [assignmentsDF, endTimeSec] = vrv.addAssignment2D(
                initAssignments=assignmentsDF,
                odID=cur_truck.odID,
                objectID=cur_truck.myObjectID,
                modelFile=cur_truck.myModel,
                startLoc=list(nodesDF[nodesDF['id'] == startNode][['lat', 'lon']].values[0]),
                endLoc=list(nodesDF[nodesDF['id'] == endNode][['lat', 'lon']].values[0]),
                startTimeSec=cur_truck.startTime,
                routeType='fastest',
                leafletColor=cur_truck.myColor,
                leafletStyle=cur_truck.myArcStyle,
                cesiumColor=cur_truck.myColor,
                cesiumStyle=cur_truck.myArcStyle,
                dataProvider=DATA_PROVIDER,
                dataProviderArgs=DATA_PROVIDER_ARGS)

            cur_truck.odID += 1

            # Update the time
            cur_truck.startTime = endTimeSec

            # Add loitering for service
            assignmentsDF = vrv.addStaticAssignment(
                initAssignments=assignmentsDF,
                odID=cur_truck.odID,
                objectID=cur_truck.myObjectID,
                modelFile=cur_truck.myModel,
                loc=list(nodesDF[nodesDF['id'] == endNode][['lat', 'lon']].values[0]),
                startTimeSec=cur_truck.startTime,
                endTimeSec=cur_truck.startTime + 30)

            cur_truck.odID += 1

            # Update the time again
            cur_truck.startTime = cur_truck.startTime + 30

            # Add a package at all non-depot nodes
            if endNode != 0:
                cur_truck.truckPkgID += 1
                assignmentsDF = vrv.addStaticAssignment(
                    initAssignments=assignmentsDF,
                    odID=0,
                    objectID='truck package %d' % (cur_truck.truckPkgID),
                    modelFile='veroviz/models/box_blue.gltf',
                    loc=list(nodesDF[nodesDF['id'] == endNode][['lat', 'lon']].values[0]),
                    startTimeSec=cur_truck.startTime,
                    endTimeSec=-1)
    ###################################################################################
    # truck
# lower triangle, following road, stopping to deliver blue packages
    # truck
# lower triangle, following road, stopping to deliver blue packages

    # truck_route= [1, 0, 3, 1]

    # myObjectID = 'Truck'
    # myModel    = 'veroviz/models/ub_truck.gltf'
    # myColor    = 'darkblue'
    # myArcStyle = 'dashed'

    # startTime = 30.0   # We'll delay the truck to let cars get started first.
    # odID = 0
    # truckPkgID = 0

    # for i in range(0, len(truck_route)-1):
    #     startNode = truck_route[i]
    #     endNode   = truck_route[i+1]
        
    #     # Update the assignments associated with this arc
    #     [assignmentsDF, endTimeSec] = vrv.addAssignment2D(
    #         initAssignments  = assignmentsDF,
    #         odID             = odID,
    #         objectID         = myObjectID, 
    #         modelFile        = myModel,
    #         startLoc         = list(nodesDF[nodesDF['id'] == startNode][['lat', 'lon']].values[0]),
    #         endLoc           = list(nodesDF[nodesDF['id'] == endNode][['lat', 'lon']].values[0]),
    #         startTimeSec     = startTime,
    #         routeType        = 'fastest',
    #         leafletColor     = myColor, 
    #         leafletStyle     = myArcStyle, 
    #         cesiumColor      = myColor, 
    #         cesiumStyle      = myArcStyle, 
    #         dataProvider     = DATA_PROVIDER,
    #         dataProviderArgs = DATA_PROVIDER_ARGS) 
            
    #     odID += 1
        
    #     # Update the time
    #     startTime = endTimeSec
        
    #     # Add loitering for service
    #     assignmentsDF = vrv.addStaticAssignment(
    #         initAssignments = assignmentsDF, 
    #         odID            = odID, 
    #         objectID        = myObjectID, 
    #         modelFile       = myModel, 
    #         loc             = list(nodesDF[nodesDF['id'] == endNode][['lat', 'lon']].values[0]),
    #         startTimeSec    = startTime,
    #         endTimeSec      = startTime + 30)
            
    #     odID += 1
        
    #     # Update the time again
    #     startTime = startTime + 30

    #     # Add a package at all non-depot nodes:
    #     if (endNode != 0):
    #         truckPkgID += 1
    #         assignmentsDF = vrv.addStaticAssignment(
    #             initAssignments = assignmentsDF, 
    #             odID            = 0, 
    #             objectID        = 'truck package %d' % (truckPkgID),
    #             modelFile       = 'veroviz/models/box_blue.gltf', 
    #             loc             = list(nodesDF[nodesDF['id'] == endNode][['lat', 'lon']].values[0]),
    #             startTimeSec    = startTime,
    #             endTimeSec      = -1)
   

    ###################################################################################


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
    setup()

    
# runner(1)


# Check the return code to see if the command executed successfully





# Usage example

