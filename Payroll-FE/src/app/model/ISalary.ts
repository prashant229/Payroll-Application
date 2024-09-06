export interface ISalary {
    empId: number;
    hra: number;
    basic: number;
    benefits:number;
    paymentAmount:number;
    unpaidLeaveDeduction:number;
    dateOfPayment:Date;
}