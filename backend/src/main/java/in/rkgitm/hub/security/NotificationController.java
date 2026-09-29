package in.rkgitm.hub.security;
import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/notifications")
public class NotificationController{
 private final PortalNotificationRepository repo;
 public NotificationController(PortalNotificationRepository r){repo=r;}
 @GetMapping public Map<String,Object> mine(Authentication a){String uid=a.getName();Map<String,Object> m=new LinkedHashMap<>();m.put("unread",repo.countByRecipientUidAndReadFlag(uid,false));m.put("items",repo.findTop50ByRecipientUidOrderByCreatedAtDesc(uid));return m;}
 @PatchMapping("/{id}/read") public void read(@PathVariable Long id,Authentication a){repo.findById(id).filter(x->Objects.equals(x.getRecipientUid(),a.getName())).ifPresent(x->{x.setReadFlag(true);repo.save(x);});}
 @PatchMapping("/read-all") public void readAll(Authentication a){for(PortalNotification x:repo.findTop50ByRecipientUidOrderByCreatedAtDesc(a.getName())){x.setReadFlag(true);repo.save(x);}}
}