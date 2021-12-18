export class VoteOption{
    public get voteTally(): number {
        return this._voteTally;
    }
    public set voteTally(value: number) {
        this._voteTally = value;
    }
    public get optionName(): string {
        return this._optionName;
    }
    public set optionName(value: string) {
        this._optionName = value;
    }
    constructor(
        private _optionName: string,
        private _voteTally: number
    ){}
}