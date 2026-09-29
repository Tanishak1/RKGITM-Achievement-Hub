package in.rkgitm.hub.security;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface PortalUserRepository extends JpaRepository<PortalUser,Long>{
 Optional<PortalUser> findByUid(String uid);boolean existsByUid(String uid);
 long countByRole(PortalUser.Role role);long countByRoleAndActive(PortalUser.Role role,boolean active);
 long countByRoleAndArchivedFalse(PortalUser.Role role);long countByRoleAndActiveAndArchivedFalse(PortalUser.Role role,boolean active);
 List<PortalUser> findByRoleOrderByCreatedAtDesc(PortalUser.Role role);
 List<PortalUser> findByRoleAndDepartmentOrderByCreatedAtDesc(PortalUser.Role role,String department);
}