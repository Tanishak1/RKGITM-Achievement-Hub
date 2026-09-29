package in.rkgitm.hub.security;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="portal_users",uniqueConstraints=@UniqueConstraint(columnNames="uid"))
public class PortalUser{
 public enum Role{STUDENT,FACULTY,ADMIN}
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String uid; @Column(nullable=false) private String passwordHash;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role=Role.STUDENT;
 private String name; private String department; private String studyYear; private String photoUrl;
 private boolean active=true; private boolean archived=false;
 private Instant archivedAt; private Instant lastActiveAt; private Instant createdAt=Instant.now();
 public Long getId(){return id;} public String getUid(){return uid;} public void setUid(String v){uid=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public Role getRole(){return role;} public void setRole(Role v){role=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
 public String getStudyYear(){return studyYear;} public void setStudyYear(String v){studyYear=v;} public String getPhotoUrl(){return photoUrl;} public void setPhotoUrl(String v){photoUrl=v;}
 public boolean isActive(){return active;} public void setActive(boolean v){active=v;} public boolean isArchived(){return archived;} public void setArchived(boolean v){archived=v;}
 public Instant getArchivedAt(){return archivedAt;} public void setArchivedAt(Instant v){archivedAt=v;} public Instant getLastActiveAt(){return lastActiveAt;} public void setLastActiveAt(Instant v){lastActiveAt=v;} public Instant getCreatedAt(){return createdAt;}
}