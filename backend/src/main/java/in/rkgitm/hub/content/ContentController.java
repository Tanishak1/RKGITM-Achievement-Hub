package in.rkgitm.hub.content;
import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api/content") @CrossOrigin(origins="*")
public class ContentController{
 private final ContentItemRepository repo; public ContentController(ContentItemRepository r){repo=r;}
 @GetMapping("/public/{type}") public List<ContentItem> publicItems(@PathVariable ContentItem.Type type){return repo.findByTypeAndPublishedOrderByCreatedAtDesc(type,true);}
 @GetMapping("/admin") public List<ContentItem> all(){return repo.findAllByOrderByCreatedAtDesc();}
 @PostMapping("/admin") public ContentItem create(@RequestBody ContentItem item){return repo.save(item);}
 @PutMapping("/admin/{id}") public ResponseEntity<ContentItem> update(@PathVariable Long id,@RequestBody ContentItem in){return repo.findById(id).map(x->{x.setType(in.getType());x.setTitle(in.getTitle());x.setDescription(in.getDescription());x.setCategory(in.getCategory());x.setDepartment(in.getDepartment());x.setLinkUrl(in.getLinkUrl());x.setPublished(in.isPublished());return ResponseEntity.ok(repo.save(x));}).orElse(ResponseEntity.notFound().build());}
 @DeleteMapping("/admin/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){if(!repo.existsById(id))return ResponseEntity.notFound().build();repo.deleteById(id);return ResponseEntity.noContent().build();}
}