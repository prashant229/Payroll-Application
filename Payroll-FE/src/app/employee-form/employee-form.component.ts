import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { EmployeeService } from '../employee/employee.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './employee-form.component.html',
  standalone: true,
  imports: [FormsModule],
  styleUrls: ['./employee-form.component.css']
})


export class AddEmployeeComponent {
  constructor(private employeeService: EmployeeService){}
  employee = {
    firstName: '',
    lastName: '',
    contactNumber: '',
    gender: '',
    role: '',
    emailAddress: '',
    managerId: '',
    dob: '',
    hra: '',
    basic: '',
    benefits: ''
  };

  onSubmit() {
    console.log('Employee data submitted:', this.employee);
    // Add your form submission logic here
    this.employeeService.createEmployee(this.employee).subscribe((data)=>{
      console.log(this.employee, data, "created")
    })

  }
}
