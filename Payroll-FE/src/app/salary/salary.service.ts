import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SalaryService {
  
  constructor(private http: HttpClient) { 
  }

  generatePayslip(fromDate:string, toDate:string) {
    // console.log('Form Submitted!');
    // console.log('From Date: ' + this.fromDate, 'To Date: ' + this.toDate);

    const apiUrl = 'http://localhost:9000/api/get'; 
    const params = { start: fromDate, end:toDate };

    return this.http.get(apiUrl, { params });
  }

  // generatePayslipTemp(fromDate:string, toDate:string) {
  //   // console.log('Form Submitted!');
  //   // console.log('From Date: ' + this.fromDate, 'To Date: ' + this.toDate);

  //   const apiUrl = 'http://localhost:8072/payroll/salary/api/get'; 
  //   const params = { start: fromDate, end:toDate };

  //   return this.http.get(apiUrl, { params });
  // }

  paySalary(paymentDate:string) {
    // console.log('Form Submitted!');
    // console.log('From Date: ' + this.fromDate, 'To Date: ' + this.toDate);

    const apiUrl = 'http://localhost:9000/api/createSalary'; 
    const params = new HttpParams().set('dateOfPayment', paymentDate);

    return this.http.post(apiUrl,  params );
  }
  

}
