from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer
from flask import Flask, jsonify
from flask_restful import Resource, Api, reqparse
import py_eureka_client.eureka_client as eureka_client
app = Flask(__name__)
api = Api(app)

# your_rest_server_port = 8200
# # The flowing code will register your server to eureka server and also start to send heartbeat every 30 seconds
# eureka_client.init(
#     eureka_server="http://your-eureka-server-peer1,http://your-eureka-server-peer2",
#     app_name="your_app_name",
#     instance_port=your_rest_server_port)

analyzer = SentimentIntensityAnalyzer()
parser = reqparse.RequestParser()
parser.add_argument('tweetText', location="json")
parser.add_argument('createdAt', location="json")
parser.add_argument('coordinates', location="json")
parser.add_argument('matchingRules', location="json")

class SentimentAnalyize(Resource):
    def post(self):
        args = parser.parse_args()
        message =  args.tweetText
        score = analyzer.polarity_scores(message)
        return jsonify(score, args.createdAt, args.coordinates, args.matchingRules)

api.add_resource(SentimentAnalyize, "/analyze")

if __name__ == '__main__':
    app.run(debug=True)