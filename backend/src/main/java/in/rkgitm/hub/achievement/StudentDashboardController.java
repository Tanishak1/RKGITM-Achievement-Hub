package in.rkgitm.hub.achievement;
import in.rkgitm.hub.security.*;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/student")
public class StudentDashboardController{
 private final AchievementRepository achievements;private final PortalUserRepository users;
 public StudentDashboardController(AchievementRepository a,PortalUserRepository u){achievements=a;users=u;}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(Authentication auth){
  String uid=auth.getName();PortalUser me=users.findByUid(uid).orElseThrow();
  List<Achievement> mine=achievements.findByStudentUidOrderByCreatedAtDesc(uid);
  long approved=mine.stream().filter(x->x.getStatus()==Achievement.Status.APPROVED).count(),pending=mine.stream().filter(x->x.getStatus()==Achievement.Status.PENDING).count(),rejected=mine.stream().filter(x->x.getStatus()==Achievement.Status.REJECTED).count();
  int profile=40+(me.getName()!=null?20:0)+(me.getDepartment()!=null?20:0)+(mine.size()>0?20:0);int score=(int)approved*100;
  List<Map<String,Object>> board=new ArrayList<>();for(PortalUser u:users.findAll()){if(u.getRole()!=PortalUser.Role.STUDENT||!u.isActive())continue;long n=achievements.countByStudentUidAndStatus(u.getUid(),Achievement.Status.APPROVED);Map<String,Object> row=new LinkedHashMap<>();row.put("uid",u.getUid());row.put("name",u.getName()==null?u.getUid():u.getName());row.put("department",u.getDepartment());row.put("verified",n);row.put("score",n*100);board.add(row);}
  board.sort((x,y)->Long.compare(((Number)y.get("score")).longValue(),((Number)x.get("score")).longValue()));for(int i=0;i<board.size();i++)board.get(i).put("rank",i+1);
  int rank=board.stream().filter(x->uid.equals(x.get("uid"))).map(x->(Integer)x.get("rank")).findFirst().orElse(0);
  Map<String,Object> out=new LinkedHashMap<>();out.put("name",me.getName());out.put("uid",uid);out.put("department",me.getDepartment());out.put("profileStrength",Math.min(profile,100));out.put("score",score);out.put("rank",rank);out.put("totalPosts",mine.size());out.put("approved",approved);out.put("pending",pending);out.put("rejected",rejected);out.put("posts",mine);out.put("leaderboard",board.stream().limit(10).toList());return out;
 }
}