package in.rkgitm.hub.achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AchievementRepository extends JpaRepository<Achievement,Long>{List<Achievement> findByStatusOrderByCreatedAtDesc(Achievement.Status status);}