package in.rkgitm.hub.security;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminBootstrap implements CommandLineRunner {
 private final PortalUserRepository users; private final Environment env; private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
 public SuperAdminBootstrap(PortalUserRepository users,Environment env){this.users=users;this.env=env;}
 @Override public void run(String... args){
  String uid=env.getProperty("SUPER_ADMIN_UID"); String password=env.getProperty("SUPER_ADMIN_PASSWORD");
  if(uid==null||uid.isBlank()||password==null||password.length()<12) return;
  if(users.findByRoleOrderByCreatedAtDesc(PortalUser.Role.ADMIN).isEmpty()){
   PortalUser u=new PortalUser();u.setUid(uid.trim());u.setPasswordHash(encoder.encode(password));u.setRole(PortalUser.Role.ADMIN);u.setName(env.getProperty("SUPER_ADMIN_NAME","Super Admin"));u.setDepartment("ADMIN");u.setActive(true);users.save(u);
   System.out.println("Super Admin bootstrap completed.");
  }
 }
}