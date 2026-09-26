package in.rkgitm.hub.security;
import org.springframework.beans.factory.annotation.Value;import org.springframework.http.*;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.web.bind.annotation.*;import java.util.Map;
@RestController @RequestMapping("/api/auth") public class AuthController{
 private final JwtService jwt;private final String user,hash;
 public AuthController(JwtService jwt,@Value("${REVIEWER_USER}") String user,@Value("${REVIEWER_PASSWORD_HASH}") String hash){this.jwt=jwt;this.user=user;this.hash=hash;}
 public record Login(String username,String password){}
 @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Login l){if(user.equals(l.username())&&new BCryptPasswordEncoder().matches(l.password(),hash))return ResponseEntity.ok(Map.of("token",jwt.create(user,"FACULTY"),"role","FACULTY"));return ResponseEntity.status(401).body(Map.of("error","Invalid credentials"));}
}