package in.rkgitm.hub.content;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="content_items")
public class ContentItem{
 public enum Type{PROJECT,RESEARCH,EVENT,OPPORTUNITY}
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Type type;
 @Column(nullable=false) private String title;
 @Column(length=3000) private String description;
 private String category; private String department; private String linkUrl;
 private boolean published=false; private Instant createdAt=Instant.now();
 public Long getId(){return id;} public Type getType(){return type;} public void setType(Type v){type=v;}
 public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;} public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
 public String getLinkUrl(){return linkUrl;} public void setLinkUrl(String v){linkUrl=v;} public boolean isPublished(){return published;} public void setPublished(boolean v){published=v;}
 public Instant getCreatedAt(){return createdAt;}
}