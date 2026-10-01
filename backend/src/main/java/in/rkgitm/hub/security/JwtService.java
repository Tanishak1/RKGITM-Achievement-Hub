package in.rkgitm.hub.security;
import io.jsonwebtoken.*;import io.jsonwebtoken.security.Keys;import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Service;import javax.crypto.SecretKey;import java.nio.charset.StandardCharsets;import java.util.Date;
@Service public class JwtService{
 private final SecretKey key; public JwtService(@Value("${JWT_SECRET}") String secret){if(secret.length()<32)throw new IllegalArgumentException("JWT_SECRET must be at least 32 characters");this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}
 public String create(String username,String role){return token(username,role,"access",7L*24*60*60*1000);}\n public String createRefresh(String username,String role){return token(username,role,"refresh",180L*24*60*60*1000);}\n private String token(String username,String role,String type,long ttl){return Jwts.builder().subject(username).claim("role",role).claim("type",type).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+ttl)).signWith(key).compact();}
 public Claims parse(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
}