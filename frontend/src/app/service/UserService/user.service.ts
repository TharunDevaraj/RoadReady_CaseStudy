import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from 'src/app/dto/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  changePassword(payload: { newPassword: string;  userId: any }) {
    console.log("In service : updateUser method called")
          
                const token = localStorage.getItem('token');
          
                const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
      
          return this.http.put(this.baseUrl+'updatePassword',payload,{headers,responseType:'text'});
  }

  baseUrl : string = 'http://localhost:8080/api/users/';
    
      constructor(private http:HttpClient) { }
  
      getUserById(userId:number):Observable<UserDTO>
      {
        console.log("In service : getUserById method called")
  
        const token = localStorage.getItem('token');
  
        const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
    
        return this.http.get<UserDTO>(this.baseUrl+"getUser/"+userId,{headers})
      }

      getAllUsers():Observable<UserDTO[]>
      {
        console.log("In service : getAllUsers method called")
  
        const token = localStorage.getItem('token');
  
        const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
    
        return this.http.get<UserDTO[]>(this.baseUrl+"getAllUsers",{headers})
      }

      updateUser(userId:number,user:UserDTO): Observable<any> {
          console.log("In service : updateUser method called")
          
                const token = localStorage.getItem('token');
          
                const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
      
          return this.http.put(this.baseUrl+'updateUser/'+userId, user,{headers});
    }

    deactivateUser(userId:any)
    {
      console.log("In service : getAllUsers method called")
  
        const token = localStorage.getItem('token');
  
        const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
    
        return this.http.delete(this.baseUrl+"deactivateUser/"+userId,{headers,responseType:'text'})
    }
}
