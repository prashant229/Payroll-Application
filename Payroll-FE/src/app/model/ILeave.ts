import { ReplaySubject } from "rxjs";
import { LeaveStatus } from "./LeaveStatus";
import { LeaveType } from "./LeaveType";

export interface ILeave {
    empId: number;
    leaveType:LeaveType;
    startDate:Date;
    endDate:Date;
    reason:string;
    status:LeaveStatus;
    rejectionReason:string;
    noOfLeaves:number;
    leaveToken:string;
    mangerId:number;
}