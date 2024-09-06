import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { EmployeeService } from '../employee/employee.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  // user
  agentProfile:any;
  empId:any;
employee: any;

  constructor(
    private keyCloakService: KeycloakService,
    private empservice: EmployeeService
  ) {
  }
  async ngOnInit(): Promise<void> {
    this.agentProfile = await this.keyCloakService.loadUserProfile();
    this.empservice.getEmployee(this.agentProfile.email).subscribe((data) => {
      this.empId = data.employeeId;
      this.employee=data;
      console.log(this.empId);
    });
  }
}
