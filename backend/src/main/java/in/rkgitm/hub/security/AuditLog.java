package in.rkgitm.hub.security;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="audit_logs")
public class AuditLog{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String actorUid;
 @Column(nullable=false) private String action;
 private String targetType; private String targetId;
 @Column(length=1200) private String details;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public String getActorUid(){return actorUid;} public void setActorUid(String v){actorUid=v;}
 public String getAction(){return action;} public void setAction(String v){action=v;} public String getTargetType(){return targetType;} public void setTargetType(String v){targetType=v;}
 public String getTargetId(){return targetId;} public void setTargetId(String v){targetId=v;} public String getDetails(){return details;} public void setDetails(String v){details=v;} public Instant getCreatedAt(){return createdAt;}
}