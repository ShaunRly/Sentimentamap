import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MapdataService {

  private baseURL = "http://localhost:8900/api/v1/geo";

  constructor(private http:HttpClient) { }

  async getMapData(dateTimeStart:string, dateTimeEnd:string): Promise<any>{
    let params = new HttpParams()
    params = params.append('dateTimeStart', dateTimeStart)
    params = params.append('dateTimeEnd', dateTimeEnd)
    return this.http.get(
      `${this.baseURL}/data`,
      {params: params} 
    ).toPromise();
  }
}
