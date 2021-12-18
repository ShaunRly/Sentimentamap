export class Bubble{
    public get matchingRule(): string {
        return this._matchingRule;
    }
    public set matchingRule(value: string) {
        this._matchingRule = value;
    }
    public get count(): number {
        return this._count;
    }
    public set count(value: number) {
        this._count = value;
    }
    public get compound(): number {
        return this._compound;
    }
    public set compound(value: number) {
        this._compound = value;
    }
    constructor(
        private _compound: number,
        private _count: number,
        private _matchingRule: string
    ){}
}