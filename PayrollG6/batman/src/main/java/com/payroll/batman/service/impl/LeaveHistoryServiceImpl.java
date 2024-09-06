package com.payroll.batman.service.impl;

import com.payroll.batman.dto.*;
import com.payroll.batman.entity.LeaveHistory;
import com.payroll.batman.enums.LeaveStatus;
import com.payroll.batman.enums.LeaveType;
import com.payroll.batman.exceptions.*;
import com.payroll.batman.mapper.LeaveHistoryMapper;
import com.payroll.batman.repository.LeaveHistoryRepository;
import com.payroll.batman.service.ILeaveHistoryService;
import com.payroll.batman.service.ILeaveService;
import com.payroll.batman.service.clients.EmployeeFeignClient;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class LeaveHistoryServiceImpl implements ILeaveHistoryService {
    private LeaveHistoryRepository leaveHistoryRepository ;

    private EmployeeFeignClient employeeFeignClient;

    private ILeaveService leaveService ;

    private StreamBridge streamBridge ;

    private  static final Logger logger = LoggerFactory.getLogger(LeaveHistoryServiceImpl.class);

    @Value("${build.baseUrl}")
    private String  baseUrl;

    public LeaveHistoryServiceImpl(LeaveHistoryRepository leaveHistoryRepository, EmployeeFeignClient employeeFeignClient, ILeaveService leaveService, StreamBridge streamBridge) {
        this.leaveHistoryRepository = leaveHistoryRepository;
        this.employeeFeignClient = employeeFeignClient;
        this.leaveService = leaveService;
        this.streamBridge = streamBridge;
    }

    @Override
    public List<LeaveHistoryDto> getLeaveHistoryByEmpID(Long empId) {
           Optional<List<LeaveHistory>> leaveHistoryListOptional= leaveHistoryRepository.findByEmpId(empId);
           if(leaveHistoryListOptional.isEmpty()){
               logger.info("No leave history found");
               return Collections.emptyList();
           }
           List<LeaveHistory> leaveHistoryList = leaveHistoryListOptional.get();

           List<LeaveHistoryDto> leaveHistoryDtoList = new ArrayList<>();
           for (LeaveHistory leaveHistory : leaveHistoryList){
               leaveHistoryDtoList.add(LeaveHistoryMapper.toLeaveHistoryDto(leaveHistory , new LeaveHistoryDto()));
           }

           return leaveHistoryDtoList ;
    }

    @Override
    public List<LeaveHistoryDto> getLeaveHistoryByEmpIdAndDate(Long empId, LocalDate from, LocalDate to) {
        Optional<List<LeaveHistory>> leaveHistoryListOptional = leaveHistoryRepository.getLeaveHistoryByDateAndEmpId(empId , from,to );

        if(leaveHistoryListOptional.isEmpty()){
            logger.info("No Leave History found for employee with id : "+empId +" and Time Interval : " + from + " -- " + to);
            return Collections.emptyList();
        }
        List<LeaveHistory> leaveHistoryList = leaveHistoryListOptional.get();

        List<LeaveHistoryDto> leaveHistoryDtoList = new ArrayList<>();
        for (LeaveHistory leaveHistory : leaveHistoryList){
            leaveHistoryDtoList.add(LeaveHistoryMapper.toLeaveHistoryDto(leaveHistory , new LeaveHistoryDto()));
        }

        return leaveHistoryDtoList ;

    }

    // TODO : for testing purpose
    @Override
    public boolean createLeaveHistoryRecord(LeaveHistoryDto leaveHistoryDto) {

        boolean isEmployeeAvailable = employeeFeignClient.isEmployeeAvailable(leaveHistoryDto.getEmpId());
        if(isEmployeeAvailable) {
            LeaveTrackerDto leaveTrackerDto = new LeaveTrackerDto(leaveHistoryDto.getEmpId(), 10L, 10L, 10L, 10L);
            leaveService.upsertTracker(leaveTrackerDto);
            LeaveHistory leaveHistory = LeaveHistoryMapper.toLeaveHistory(leaveHistoryDto, new LeaveHistory());
            leaveHistoryRepository.save(leaveHistory);

            return true;
        }
        return false;
    }

    @Override
    public Long getUnpaidLeaves(Long empId, LocalDate from, LocalDate to) {
        List<LeaveHistory> leaveHistoryList = leaveHistoryRepository.getLeaveHistoryByDateAndEmpId(empId,from,to)
                .orElse(Collections.emptyList());

        Long unpaidLeaves = 0L ;
        for (LeaveHistory leaveHistory : leaveHistoryList){
            if(leaveHistory.getLeaveType().equals(LeaveType.UNPAID_LEAVE)){
                unpaidLeaves += leaveHistory.getNoOfLeaves();
            }
        }

        return unpaidLeaves ;

    }

    @Override
    public List<LeaveHistoryDto> getLeavesForManagerApproval(Long managerId) {
        List<LeaveHistory> leaveHistoryList = leaveHistoryRepository.getLeaveHistoryByManagerId(managerId,LeaveStatus.PENDING_FROM_MANAGER)
                .orElse(Collections.emptyList());

        List<LeaveHistoryDto> leaveHistoryDtoList = new ArrayList<>();
        for (LeaveHistory leaveHistory : leaveHistoryList){
            leaveHistoryDtoList.add(LeaveHistoryMapper.toLeaveHistoryDto(leaveHistory , new LeaveHistoryDto()));
        }

        return leaveHistoryDtoList ;
    }

    @Override
    public boolean takeAction(Long leaveId, String newStatus, String rejectionReason) {
        Optional<LeaveHistory> leaveHistoryOptional = leaveHistoryRepository.findById(leaveId);
        if(leaveHistoryOptional.isEmpty()){
            throw new LeaveHistoryNotFoundException("Leave History not found with id : "+leaveId);
        }

        LeaveHistory leaveHistory = leaveHistoryOptional.get();
        LeaveStatus leaveStatus = LeaveStatus.valueOf(newStatus);
        if(leaveStatus.equals(LeaveStatus.LEAVE_REJECTED)){
            leaveHistory.setRejectionReason(rejectionReason);
        }
        leaveHistory.setStatus(leaveStatus);
        leaveHistoryRepository.save(leaveHistory);
//        sendEmails(leaveHistory);
        return true ;
    }

    @Override
    public boolean deleteLeaveHistory(Long leaveId) {
        Optional<LeaveHistory> leaveHistoryOptional = leaveHistoryRepository.findById(leaveId);
        if(leaveHistoryOptional.isEmpty()){
            throw new LeaveHistoryNotFoundException("Leave History not found with id : "+leaveId);
        }

        leaveHistoryRepository.deleteById(leaveId);
        return true ;
    }

    @Override
    public boolean approveLeave(String leaveToken) {
        Optional<LeaveHistory> leaveHistoryOptional = leaveHistoryRepository.findByLeaveToken(leaveToken);
        if(leaveHistoryOptional.isEmpty()){
            throw new LeaveHistoryNotFoundException("Leave History not found with token : "+leaveToken);
        }

        LeaveHistory leaveHistory = leaveHistoryOptional.get();
        leaveHistory.setStatus(LeaveStatus.LEAVE_APPROVED);
        leaveHistoryRepository.save(leaveHistory);
        return  true ;
    }

    @Override
    public boolean rejectLeave(String leaveToken) {
        Optional<LeaveHistory> leaveHistoryOptional = leaveHistoryRepository.findByLeaveToken(leaveToken);
        if(leaveHistoryOptional.isEmpty()){
            throw new LeaveHistoryNotFoundException("Leave History not found with token : "+leaveToken);
        }

        LeaveHistory leaveHistory = leaveHistoryOptional.get();
        leaveHistory.setStatus(LeaveStatus.LEAVE_REJECTED);
        updateLeaveTracker(LeaveHistoryMapper.toLeaveHistoryDto(leaveHistory , new LeaveHistoryDto()));
        leaveHistoryRepository.save(leaveHistory);
        return  true ;
    }

    @Override
    public boolean processLeaveRequest(LeaveHistoryDto leaveHistoryDto) {
        // if the leave extends over 2 months we prompt the user to apply for  separate leaves for each month
        validateDateRange(leaveHistoryDto);
        // leave status should be PENDING_FROM_MANAGER
        validateLeaveStatus(leaveHistoryDto);


        Long noOfLeaves = calculateNoOfLeaves(leaveHistoryDto);
        leaveHistoryDto.setNoOfLeaves(noOfLeaves);
        if(!updateLeaveTracker(leaveHistoryDto)){
            throw new InsufficientLeaveBalanceException("No leaves remaining for leave type : "+leaveHistoryDto.getLeaveType());
        }


        // check if the leave already applied for the period
        checkIfLeaveAlreadyApplied(leaveHistoryDto);

        LeaveHistory leaveHistory = LeaveHistoryMapper.toLeaveHistory(leaveHistoryDto , new LeaveHistory());
        leaveHistory.setLeaveToken(UUID.randomUUID().toString());
        sendEmails(leaveHistory);
        leaveHistory = leaveHistoryRepository.save(leaveHistory);
        return true ;
    }

    private void validateDateRange(LeaveHistoryDto leaveHistoryDto){
        LocalDate startDate = leaveHistoryDto.getStartDate() ;
        LocalDate endDate = leaveHistoryDto.getEndDate() ;

        YearMonth startYearMonth = YearMonth.of(startDate.getYear(),startDate.getMonth());
        YearMonth endYearMonth = YearMonth.of(endDate.getYear() , endDate.getMonth()) ;

        if(!startYearMonth.equals(endYearMonth) || endDate.isBefore(startDate)){
            throw new InvalidDateRangeException("Date Range is incorrect");
        }
    }

    private void validateLeaveStatus(LeaveHistoryDto leaveHistoryDto){
        if(!leaveHistoryDto.getStatus().equals(LeaveStatus.PENDING_FROM_MANAGER)){
            throw new IncorrectStatusException("Status is incorrect");
        }
    }

    private Long calculateNoOfLeaves(LeaveHistoryDto leaveHistoryDto){
        Long noOfLeaves = workingDays(leaveHistoryDto.getStartDate() , leaveHistoryDto.getEndDate());
        if(noOfLeaves <= 0){
            throw new InvalidDateRangeException("Invalid date range");
        }
        return noOfLeaves ;
    }

    private void checkIfLeaveAlreadyApplied(LeaveHistoryDto leaveHistoryDto){
        leaveHistoryRepository.getLeaveHistoryByDateAndEmpId(leaveHistoryDto.getEmpId() , leaveHistoryDto.getStartDate() , leaveHistoryDto.getEndDate()).ifPresent(leaveHistories -> {
            if(!leaveHistories.isEmpty()){
                throw new InvalidDateRangeException("Leave already applied for the same period");
            }
        });
    }


    private Long workingDays(LocalDate startDate , LocalDate endDate){
        Long workingDays = 0L ;
        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)){
            if(startDate.getDayOfWeek().getValue() < 6){
                workingDays++;
            }
            startDate = startDate.plusDays(1);
        }
        System.out.println("Working days : "+workingDays);
        return workingDays ;
    }

    private boolean updateLeaveTracker(LeaveHistoryDto leaveHistoryDto){
        LeaveType leaveType = leaveHistoryDto.getLeaveType();
        long remainingLeaves = 0L ;
        LeaveTrackerDto leaveTrackerDto = leaveService.getTracker(leaveHistoryDto.getEmpId());
        if (leaveHistoryDto.getStatus().equals(LeaveStatus.LEAVE_REJECTED)) {
            switch (leaveType) {
                case CASUAL_LEAVE:
                    remainingLeaves = leaveTrackerDto.getCasualLeaves() + leaveHistoryDto.getNoOfLeaves();
                    leaveTrackerDto.setCasualLeaves(remainingLeaves);
                    break;
                case SICK_LEAVE:
                    remainingLeaves = leaveTrackerDto.getSickLeaves() + leaveHistoryDto.getNoOfLeaves();
                    leaveTrackerDto.setSickLeaves(remainingLeaves);
                    break;
                case EARNED_LEAVE:
                    remainingLeaves = leaveTrackerDto.getEarnedLeaves() + leaveHistoryDto.getNoOfLeaves();
                    leaveTrackerDto.setEarnedLeaves(remainingLeaves);
                    break;
                case UNPAID_LEAVE:
                    leaveTrackerDto.setUnpaidLeaves(leaveTrackerDto.getUnpaidLeaves() - leaveHistoryDto.getNoOfLeaves());
                    break;
                default:
                    throw new IncorrectLeaveTypeException("Unexpected value for leave type: " + leaveType);
            }
        } else {
            // Update the leave count if the leave is not rejected
            switch (leaveType) {
                case CASUAL_LEAVE:
                    remainingLeaves = leaveTrackerDto.getCasualLeaves() - leaveHistoryDto.getNoOfLeaves();
                    leaveTrackerDto.setCasualLeaves(remainingLeaves);
                    break;
                case SICK_LEAVE:
                    remainingLeaves = leaveTrackerDto.getSickLeaves() - leaveHistoryDto.getNoOfLeaves();
                    leaveTrackerDto.setSickLeaves(remainingLeaves);
                    break;
                case EARNED_LEAVE:
                    remainingLeaves = leaveTrackerDto.getEarnedLeaves() - leaveHistoryDto.getNoOfLeaves();
                    leaveTrackerDto.setEarnedLeaves(remainingLeaves);
                    break;
                case UNPAID_LEAVE:
                    leaveTrackerDto.setUnpaidLeaves(leaveTrackerDto.getUnpaidLeaves() + leaveHistoryDto.getNoOfLeaves());
                    break;
                default:
                    throw new IncorrectLeaveTypeException("Unexpected value for leave type: " + leaveType);
            }
        }

        if(remainingLeaves < 0){
            throw new InsufficientLeaveBalanceException("No leaves remaining for leave type : "+leaveType);
        }

        leaveService.upsertTracker(leaveTrackerDto);
        return  true ;

    }


    private void sendEmails(LeaveHistory leaveHistory) {
        // Formulate email DTOs
        List<EmailDto> emailDtos = formulateEmailDtos(leaveHistory);

        for (EmailDto emailDto : emailDtos) {
            streamBridge.send("notification-out-0", emailDto);
        }
    }

    private List<EmailDto> formulateEmailDtos(LeaveHistory leaveHistory) {
        // Fetch employee details
        EmployeeDto employeeDto = Optional.ofNullable(employeeFeignClient.fetchEmployee(leaveHistory.getEmpId()))
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + leaveHistory.getEmpId()));

        // Fetch manager details
        EmployeeDto managerDto = Optional.ofNullable(employeeFeignClient.fetchEmployee(employeeDto.getManagerId()))
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new EmployeeNotFoundException("Manager not found with id: " + employeeDto.getManagerId()));

        // Create EmailDto for manager approval
        String managerEmail = managerDto.getEmailAddress();
        String managerEmailSubject = "Leave Approval Request for " + employeeDto.getFirstName() + " " + employeeDto.getLastName();
