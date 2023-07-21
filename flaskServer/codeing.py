import re
# import app
import numpy as np
# Open the file in read mode
def extracter(file_contents):  
    try:
        # Extract the nodesArray using regular expression
        pattern = r"nodesArray = \[(.*?)\]"
        match = re.search(pattern,str(file_contents) , re.DOTALL)
        i=0;
        if match:
            # print (i)
            i+=1
            nodes_array_data = match.group(1)
            # Remove indentation from the data
            nodes_array_data = re.sub(r'\s+', ' ', nodes_array_data)
            # Assign the data to the nodesArray variable
            nodesArray = np.array(eval(nodes_array_data))
            print(nodesArray)  # Print the extracted data
            return nodesArray
        else:
            print("No 'nodesArray' data found in the file.")
    except FileNotFoundError:
        print(f"File '{file_path}' not found.")
    except IOError:
        print(f"Error reading file '{file_path}'.")

# extracter("# Bounding Region: \n# [There is no bounding region defined] \n \n# Nodes: \nnodesArray = [ \n    {'id': 0, 'lat': 42.99716743756055, 'lon': -78.66004943847658, 'altMeters': 0.0, 'nodeName': 'd11', 'nodeType': 'depot', 'popupText': 'd11', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'info-sign', 'leafletColor': 'blue', 'leafletIconText': '0', 'cesiumIconType': 'pin', 'cesiumColor': 'blue', 'cesiumIconText': '0', 'elevMeters': None},\n    {'id': 1, 'lat': 42.960508373698126, 'lon': -78.66210937500001, 'altMeters': 0.0, 'nodeName': 'c12', 'nodeType': 'customer', 'popupText': 'c12', 'leafletIconPrefix': 'glyphicon', 'leafletIconType': 'info-sign', 'leafletColor': 'orange', 'leafletIconText': '1', 'cesiumIconType': 'pin', 'cesiumColor': 'orange', 'cesiumIconText': '1', 'elevMeters': None},\n]\nmyNodes = pd.DataFrame(nodesArray)\n\n# Arcs/Routes: \narcsArray = [ \n    {'odID': 0, 'objectID': None,'startLat': 42.99716743756055, 'startLon': -78.66004943847658, 'endLat': 42.960508373698126, 'endLon': -78.66210937500001, 'leafletColor': 'orange', 'leafletWeight': 5, 'leafletStyle': 'solid', 'leafletOpacity': 0.8, 'leafletCurveType': 'straight', 'leafletCurvature': 0, 'useArrows': True, 'cesiumColor': 'orange', 'cesiumWeight': 5, 'cesiumStyle': 'solid', 'cesiumOpacity': 0.8, 'popupText': None,'startElevMeters': None,'endElevMeters': None},\n    {'odID': 1, 'objectID': None,'startLat': 42.960508373698126, 'startLon': -78.66210937500001, 'endLat': 42.99716743756055, 'endLon': -78.66004943847658, 'leafletColor': 'orange', 'leafletWeight': 5, 'leafletStyle': 'solid', 'leafletOpacity': 0.8, 'leafletCurveType': 'straight', 'leafletCurvature': 0, 'useArrows': True, 'cesiumColor': 'orange', 'cesiumWeight': 5, 'cesiumStyle': 'solid', 'cesiumOpacity': 0.8, 'popupText': None,'startElevMeters': None,'endElevMeters': None},\n]\nmyArcs = pd.DataFrame(arcsArray)\n\n# Text Annotations: \n# [There are no text annotations defined] \n \n")