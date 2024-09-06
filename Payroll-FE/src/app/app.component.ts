import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './header/header.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { FormsModule } from '@angular/forms';
import { HomeComponent } from "./home/home.component";
import { FooterComponent } from "./footer/footer.component";
import { EmployeeComponent } from "./employee/employee.component";
import { LeavesComponent } from "./leaves/leaves.component";
import { SalaryComponent } from "./salary/salary.component";
import { PayslipComponent } from "./payslip/payslip.component";
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, LoginFormComponent, HomeComponent, FooterComponent, EmployeeComponent, LeavesComponent, SalaryComponent, PayslipComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'payroll-fe-g6';
  agentProfile: any;
  constructor(private keyCloakService: KeycloakService) {  
    this.keyCloakService.loadUserProfile().then(user => {
      this.agentProfile = user;
      console.log(this.agentProfile);
  })
  }
  
}
