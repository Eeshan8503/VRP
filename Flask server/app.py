from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

@app.route('/init-data', methods=['POST'])
def init_data():
    data = request.get_json()
    print('Received data:', data)
    response = {
        'message': 'Data received successfully',
        'data': data
    }
    return jsonify(response)

if __name__ == '__main__':
    app.run(debug=True)