//        String managerEmailBody = "Dear " + managerDto.getFirstName() + ",\n\n" +
//                "You have a new leave approval request from " + employeeDto.getFirstName() + " " + employeeDto.getLastName() + ".\n" +
//                "Leave Type: " + leaveHistory.getLeaveType() + "\n" +
//                "Start Date: " + leaveHistory.getStartDate() + "\n" +
//                "End Date: " + leaveHistory.getEndDate() + "\n" +
//                "Reason: " + leaveHistory.getReason() + "\n\n" +
//                "Please review and take the necessary action.\n\n" +
//                "Approve leave request: " + createApproveLink(leaveHistory.getLeaveToken()) + "\n\n" +
//                "Reject leave request: " + createRejectLink(leaveHistory.getLeaveToken()) + "\n\n" +
//                "Best Regards,\n" +
//                "Payroll System";
        String managerEmailBody = "<p style=\"font-family: Arial, sans-serif; color: #333; background-color: #f4f4f4; margin: 0; padding: 0;\">\n" +
                "    <div style=\"width: 80%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">\n" +
                "        <h1 style=\"font-size: 18px; padding:15px; margin-bottom: 20px;background-color: #005151; color: #ffffff;\">Leave Approval Request</h1>\n" +
                "        <p style=\"margin: 0 0 10px;\">Dear " + managerDto.getFirstName() +",</p>\n" +
                "        <p style=\"margin: 0 0 10px;\">You have a new leave approval request from " + employeeDto.getFirstName() +" " + employeeDto.getLastName() +".</p>\n" +
                "        <p style=\"margin: 0 0 10px;\"><strong>Leave Type: </strong>" + leaveHistory.getLeaveType()+"</p>\n" +
                "        <p style=\"margin: 0 0 10px;\"><strong>Start Date: </strong>"+ leaveHistory.getStartDate() +"</p>\n" +
                "        <p style=\"margin: 0 0 10px;\"><strong>End Date: </strong>"+ leaveHistory.getEndDate()+"</p>\n" +
                "        <p style=\"margin: 0 0 10px;\"><strong>Reason: </strong>"+ leaveHistory.getReason()+"</p>\n" +
                "        <p style=\"margin: 0 0 10px;\">Please review and take the necessary action.</p>\n" +
                "        <p style=\"margin: 0 0 10px;\">\n" +
                "            <a href=\""+createApproveLink(leaveHistory.getLeaveToken())+"\" style=\"display: inline-block; padding: 10px 20px; margin: 5px 0; font-size: 16px; font-weight: bold; color: #ffffff; background-color: #007bff; border: none; border-radius: 5px; text-decoration: none; text-align: center;\">Approve Leave Request</a>\n" +
                "        </p>\n" +
                "        <p style=\"margin: 0 0 10px;\">\n" +
                "            <a href=\"" + createRejectLink(leaveHistory.getLeaveToken()) + "\" style=\"display: inline-block; padding: 10px 20px; margin: 5px 0; font-size: 16px; font-weight: bold; color: #ffffff; background-color: #dc3545; border: none; border-radius: 5px; text-decoration: none; text-align: center;\">Reject Leave Request</a>\n" +
                "        </p>\n" +
                "        <div style=\"margin-top: 20px; font-size: 14px; color: #666;\">\n" +
                "            <p style=\"margin: 0;\">Best Regards,</p>\n" +
                "            <p style=\"margin: 0;\">Payroll System</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</p>\n";

        EmailDto managerEmailDto = new EmailDto(managerEmail, managerEmailSubject, managerEmailBody);

