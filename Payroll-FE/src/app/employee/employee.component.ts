import { Component, OnInit } from '@angular/core';
import { IUser } from '../model/IUser';
import { DatePipe } from '@angular/common';
import { EmployeeService } from './employee.service';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-employee',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.css'
})
export class EmployeeComponent implements OnInit {
  employee: IUser = {} as IUser;
  agentProfile: any;
  constructor(private keyCloakService: KeycloakService,private employeeSvc: EmployeeService) {  
  
  }

  
  async ngOnInit(): Promise<void> {
    this.agentProfile= await this.keyCloakService.loadUserProfile();
    console.log(this.agentProfile);
    
    if (this.agentProfile?.email) {
      this.employeeSvc.getEmployee(this.agentProfile.email).subscribe({
        next: (data) => {
          this.employee = data;
        },
        error: (err) => {
          console.error('Error fetching employee data', err);
          // Handle the error as appropriate for your application
        }
      });
    } else {
      console.error('Email address not found in user profile');
      // Handle the case where email is not available
    }
  } catch (error: any) {
    console.error('Failed to load user profile', error);
    // Handle the error as appropriate for your application
  }
  
}

