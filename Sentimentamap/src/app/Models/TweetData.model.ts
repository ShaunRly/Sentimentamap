import { Sentiment } from './Sentiment.model';

export class TweetData{
    public get createdAt(): Date {
        return this._createdAt;
    }
    public set createdAt(value: Date) {
        this._createdAt = value;
    }
    public get matchingRules(): String {
        return this._matchingRules;
    }
    public set matchingRules(value: String) {
        this._matchingRules = value;
    }
    public get sentiment(): Sentiment {
        return this._sentiment;
    }
    public set sentiment(value: Sentiment) {
        this._sentiment = value;
    }
    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }

    constructor(
        private _id: number,
        private _sentiment: Sentiment,
        private _matchingRules: String,
        private _createdAt: Date
    ){}
}