package in.rkgitm.hub.security;
import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/super-admin") public class SuperAdminController{
 private final PortalUserRepository users;private final RegistrationRequestRepository requests;
 public SuperAdminController(PortalUserRepository u,RegistrationRequestRepository r){users=u;requests=r;}
 @GetMapping("/overview") public Map<String,Object> overview(){
  Map<String,Object> m=new LinkedHashMap<>();
  m.put("registeredStudents",users.countByRole(PortalUser.Role.STUDENT));
  m.put("liveStudents",users.countByRoleAndActive(PortalUser.Role.STUDENT,true));
  m.put("registeredFaculty",users.countByRole(PortalUser.Role.FACULTY));
  m.put("liveFaculty",users.countByRoleAndActive(PortalUser.Role.FACULTY,true));
  m.put("pendingFaculty",requests.countByRequestedRoleAndStatus(RegistrationRequest.RequestedRole.FACULTY,RegistrationRequest.Status.PENDING));
  m.put("pendingStudents",requests.countByRequestedRoleAndStatus(RegistrationRequest.RequestedRole.STUDENT,RegistrationRequest.Status.PENDING));
  m.put("students",rows(PortalUser.Role.STUDENT));m.put("faculty",rows(PortalUser.Role.FACULTY));return m;
 }
 private List<Map<String,Object>> rows(PortalUser.Role role){return users.findByRoleOrderByCreatedAtDesc(role).stream().map(u->{Map<String,Object> x=new LinkedHashMap<>();x.put("uid",u.getUid());x.put("name",u.getName());x.put("department",u.getDepartment());x.put("active",u.isActive());x.put("createdAt",u.getCreatedAt());return x;}).toList();}
 @PatchMapping("/users/{uid}/active") public Map<String,Object> active(@PathVariable String uid,@RequestParam boolean value){PortalUser u=users.findByUid(uid).orElseThrow();u.setActive(value);users.save(u);return Map.of("uid",uid,"active",value);}
 @DeleteMapping("/faculty/{uid}") public Map<String,Object> deleteFaculty(@PathVariable String uid){PortalUser u=users.findByUid(uid).orElseThrow();if(u.getRole()!=PortalUser.Role.FACULTY)throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Only faculty accounts can be deleted here.");users.delete(u);return Map.of("deleted",true,"uid",uid);}
}