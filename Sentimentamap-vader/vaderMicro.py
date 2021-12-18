from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer
from flask import Flask, jsonify
from flask_restful import Resource, Api, reqparse
import py_eureka_client.eureka_client as eureka_client
import requests
from datetime import datetime
from dateutil.parser import parse
from deep_translator import (GoogleTranslator)

app = Flask(__name__)
api = Api(app)

your_rest_server_port = 8200
# The flowing code will register your server to eureka server and also start to send heartbeat every 30 seconds
eureka_client.init(eureka_server="http://localhost:8761",
                   app_name="vader-service",
                   instance_ip_network="127.0.0.1",
                   instance_port=your_rest_server_port)


analyzer = SentimentIntensityAnalyzer()
parser = reqparse.RequestParser()
parser.add_argument('tweetText', location="json")
parser.add_argument('createdAt', location="json")
parser.add_argument('coordinates', location="json")
parser.add_argument('matchingRules', location="json")


def formatTwitterDate(createdAt):
    createdAt = createdAt.replace("[", "").replace("]", "").replace(",", "")
    f = "%Y %m %d %H %M %S"
    createdAt = datetime.strptime(createdAt, f)
    createdAt = createdAt.isoformat()
    return createdAt


class SentimentAnalyize(Resource):
    def post(self):
        args = parser.parse_args()
        message = args.tweetText
        print(message)
        message = GoogleTranslator(source='auto', target='en').translate(text=message)
        print(message)
        score = analyzer.polarity_scores(message)
        dateFormatted = formatTwitterDate(args.createdAt)
        tweetObj = {"sentiment": score, "createdAt" : dateFormatted, "coordinates": args.coordinates, "matchingRules" : args.matchingRules}
        returnedObj = requests.post("http://localhost:8700/api/v1/tweetdb/post", json=tweetObj)
        print(returnedObj.text)

api.add_resource(SentimentAnalyize, "/analyze")

if __name__ == '__main__':
    app.run(host="0.0.0.0", port="8200")