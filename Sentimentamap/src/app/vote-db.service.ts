import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class VoteDBService {

  private baseURL = "http://localhost:8800/api/v1/voteDB";

  constructor(private http:HttpClient) { }

  async getVoteData(): Promise<any>{
    return this.http.get(
      `${this.baseURL}/tallies`,
    ).toPromise();
  }

  async castVote(choice:string, category:string): Promise<any>{
    let params = new HttpParams()
    params = params.append('choice', choice)
    params = params.append('category', category)
    return this.http.post(
      `${this.baseURL}/vote`,
      {params: params} 
    ).toPromise();
  }
}
