import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IUser } from '../model/IUser';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = 'http://localhost:8080/api/employee/fetchEmployee';

  constructor(private http: HttpClient) {}

  getEmployee(emailAddress: string): Observable<IUser> {
    const params = new HttpParams().set('emailAddress', emailAddress);
    return this.http.get<IUser>(this.apiUrl, { params });
  }

  createEmployee(employee:any): Observable<any> {
    const payload={
      "firstName": employee.firstName,
      "lastName" : employee.lastName,
      "contactNumber" : employee.contactNumber,
      "gender": employee.gender,
      "role": employee.role,
      "emailAddress": employee.emailAddress,
      "managerId":employee.managerId,
      "dob": employee.dob,
      "hra":employee.hra,
      "basic":employee.basic,
      "benefits":employee.benefits
  }
  const apiUrls="http://localhost:8080/api/createEmp";
 return this.http.post(apiUrls,payload); 
  }
}
