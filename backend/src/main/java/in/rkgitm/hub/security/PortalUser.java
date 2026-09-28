package in.rkgitm.hub.security;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="portal_users",uniqueConstraints=@UniqueConstraint(columnNames="uid"))
public class PortalUser{
 public enum Role{STUDENT,FACULTY,ADMIN}
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String uid; @Column(nullable=false) private String passwordHash;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role=Role.STUDENT;
 private String name; private String department; private boolean active=true; private Instant createdAt=Instant.now();
 public Long getId(){return id;} public String getUid(){return uid;} public void setUid(String v){uid=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public Role getRole(){return role;} public void setRole(Role v){role=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
 public boolean isActive(){return active;} public void setActive(boolean v){active=v;} public Instant getCreatedAt(){return createdAt;}
}