import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LeaveType } from '../model/LeaveType';
import { LeavesService } from './leaves.service';
import { KeycloakService } from 'keycloak-angular';
import { EmployeeService } from '../employee/employee.service';
import { CommonModule } from '@angular/common';
import { ILeave } from '../model/ILeave';

@Component({
  selector: 'app-leaves',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './leaves.component.html',
  styleUrl: './leaves.component.css',
})
export class LeavesComponent {
  fromDate: any;
  toDate: any;
  leaveType: any; // Default value
  reason: string;
  agentProfile: any;
  empId: any;
  managerId: any;
  leaveSuccessMessage: any;
  leaveRequests: ILeave[] = [];
  leaves: any;

  constructor(
    private keyCloakService: KeycloakService,
    private leaveService: LeavesService,
    private empservice: EmployeeService
  ) {
    this.reason = '';
  }

  async ngOnInit(): Promise<void> {
    this.agentProfile = await this.keyCloakService.loadUserProfile();
    this.empservice.getEmployee(this.agentProfile.email).subscribe((data) => {
      this.empId = data.employeeId;
      this.managerId = data.managerId;
      console.log(this.empId, this.managerId);
      this.leaveService
        .getLeaveHistoryByEmpId(this.empId)
        .subscribe((data: any) => {
          this.leaveRequests = data;
        });
      this.leaveService.getLeaves(this.empId).subscribe((data: any) => {
        this.leaves = {
          casual: data.casualLeaves || 0,
          sick: data.sickLeaves || 0,
          earned: data.earnedLeaves || 0,
        };
        console.log(this.leaves);
      });
    });
  }

  onSubmit() {
    console.log('submit');
    console.log(this.reason);

    console.log(
      'Form Submitted!',
      this.fromDate,
      this.toDate,
      this.leaveType,
      this.reason,
      this.empId,
      this.managerId
    );
    this.leaveService
      .applyLeave(
        this.fromDate,
        this.toDate,
        this.leaveType,
        this.reason,
        this.empId,
        this.managerId
      )
      .subscribe((data: any) => {
        this.leaveSuccessMessage = 'Leave Applied Successfully';
        console.log(data);
      });
  }
}
