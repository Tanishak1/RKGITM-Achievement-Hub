package in.rkgitm.hub.achievement;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="achievements")
public class Achievement {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title;
 @Column(nullable=false) private String studentName;
 private String department;
 private String category;
 @Column(length=2000) private String description;
 private String proofUrl;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.PENDING;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public enum Status{PENDING,APPROVED,REJECTED}
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getStudentName(){return studentName;} public void setStudentName(String v){studentName=v;}
 public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getProofUrl(){return proofUrl;} public void setProofUrl(String v){proofUrl=v;}
 public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
 public Instant getCreatedAt(){return createdAt;}
}