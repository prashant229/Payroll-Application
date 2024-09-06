package com.payroll.batman.repository;

import com.payroll.batman.entity.LeaveHistory;
import com.payroll.batman.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaveHistoryRepository extends JpaRepository<LeaveHistory , Long> {

    @Query("SELECT lh FROM LeaveHistory lh WHERE (lh.startDate >= :from AND lh.startDate <= :to ) AND lh.empId = :empId")
    Optional<List<LeaveHistory>> getLeaveHistoryByDateAndEmpId(@Param("empId") Long empId, @Param("from") LocalDate from, @Param("to") LocalDate to);

    Optional<List<LeaveHistory>> findByEmpId(Long empID);

    @Query("SELECT lh FROM LeaveHistory lh WHERE lh.mangerId = :managerId AND lh.status = :status")
    Optional<List<LeaveHistory>> getLeaveHistoryByManagerId(@Param("managerId") Long managerId , @Param("status") LeaveStatus status);

    Optional<LeaveHistory> findByLeaveToken(String leaveToken);

}
