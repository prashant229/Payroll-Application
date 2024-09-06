package com.payroll.batman.controller;


import com.payroll.batman.dto.LeaveHistoryDto;
import com.payroll.batman.dto.ResponseDto;
import com.payroll.batman.service.ILeaveHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
//@AllArgsConstructor
public class LeaveHistoryController {

    ILeaveHistoryService leaveHistoryService ;

    @Value("${build.version}")
    private String buildVersion ;

    public LeaveHistoryController(ILeaveHistoryService leaveHistoryService){
        this.leaveHistoryService = leaveHistoryService;
    }


    @GetMapping("/getLeavesHistoryByDate")
    public ResponseEntity<List<LeaveHistoryDto>> getLeavesHistoryByDate(@Valid @RequestParam(value = "empId")  Long empId , @RequestParam(value = "from") LocalDate from , @RequestParam(value = "to") LocalDate to){
        List<LeaveHistoryDto> leaveHistoryDtoList =  leaveHistoryService.getLeaveHistoryByEmpIdAndDate(empId , from ,to );

        return  ResponseEntity.status(HttpStatus.OK).body(leaveHistoryDtoList);
    }

    @GetMapping("/getLeavesHistory")
    public ResponseEntity<List<LeaveHistoryDto>> getLeavesHistory(@Valid @RequestParam(value = "empId")  Long empId){
        List<LeaveHistoryDto> leaveHistoryDtoList =  leaveHistoryService.getLeaveHistoryByEmpID(empId);

        return  ResponseEntity.status(HttpStatus.OK).body(leaveHistoryDtoList);
    }


    @PostMapping("/applyLeave")
    public ResponseEntity<ResponseDto> applyLeave(@Valid @RequestBody LeaveHistoryDto leaveHistoryDto){
        boolean response = leaveHistoryService.processLeaveRequest(leaveHistoryDto);
        if(response)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success" , HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("No leaves remaining" , HttpStatus.BAD_REQUEST));
    }

//    // TODO : for inserting dummy data , for testing purpose - to be removed later
    @PostMapping("/createHistoryAndTracker")
    public ResponseEntity<ResponseDto> createData(@Valid @RequestBody LeaveHistoryDto leaveHistoryDto){
        boolean response = leaveHistoryService.createLeaveHistoryRecord(leaveHistoryDto);
        if(response)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success" , HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Failed" , HttpStatus.BAD_REQUEST));
    }


    @GetMapping("/getUnpaidLeaves")
    public  ResponseEntity<Long> getUnpaidLeaves(@Valid @RequestParam(value ="empId") Long empId ,
                                                 @RequestParam(value ="from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from ,
                                                 @RequestParam(value ="to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to){
        Long unpaidLeaves = leaveHistoryService.getUnpaidLeaves(empId , from , to);
        return ResponseEntity.status(HttpStatus.OK).body(unpaidLeaves);
    }
    // TODO : for testing to be removed later
    @GetMapping("/getBuildVersion")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @DeleteMapping("/deleteLeaveHistory")
    public ResponseEntity<ResponseDto> deleteLeaveHistory(@RequestParam(value = "leaveId") Long leaveId){
        boolean response = leaveHistoryService.deleteLeaveHistory(leaveId);
        if(response)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success" , HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Failed" , HttpStatus.BAD_REQUEST));
    }


    @GetMapping("/getLeavesForManagerApproval")
    public ResponseEntity<List<LeaveHistoryDto>> getLeavesForManagerApproval(@RequestParam(value = "managerId") Long managerId){
        List<LeaveHistoryDto> leaveHistoryDtoList = leaveHistoryService.getLeavesForManagerApproval(managerId);
        return ResponseEntity.status(HttpStatus.OK).body(leaveHistoryDtoList);
    }

    @GetMapping("/takeAction")
    public ResponseEntity<ResponseDto> takeAction(@RequestParam(value = "leaveId") Long leaveId , @RequestParam(value = "newStatus") String newStatus , @RequestParam(value = "rejectionReason") String rejectionReason){
        boolean response = leaveHistoryService.takeAction(leaveId , newStatus , rejectionReason);
        if(response)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success" , HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Failed" , HttpStatus.BAD_REQUEST));
    }

    //approve leave request
    @GetMapping("/approveLeave/{leaveToken}")
    public ResponseEntity<ResponseDto> approveLeave(@PathVariable("leaveToken") String leaveToken){
        boolean response = leaveHistoryService.approveLeave(leaveToken);
        if(response)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success" , HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Failed" , HttpStatus.BAD_REQUEST));
    }

    //reject leave request
    @GetMapping("/rejectLeave/{leaveToken}")
    public ResponseEntity<ResponseDto> rejectLeave(@PathVariable("leaveToken") String leaveToken){
        boolean response = leaveHistoryService.rejectLeave(leaveToken);
        if(response)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success" , HttpStatus.OK));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Failed" , HttpStatus.BAD_REQUEST));
    }


}
