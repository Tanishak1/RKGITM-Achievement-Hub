package in.rkgitm.hub.achievement;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/achievements") @CrossOrigin
public class AchievementController{
 private final AchievementRepository repo; public AchievementController(AchievementRepository repo){this.repo=repo;}
 @GetMapping("/public") public List<Achievement> approved(){return repo.findByStatusOrderByCreatedAtDesc(Achievement.Status.APPROVED);}
 @PostMapping public Achievement submit(@RequestBody Achievement a){a.setStatus(Achievement.Status.PENDING);return repo.save(a);}
}