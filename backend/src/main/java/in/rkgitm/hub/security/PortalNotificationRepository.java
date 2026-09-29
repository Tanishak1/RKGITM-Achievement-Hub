package in.rkgitm.hub.security;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.List;
public interface PortalNotificationRepository extends JpaRepository<PortalNotification,Long>{
 List<PortalNotification> findTop50ByRecipientUidOrderByCreatedAtDesc(String recipientUid);
 long countByRecipientUidAndReadFlag(String recipientUid,boolean readFlag);
}