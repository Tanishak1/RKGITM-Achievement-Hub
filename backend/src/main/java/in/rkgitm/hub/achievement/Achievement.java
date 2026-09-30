package in.rkgitm.hub.achievement;
import jakarta.persistence.*;import java.time.Instant;import java.util.*;
@Entity @Table(name="achievements")
public class Achievement {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title;
 @Column(nullable=false) private String studentName;
 private String department; private String category;
 @Column(length=2000) private String description;
 private String proofUrl; private String studentUid;
 @ElementCollection @CollectionTable(name="achievement_photos",joinColumns=@JoinColumn(name="achievement_id")) @Column(name="photo_url",length=7000000) private List<String> photoUrls=new ArrayList<>();
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.PENDING;
 @Column(length=1000) private String rejectionReason; private String reviewedBy; private Instant reviewedAt;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public enum Status{PENDING,APPROVED,REJECTED}
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getStudentName(){return studentName;} public void setStudentName(String v){studentName=v;}
 public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getProofUrl(){return proofUrl;} public void setProofUrl(String v){proofUrl=v;}
 public String getStudentUid(){return studentUid;} public void setStudentUid(String v){studentUid=v;}
 public List<String> getPhotoUrls(){return photoUrls;} public void setPhotoUrls(List<String> v){photoUrls=v==null?new ArrayList<>():v.stream().filter(Objects::nonNull).limit(6).toList();}
 public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
 public String getRejectionReason(){return rejectionReason;} public void setRejectionReason(String v){rejectionReason=v;}
 public String getReviewedBy(){return reviewedBy;} public void setReviewedBy(String v){reviewedBy=v;} public Instant getReviewedAt(){return reviewedAt;} public void setReviewedAt(Instant v){reviewedAt=v;}
 public Instant getCreatedAt(){return createdAt;}
}