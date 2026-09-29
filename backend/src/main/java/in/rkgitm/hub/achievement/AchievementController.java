package in.rkgitm.hub.achievement;
import org.springframework.http.ResponseEntity;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.List;import java.util.Map;import java.util.LinkedHashMap;
@RestController @RequestMapping("/api/achievements")
public class AchievementController{
 private final AchievementRepository repo;public AchievementController(AchievementRepository repo){this.repo=repo;}
 @GetMapping("/public") public List<Achievement> approved(){return repo.findByStatusOrderByCreatedAtDesc(Achievement.Status.APPROVED);}
 @GetMapping("/pending") public List<Achievement> pending(){return repo.findByStatusOrderByCreatedAtDesc(Achievement.Status.PENDING);}
 @GetMapping("/faculty-summary") public Map<String,Object> facultySummary(){Map<String,Object> m=new LinkedHashMap<>();m.put("pending",repo.findByStatusOrderByCreatedAtDesc(Achievement.Status.PENDING).size());m.put("approved",repo.findByStatusOrderByCreatedAtDesc(Achievement.Status.APPROVED).size());m.put("rejected",repo.findByStatusOrderByCreatedAtDesc(Achievement.Status.REJECTED).size());return m;}
 @GetMapping("/admin") public List<Achievement> admin(@RequestParam(required=false) Achievement.Status status){return status==null?repo.findAll():repo.findByStatusOrderByCreatedAtDesc(status);}
 @PostMapping public Achievement submit(@RequestBody Achievement a,Authentication auth){a.setStudentUid(auth.getName());a.setStatus(Achievement.Status.PENDING);return repo.save(a);}
 @PatchMapping("/{id}/status") public ResponseEntity<Achievement> status(@PathVariable Long id,@RequestParam Achievement.Status value){return repo.findById(id).map(a->{a.setStatus(value);return ResponseEntity.ok(repo.save(a));}).orElse(ResponseEntity.notFound().build());}
}