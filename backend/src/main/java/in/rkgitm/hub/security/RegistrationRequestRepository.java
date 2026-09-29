package in.rkgitm.hub.security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest,Long>{
 boolean existsByCollegeRollNo(String roll);
 long countByRequestedRoleAndStatus(RegistrationRequest.RequestedRole role, RegistrationRequest.Status status);
 List<RegistrationRequest> findAllByOrderByCreatedAtDesc();

 interface Summary{
  Long getId();
  String getCollegeRollNo();
  String getUniversityRollNo();
  String getName();
  String getDepartment();
  String getStudyYear();
  RegistrationRequest.RequestedRole getRequestedRole();
  RegistrationRequest.Status getStatus();
  String getRejectionReason();
 }

 @Query("select r.id as id,r.collegeRollNo as collegeRollNo,r.universityRollNo as universityRollNo,r.name as name,r.department as department,r.studyYear as studyYear,r.requestedRole as requestedRole,r.status as status,r.rejectionReason as rejectionReason from RegistrationRequest r order by r.createdAt desc")
 List<Summary> findAllSummaries();
}