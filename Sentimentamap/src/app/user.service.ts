import { LoginDetails } from './Models/LoginDetails';
import { UserCreationDTO } from './Models/UserCreationDTO';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, retry} from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseURL = "http://localhost:8600/api/v1/userDB";
  private userStatus = 0;
  private bearerToken= 'ssf4r22fsadf'
  private baseHeaders:HttpHeaders =  new HttpHeaders({
      'Content-Type':  'application/json;charset=UTF-8',
      Authorization: this.bearerToken
    })


  constructor(private http:HttpClient) { }

  async register(username:String, password:String, email:String):Promise<any>{
    let newUser:UserCreationDTO = new UserCreationDTO(username, password, email);
    try{
      console.log(newUser.password)
      const response = await this.http.post<UserCreationDTO>(`${this.baseURL}/new_user`,JSON.stringify(newUser),{headers:this.baseHeaders, observe:'response'}).toPromise();
      this.userStatus=response.status;
    }catch(e){
      console.log(e)
    }
  }

  async login(username:String, password:String){
    let loginDetails:LoginDetails = new LoginDetails(username, password)
    try{
      const response = await this.http.post(`${this.baseURL}/users/login`,loginDetails,{headers:this.baseHeaders, observe:'response'}).toPromise();
      let tmpToken = response.headers.get('pragma');
      this.bearerToken = tmpToken? tmpToken : '';
      this.storeData();
    }catch(e){
      console.log(e)
    }
  }

  async getUserDetails(username:string, password:string){
    let params = new HttpParams()
    params = params.append('username', username)
    params = params.append('password', password)
    let loginStatus:any = await this.http.get(
      `${this.baseURL}/user_details`,
      {params: params, headers: this.baseHeaders}
    ).toPromise();
    if (loginStatus == true){
      this.storeData()
      this.userStatus = 1;
    }
    return loginStatus;
  }

  getUserStatus():number{
    this.restoreData()
    return this.userStatus;
  }

  private storeData(){
    window.localStorage.clear();
    window.localStorage.setItem('token', "123");
  }
  private restoreData(){
    let tokenTmp: string | null;
    let userIdTmp: string | null;
    tokenTmp = window.localStorage.getItem('token');
    console.log(tokenTmp)
    userIdTmp = window.localStorage.getItem('id');
    this.bearerToken = tokenTmp ? tokenTmp : '';
    if(this.bearerToken == "123"){
      this.userStatus = 1;
    }
    return this.userStatus;
  }

  logout(){
    window.localStorage.clear();
    this.userStatus = 0;
  }


}
