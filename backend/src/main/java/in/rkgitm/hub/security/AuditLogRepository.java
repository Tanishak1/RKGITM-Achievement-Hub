package in.rkgitm.hub.security;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.List;import java.time.Instant;
public interface AuditLogRepository extends JpaRepository<AuditLog,Long>{
 List<AuditLog> findTop100ByOrderByCreatedAtDesc();
 long deleteByCreatedAtBefore(Instant cutoff);
}