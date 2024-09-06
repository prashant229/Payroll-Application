import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class LeavesService {
  agentProfile:any

  constructor( private http:HttpClient) { }

      // console.log('Form Submitted!');
    // console.log('From Date: ' + this.fromDate, 'To Date: ' + this.toDate);
 
  
  
  applyLeave(fromDate: any, toDate: any, leaveType: any, reason: any,empId:any, managerId:any){

    const apiUrl = 'http://localhost:8090/api/applyLeave'; 
    const payload = {
      empId: empId,
        leaveType: leaveType,
        startDate: fromDate,
        endDate: toDate,
        reason: reason,
        status: "PENDING_FROM_MANAGER",
        rejectionReason: "rejection-reason",
        noOfLeaves: this.calculateNumberOfLeaves(fromDate, toDate),
         mangerId: managerId
        }
    return this.http.post(apiUrl,  payload );
  }
  calculateNumberOfLeaves(fromDate: string, toDate: string): number {
    // Convert the input dates to JavaScript Date objects
    const start = new Date(fromDate);
    const end = new Date(toDate);
    
    // Ensure the end date is after the start date
    if (end < start) {
      throw new Error('End date must be after the start date.');
    }
  
    // Calculate the difference in time (in milliseconds)
    const timeDiff = end.getTime() - start.getTime();
    
    // Convert time difference from milliseconds to days
    // Adding 1 to include both start and end dates
    const daysDiff = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;
    
    return daysDiff;
  }
  getLeaveHistoryByEmpId(empId:any){
    const apiUrl = 'http://localhost:8090/api/getLeavesHistory';
    const params = {empId: empId};
    return this.http.get(apiUrl, { params });
  }

  getLeaves(empId: string) {
    const apiUrl = `http://localhost:8090/api/fetch`; 
    const params = {empId: empId};
    return this.http.get(apiUrl, { params });
  }
  
}
