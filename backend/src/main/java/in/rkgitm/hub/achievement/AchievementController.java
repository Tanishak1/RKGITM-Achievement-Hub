package in.rkgitm.hub.achievement;
import org.springframework.http.ResponseEntity;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api/achievements")
public class AchievementController{
 private final AchievementRepository repo;public AchievementController(AchievementRepository repo){this.repo=repo;}
 @GetMapping("/public") public List<Achievement> approved(){return repo.findByStatusOrderByCreatedAtDesc(Achievement.Status.APPROVED);}
 @GetMapping("/pending") public List<Achievement> pending(){return repo.findByStatusOrderByCreatedAtDesc(Achievement.Status.PENDING);}
 @GetMapping("/admin") public List<Achievement> admin(@RequestParam(required=false) Achievement.Status status){return status==null?repo.findAll():repo.findByStatusOrderByCreatedAtDesc(status);}
 @PostMapping public Achievement submit(@RequestBody Achievement a,Authentication auth){a.setStudentUid(auth.getName());a.setStatus(Achievement.Status.PENDING);return repo.save(a);}
 @PatchMapping("/{id}/status") public ResponseEntity<Achievement> status(@PathVariable Long id,@RequestParam Achievement.Status value){return repo.findById(id).map(a->{a.setStatus(value);return ResponseEntity.ok(repo.save(a));}).orElse(ResponseEntity.notFound().build());}
}