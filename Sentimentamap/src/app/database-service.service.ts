import { TweetData } from './Models/TweetData.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DatabaseServiceService {

  private baseURL = "http://localhost:8700/api/v1/tweetdb";
  private rule = "place";

  constructor(private http:HttpClient) { }

  setRule(rule:string){
    this.rule = rule;
  }

  async getHalfHourDataPoints(dateTimeStart:string, dateTimeEnd:string): Promise<any>{
    let params = new HttpParams()
    params = params.append('rule', this.rule)
    params = params.append('dateTimeStart', dateTimeStart)
    params = params.append('dateTimeEnd', dateTimeEnd)
    return this.http.get(
      `${this.baseURL}/bubbleCategoryQuery`,
      {params: params} 
    ).toPromise();
  }

  async getTrackedTopics(dateTimeStart:string, dateTimeEnd:string): Promise<any>{
    let params = new HttpParams()
    params = params.append('dateTimeStart', dateTimeStart)
    params = params.append('dateTimeEnd', dateTimeEnd)
    return this.http.get(
      `${this.baseURL}/tracked`,
      {params: params} 
    ).toPromise();
  }

  async getTweetsForTopic(dateTimeStart:string, dateTimeEnd:string): Promise<any>{
    let params = new HttpParams()
    params = params.append('rule', this.rule)
    params = params.append('dateTimeStart', dateTimeStart)
    params = params.append('dateTimeEnd', dateTimeEnd)
    return this.http.get(
      `${this.baseURL}/topicQuery`,
      {params: params} 
    ).toPromise();
  }

  // getPersonDetail(id: number): Observable<any>{
  //   return this.http.get(`${this.baseURL}/personId/${id}`);
  // }

  // getPersonSearchResult(name: string): Observable<any>{
  //   return this.http.get(`${this.baseURL}/person/${name}`);
  // }
}
