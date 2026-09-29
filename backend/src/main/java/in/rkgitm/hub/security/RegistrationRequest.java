package in.rkgitm.hub.security;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="registration_requests")
public class RegistrationRequest{
 public enum RequestedRole{STUDENT,FACULTY} public enum Status{PENDING,APPROVED,REJECTED}
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String collegeRollNo; private String universityRollNo;
 @Column(nullable=false) private String name; private String department; private String studyYear;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private RequestedRole requestedRole;
 @Enumerated(EnumType.STRING) private Status status=Status.PENDING; @Column(length=1000) private String rejectionReason;
 @Column(nullable=false) private String passwordHash; private String idCardFileName; private String idCardContentType;
 @Lob @Column(nullable=false) private byte[] idCardImage; private Instant createdAt=Instant.now();
 public Long getId(){return id;} public String getCollegeRollNo(){return collegeRollNo;} public void setCollegeRollNo(String v){collegeRollNo=v;} public String getUniversityRollNo(){return universityRollNo;} public void setUniversityRollNo(String v){universityRollNo=v;}
 public String getName(){return name;} public void setName(String v){name=v;} public String getDepartment(){return department;} public void setDepartment(String v){department=v;} public String getStudyYear(){return studyYear;} public void setStudyYear(String v){studyYear=v;}
 public String getRejectionReason(){return rejectionReason;} public void setRejectionReason(String v){rejectionReason=v;} public RequestedRole getRequestedRole(){return requestedRole;} public void setRequestedRole(RequestedRole v){requestedRole=v;} public Status getStatus(){return status;} public void setStatus(Status v){status=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public String getIdCardFileName(){return idCardFileName;} public void setIdCardFileName(String v){idCardFileName=v;} public String getIdCardContentType(){return idCardContentType;} public void setIdCardContentType(String v){idCardContentType=v;} public byte[] getIdCardImage(){return idCardImage;} public void setIdCardImage(byte[] v){idCardImage=v;} public Instant getCreatedAt(){return createdAt;}
}