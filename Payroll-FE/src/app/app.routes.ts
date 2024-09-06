import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SalaryComponent } from './salary/salary.component';
import { LeavesComponent } from './leaves/leaves.component';
import { EmployeeComponent } from './employee/employee.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { AuthGuard } from './guard/auth.guard';
import { AddEmployeeComponent } from './employee-form/employee-form.component';

export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'login', component: LoginFormComponent },
    { path: 'addEmployee', canActivate: [AuthGuard], component: AddEmployeeComponent },
    { path: 'home', canActivate: [AuthGuard], component: HomeComponent },
    { path: 'salary',canActivate: [AuthGuard], component: SalaryComponent },
    { path: 'leaves',canActivate: [AuthGuard], component: LeavesComponent },
    { path: 'employee',canActivate: [AuthGuard], component: EmployeeComponent }
];
