package in.rkgitm.hub.security;
import org.springframework.beans.factory.annotation.Value;import org.springframework.http.*;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.web.bind.annotation.*;import java.util.Map;
@RestController @RequestMapping("/api/auth") public class AuthController{
 private final JwtService jwt;private final PortalUserRepository users;private final String legacyUser,legacyHash;private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
 public AuthController(JwtService jwt,PortalUserRepository users,@Value("${REVIEWER_USER:}") String user,@Value("${REVIEWER_PASSWORD_HASH:}") String hash){this.jwt=jwt;this.users=users;this.legacyUser=user;this.legacyHash=hash;}
 public record Login(String username,String password){}
 @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Login l){
  var found=users.findByUid(l.username());
  if(found.isPresent()){var u=found.get();if(u.isActive()&&encoder.matches(l.password(),u.getPasswordHash()))return ResponseEntity.ok(Map.of("token",jwt.create(u.getUid(),u.getRole().name()),"role",u.getRole().name(),"name",u.getName()==null?u.getUid():u.getName(),"uid",u.getUid()));}
  if(!legacyUser.isBlank()&&legacyUser.equals(l.username())&&!legacyHash.isBlank()&&encoder.matches(l.password(),legacyHash))return ResponseEntity.ok(Map.of("token",jwt.create(legacyUser,"FACULTY"),"role","FACULTY","uid",legacyUser));
  return ResponseEntity.status(401).body(Map.of("error","Invalid UID or password"));
 }
}