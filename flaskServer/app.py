import veroviz as vrv
import os
import pandas as pd
import numpy as np
from flask import Flask, request, jsonify
from flask_cors import CORS
import codeing
import cesium_demo_python
app = Flask(__name__)
CORS(app)
data=5
@app.route('/init-data', methods=['POST'])
def init_data():
    data = request.get_json()
    print('Received data:', data)
    response = {
        'message': 'Data received successfully',
    }
    # data=codeing.extracter(data);
    cesium_demo_python.runner(1)
    return jsonify(response)

if __name__ == '__main__':
    app.run(debug=True)