//        // Create EmailDto for employee notification
//        String employeeEmail = employeeDto.getEmailAddress();
//        String employeeEmailSubject = "Leave Request " + leaveHistory.getStatus();
//        String employeeEmailBody = "Dear " + employeeDto.getFirstName() + ",\n\n" +
//                "Your leave request has been " + leaveHistory.getStatus().toString().toLowerCase() + ".\n" +
//                "Leave Type: " + leaveHistory.getLeaveType() + "\n" +
//                "Start Date: " + leaveHistory.getStartDate() + "\n" +
//                "End Date: " + leaveHistory.getEndDate() + "\n" +
//                "Reason: " + leaveHistory.getReason() + "\n";
//        if (leaveHistory.getStatus() == LeaveStatus.LEAVE_REJECTED) {
//            employeeEmailBody += "Rejection Reason: " + leaveHistory.getRejectionReason() + "\n";
//        }
//        employeeEmailBody += "\nBest Regards,\nPayroll System";
//        EmailDto employeeEmailDto = new EmailDto(employeeEmail, employeeEmailSubject, employeeEmailBody);
//
        return Arrays.asList(managerEmailDto);
    }


    private String createRejectLink(String leaveToken) {
        return  baseUrl +"/api/rejectLeave/" + leaveToken;
    }

    private String createApproveLink(String leaveToken) {
        return  baseUrl +"/api/approveLeave/" + leaveToken;
    }

}
