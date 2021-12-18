export class Sentiment{
    public get pos(): number {
        return this._pos;
    }
    public set pos(value: number) {
        this._pos = value;
    }
    public get neu(): number {
        return this._neu;
    }
    public set neu(value: number) {
        this._neu = value;
    }
    public get neg(): number {
        return this._neg;
    }
    public set neg(value: number) {
        this._neg = value;
    }
    public get compound(): number {
        return this._compound;
    }
    public set compound(value: number) {
        this._compound = value;
    }

    constructor(
        private _compound: number,
        private _neg: number,
        private _neu: number,
        private _pos: number
    ){}
}