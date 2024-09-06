import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SalaryService } from './salary.service';
import { BehaviorSubject } from 'rxjs';
import { CommonModule, JsonPipe } from '@angular/common';
import { ISalary } from '../model/ISalary';
import { PayslipComponent } from '../payslip/payslip.component';
import { KeycloakService } from 'keycloak-angular';
import { EmployeeService } from '../employee/employee.service';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

@Component({
  selector: 'app-salary',
  standalone: true,
  imports: [FormsModule, JsonPipe, PayslipComponent, CommonModule],
  templateUrl: './salary.component.html',
  styleUrl: './salary.component.css',
})
export class SalaryComponent {
  fromDate: string;
  toDate: string;
  result: any;
  paymentDate: string;
  paymentSuccessMessage: any;
  agentProfile: any;
  empId: any;
  employee: any;
  // private payslipDataSubject = new BehaviorSubject<any>(null);
  // payslipData$ = this.payslipDataSubject.asObservable();
  constructor(
    private salaryService: SalaryService,
    private keyCloakService: KeycloakService,
    private empservice: EmployeeService
  ) {
    this.fromDate = '';
    this.toDate = '';
    this.paymentDate = '';
  }
  async ngOnInit(): Promise<void> {
    this.agentProfile = await this.keyCloakService.loadUserProfile();
    this.empservice.getEmployee(this.agentProfile.email).subscribe((data) => {
      this.empId = data.employeeId;
      this.employee = data;
      console.log(this.employee);
      console.log(this.empId);
    });
  }
  downloadPDF() {
    const payslip = document.getElementById('payslip-content'); // Ensure this matches your template ID

    html2canvas(payslip!).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF('p', 'mm', 'a4'); // A4 size page of PDF

      const imgWidth = 210; // A4 page width in mm
      const pageHeight = 295;  // A4 page height in mm
      const imgHeight = canvas.height * imgWidth / canvas.width;
      let heightLeft = imgHeight;

      let position = 0;

      pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
      heightLeft -= pageHeight;

      while (heightLeft >= 0) {
        position = heightLeft - imgHeight;
        pdf.addPage();
        pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;
      }

      pdf.save('payslip.pdf'); // Download the PDF
    });
  }

  onSubmit() {
    this.salaryService.generatePayslip(this.fromDate, this.toDate).subscribe(
      (response) => {
        console.log('API Response:', response);

        // this.result = response as ISalary[];
        // this.result = this.result.filter((item) => item.empId === 123); // insert employeeid from employee service
        // const filteredResult = (response as ISalary[]).filter(item => item.empId === this.employeeID);
        const filteredResult = (response as ISalary[]).filter(
          (item) => item.empId === this.empId
        );

        // Reduce the filtered array to a single object with totals
        const reducedResult = filteredResult.reduce(
          (acc, item) => {
            acc.totalHra += item.hra;
            acc.totalBasic += item.basic;
            acc.totalBenefits += item.benefits;
            acc.totalLeaves += item.unpaidLeaveDeduction;
            return acc;
          },
          { totalHra: 0, totalBasic: 0, totalLeaves: 0, totalBenefits: 0 }
        );

        // Add fromDate and toDate fields to the final object
        const finalResult = {
          ...reducedResult,
          fromDate: this.fromDate,
          toDate: this.toDate,
        };

        console.log('Final Result:', finalResult);
        this.result = finalResult; // Update the result with the final object
        console.log('Result:', this.result);
      },
      (error) => {
        console.error('API Error:', error);
      }
    );
  }
  onPayment() {
    this.salaryService.paySalary(this.paymentDate).subscribe(
      (response) => {
        this.paymentSuccessMessage = 'Payment Successful';
      },
      (error) => {
        console.error('API Error:', error);
      }
    );
  }

  // @ViewChild('contentToExport', { static: false }) content: ElementRef;

  // downloadPDF() {
  //   const element = this.content.nativeElement;
  //   const options = {
  //     margin: 1,
  //     filename: 'sample.pdf',
  //     image: { type: 'jpeg', quality: 0.98 },
  //     html2canvas: { scale: 2 },
  //     jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
  //   };

  //   html2pdf().from(element).set(options).save();
  // }

  // setPayslipData(data: any) {
  //   this.payslipDataSubject.next(data);
  // }
}
