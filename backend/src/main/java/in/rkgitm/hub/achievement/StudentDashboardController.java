package in.rkgitm.hub.achievement;
import in.rkgitm.hub.security.*;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/student")
public class StudentDashboardController{
 private final AchievementRepository achievements;private final PortalUserRepository users;
 public StudentDashboardController(AchievementRepository a,PortalUserRepository u){achievements=a;users=u;}
 private int points(Achievement a){if(a.getStatus()!=Achievement.Status.APPROVED)return 0;String c=Optional.ofNullable(a.getCategory()).orElse("").toLowerCase();if(c.contains("research")||c.contains("publication")||c.contains("patent"))return 250;if(c.contains("project")||c.contains("innovation"))return 180;if(c.contains("hackathon")||c.contains("competition")||c.contains("award"))return 150;if(c.contains("certif"))return 80;return 100;}
 private int score(String uid){return achievements.findByStudentUidOrderByCreatedAtDesc(uid).stream().mapToInt(this::points).sum();}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(Authentication auth){
  String uid=auth.getName();PortalUser me=users.findByUid(uid).orElseThrow();List<Achievement> mine=achievements.findByStudentUidOrderByCreatedAtDesc(uid);
  long approved=mine.stream().filter(x->x.getStatus()==Achievement.Status.APPROVED).count(),pending=mine.stream().filter(x->x.getStatus()==Achievement.Status.PENDING).count(),rejected=mine.stream().filter(x->x.getStatus()==Achievement.Status.REJECTED).count();
  int profile=40+(me.getName()!=null?20:0)+(me.getDepartment()!=null?20:0)+(mine.size()>0?20:0),score=mine.stream().mapToInt(this::points).sum();
  List<Map<String,Object>> board=new ArrayList<>();for(PortalUser u:users.findAll()){if(u.getRole()!=PortalUser.Role.STUDENT||!u.isActive())continue;long n=achievements.countByStudentUidAndStatus(u.getUid(),Achievement.Status.APPROVED);Map<String,Object> row=new LinkedHashMap<>();row.put("uid",u.getUid());row.put("name",u.getName()==null?u.getUid():u.getName());row.put("department",u.getDepartment());row.put("verified",n);row.put("score",score(u.getUid()));board.add(row);}
  board.sort((x,y)->Integer.compare(((Number)y.get("score")).intValue(),((Number)x.get("score")).intValue()));for(int i=0;i<board.size();i++)board.get(i).put("rank",i+1);
  int rank=board.stream().filter(x->uid.equals(x.get("uid"))).map(x->(Integer)x.get("rank")).findFirst().orElse(0);List<Map<String,Object>> dept=board.stream().filter(x->Objects.equals(me.getDepartment(),x.get("department"))).toList();for(int i=0;i<dept.size();i++)dept.get(i).put("departmentRank",i+1);
  String badge=score>=1500?"Campus Elite":score>=800?"Achiever":score>=400?"Rising Star":score>=100?"Explorer":"Newcomer";int next=score<100?100:score<400?400:score<800?800:score<1500?1500:score;
  Map<String,Object> out=new LinkedHashMap<>();out.put("name",me.getName());out.put("uid",uid);out.put("department",me.getDepartment());out.put("studyYear",me.getStudyYear());out.put("photoUrl",me.getPhotoUrl());out.put("lastActiveAt",me.getLastActiveAt());out.put("profileStrength",Math.min(profile,100));out.put("score",score);out.put("badge",badge);out.put("nextMilestone",next);out.put("pointsToNext",Math.max(0,next-score));out.put("rank",rank);out.put("totalPosts",mine.size());out.put("approved",approved);out.put("pending",pending);out.put("rejected",rejected);out.put("posts",mine);out.put("leaderboard",board.stream().limit(10).toList());out.put("departmentLeaderboard",dept.stream().limit(10).toList());return out;
 }
}