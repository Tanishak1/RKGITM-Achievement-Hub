package in.rkgitm.hub.security;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="portal_notifications")
public class PortalNotification{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String recipientUid;
 @Column(nullable=false) private String title;
 @Column(length=1200) private String message;
 private String linkUrl; private boolean readFlag=false;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public String getRecipientUid(){return recipientUid;} public void setRecipientUid(String v){recipientUid=v;}
 public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getMessage(){return message;} public void setMessage(String v){message=v;}
 public String getLinkUrl(){return linkUrl;} public void setLinkUrl(String v){linkUrl=v;} public boolean isReadFlag(){return readFlag;} public void setReadFlag(boolean v){readFlag=v;} public Instant getCreatedAt(){return createdAt;}
}