import { VoteOption } from './VoteOption.model';
export class VoteCategory{
    public get results(): VoteOption[] {
        return this._results;
    }
    public set results(value: VoteOption[]) {
        this._results = value;
    }
    
    public get category(): string {
        return this._category;
    }
    public set category(value: string) {
        this._category = value;
    }
    constructor(
        private _category: string,
        private _results: VoteOption[]
    ){}
}