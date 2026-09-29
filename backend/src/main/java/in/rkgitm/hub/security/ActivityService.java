package in.rkgitm.hub.security;
import org.springframework.stereotype.Service;
@Service public class ActivityService{
 private final AuditLogRepository audits;private final PortalNotificationRepository notifications;
 public ActivityService(AuditLogRepository a,PortalNotificationRepository n){audits=a;notifications=n;}
 public void audit(String actor,String action,String targetType,String targetId,String details){AuditLog x=new AuditLog();x.setActorUid(actor==null?"SYSTEM":actor);x.setAction(action);x.setTargetType(targetType);x.setTargetId(targetId);x.setDetails(details);audits.save(x);}
 public void notify(String uid,String title,String message,String link){if(uid==null||uid.isBlank())return;PortalNotification n=new PortalNotification();n.setRecipientUid(uid);n.setTitle(title);n.setMessage(message);n.setLinkUrl(link);notifications.save(n);}
}