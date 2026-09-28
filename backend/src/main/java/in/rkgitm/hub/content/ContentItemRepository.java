package in.rkgitm.hub.content;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.List;
public interface ContentItemRepository extends JpaRepository<ContentItem,Long>{List<ContentItem> findByTypeAndPublishedOrderByCreatedAtDesc(ContentItem.Type type,boolean published);List<ContentItem> findAllByOrderByCreatedAtDesc();}