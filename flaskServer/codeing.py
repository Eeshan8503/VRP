import re
import app
import numpy as np
# Open the file in read mode
def extracter(file_contents):  
    try:
        # Extract the nodesArray using regular expression
        pattern = r"nodesArray = \[(.*?)\]"
        match = re.search(pattern, file_contents, re.DOTALL)
        if match:
            nodes_array_data = match.group(1)
            # Remove indentation from the data
            nodes_array_data = re.sub(r'\s+', ' ', nodes_array_data)
            # Assign the data to the nodesArray variable
            nodesArray = np.array(eval(nodes_array_data))
            print(nodesArray[0])  # Print the extracted data
            return nodesArray
        else:
            print("No 'nodesArray' data found in the file.")
    except FileNotFoundError:
        print(f"File '{file_path}' not found.")
    except IOError:
        print(f"Error reading file '{file_path}'.")
