package in.rkgitm.hub.security;
import in.rkgitm.hub.achievement.*;import in.rkgitm.hub.content.*;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.time.Instant;import java.util.*;
@RestController @RequestMapping("/api/super-admin") public class SuperAdminController{
 private final PortalUserRepository users;private final RegistrationRequestRepository requests;private final AchievementRepository achievements;private final ContentItemRepository content;private final AuditLogRepository audits;private final ActivityService activity;
 public SuperAdminController(PortalUserRepository u,RegistrationRequestRepository r,AchievementRepository a,ContentItemRepository c,AuditLogRepository l,ActivityService activity){users=u;requests=r;achievements=a;content=c;audits=l;this.activity=activity;}
 @GetMapping("/overview") public Map<String,Object> overview(){
  Map<String,Object> m=new LinkedHashMap<>();
  m.put("registeredStudents",users.countByRoleAndArchivedFalse(PortalUser.Role.STUDENT));
  m.put("liveStudents",users.countByRoleAndActiveAndArchivedFalse(PortalUser.Role.STUDENT,true));
  m.put("registeredFaculty",users.countByRoleAndArchivedFalse(PortalUser.Role.FACULTY));
  m.put("liveFaculty",users.countByRoleAndActiveAndArchivedFalse(PortalUser.Role.FACULTY,true));
  m.put("pendingFaculty",requests.countByRequestedRoleAndStatus(RegistrationRequest.RequestedRole.FACULTY,RegistrationRequest.Status.PENDING));
  m.put("pendingStudents",requests.countByRequestedRoleAndStatus(RegistrationRequest.RequestedRole.STUDENT,RegistrationRequest.Status.PENDING));
  m.put("approvedAchievements",achievements.countByStatus(Achievement.Status.APPROVED));
  m.put("pendingAchievements",achievements.countByStatus(Achievement.Status.PENDING));
  m.put("projects",content.countByTypeAndPublished(ContentItem.Type.PROJECT,true));
  m.put("research",content.countByTypeAndPublished(ContentItem.Type.RESEARCH,true));
  m.put("events",content.countByTypeAndPublished(ContentItem.Type.EVENT,true));
  m.put("students",rows(PortalUser.Role.STUDENT,false));m.put("faculty",rows(PortalUser.Role.FACULTY,false));m.put("archivedFaculty",rows(PortalUser.Role.FACULTY,true));
  m.put("recentAudit",audits.findTop100ByOrderByCreatedAtDesc().stream().limit(12).toList());
  return m;
 }
 @GetMapping("/audit") public List<AuditLog> audit(){return audits.findTop100ByOrderByCreatedAtDesc();}
 private List<Map<String,Object>> rows(PortalUser.Role role,boolean archived){return users.findByRoleOrderByCreatedAtDesc(role).stream().filter(u->u.isArchived()==archived).map(u->{Map<String,Object> x=new LinkedHashMap<>();x.put("uid",u.getUid());x.put("name",u.getName());x.put("department",u.getDepartment());x.put("studyYear",u.getStudyYear());x.put("active",u.isActive());x.put("archived",u.isArchived());x.put("createdAt",u.getCreatedAt());x.put("lastActiveAt",u.getLastActiveAt());x.put("archivedAt",u.getArchivedAt());return x;}).toList();}
 @PatchMapping("/users/{uid}/active") public Map<String,Object> active(@PathVariable String uid,@RequestParam boolean value,Authentication auth){PortalUser u=users.findByUid(uid).orElseThrow();if(u.isArchived())throw new IllegalStateException("Archived account must be restored first.");u.setActive(value);users.save(u);activity.audit(auth.getName(),value?"ACCOUNT_ACTIVATED":"ACCOUNT_DEACTIVATED","USER",uid,u.getName());return Map.of("uid",uid,"active",value);}
 @DeleteMapping("/faculty/{uid}") public Map<String,Object> archiveFaculty(@PathVariable String uid,Authentication auth){PortalUser u=users.findByUid(uid).orElseThrow();if(u.getRole()!=PortalUser.Role.FACULTY)throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Only faculty accounts can be archived here.");u.setArchived(true);u.setActive(false);u.setArchivedAt(Instant.now());users.save(u);activity.audit(auth.getName(),"FACULTY_ARCHIVED","USER",uid,u.getName());return Map.of("archived",true,"uid",uid);}
 @PatchMapping("/faculty/{uid}/restore") public Map<String,Object> restoreFaculty(@PathVariable String uid,Authentication auth){PortalUser u=users.findByUid(uid).orElseThrow();if(u.getRole()!=PortalUser.Role.FACULTY)throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Only faculty accounts can be restored here.");u.setArchived(false);u.setActive(true);u.setArchivedAt(null);users.save(u);activity.audit(auth.getName(),"FACULTY_RESTORED","USER",uid,u.getName());return Map.of("restored",true,"uid",uid);}
}