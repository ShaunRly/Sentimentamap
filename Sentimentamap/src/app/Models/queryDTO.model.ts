export class QueryDTO{
    public get dateTImeEnd(): String {
        return this._dateTImeEnd;
    }
    public set dateTImeEnd(value: String) {
        this._dateTImeEnd = value;
    }
    public get dateTimeStart(): String {
        return this._dateTimeStart;
    }
    public set dateTimeStart(value: String) {
        this._dateTimeStart = value;
    }
    public get rule(): String {
        return this._rule;
    }
    public set rule(value: String) {
        this._rule = value;
    }
    constructor(
        private _rule: String,
        private _dateTimeStart: String,
        private _dateTImeEnd: String
    ){}
}