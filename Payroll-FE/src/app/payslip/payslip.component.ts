import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ISalary } from '../model/ISalary';
import { IUser } from '../model/IUser';
import numWords from 'num-words';
import { SalaryService } from '../salary/salary.service';
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-payslip',
  standalone: true,
  imports: [JsonPipe],
  templateUrl: './payslip.component.html',
  styleUrl: './payslip.component.css'
})
export class PayslipComponent implements OnInit, OnChanges {

  @Input() payslipData: any;
  @Input() employee: any;

  data: any;

  constructor(private salaryService: SalaryService) { 
  }

  ngOnInit(): void {
    console.log('PayslipComponent constructor called', this.payslipData);
    // this.data = this.payslipData;
    // this.salaryService.payslipData$.subscribe(data => {
    //   this.payslipData = data;
    // });
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['payslipData']) {
      console.log('PayslipComponent ngOnChanges called', changes['payslipData'].currentValue);
      this.data = changes['payslipData'].currentValue;
      console.log('DATA: ', this.data);
    }
  }
  // salary:ISalary = {
  //   empId: 1,
  //   hra: 1000,
  //   basic: 9000,
  //   benefits:2000,
  //   paymentAmount:12000,
  //   unpaidLeaveDeduction:0,
  //   dateOfPayment: new Date('2024-08-01')
  // };
  // employee:IUser = {
  //   employeeId: 1,
  //   firstName: 'John',
  //   lastName: 'Doe',
  //   emailAddress: 'test@123.com',
  //   contactNumber: '1234567890',
  //   role: 'Employee',
  //   gender:'Male',
  //   managerId:2,
  //   dob: new Date('1990-01-01'),
  // };

  calculateTotalSalary() : number{
    return this.payslipData.totalBasic + this.payslipData.totalBenefits + this.payslipData.totalHra;
  }

  calculateTotalDeductions() : number{
    return this.payslipData.totalLeaves*100;
  }

  toWords(num:number) : string{
    return numWords(num);
  }
}
