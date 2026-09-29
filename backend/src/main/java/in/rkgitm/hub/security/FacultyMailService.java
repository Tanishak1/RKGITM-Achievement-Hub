package in.rkgitm.hub.security;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@Service
public class FacultyMailService {
 private final ObjectMapper json=new ObjectMapper();
 @Value("${RESEND_API_KEY:}") private String apiKey;
 @Value("${RESEND_FROM_EMAIL:onboarding@resend.dev}") private String from;

 public void sendFacultyApproval(String to,String name,String uid){
  if(apiKey==null||apiKey.isBlank())throw new IllegalStateException("Email delivery is not configured.");
  if(to==null||to.isBlank())throw new IllegalArgumentException("Faculty email is missing.");
  try{
   String html="<div style='font-family:Arial,sans-serif;max-width:620px;margin:auto;padding:28px;color:#0f172a'>"
    +"<h2>RKGITM Achievement Hub</h2><p>Hello "+escape(name)+",</p>"
    +"<p>Your faculty account has been approved by the Super Admin.</p>"
    +"<p style='padding:18px;background:#eff6ff;border-radius:12px'><b>Faculty User ID</b><br><span style='font-size:24px'>"+escape(uid)+"</span></p>"
    +"<p>Use this User ID with the password you created during registration. Keep this ID private.</p>"
    +"<p>— RKGITM Achievement Hub</p></div>";
   String body=json.writeValueAsString(Map.of("from",from,"to",new String[]{to},"subject","Your RKGITM Faculty User ID","html",html));
   HttpRequest req=HttpRequest.newBuilder(URI.create("https://api.resend.com/emails"))
    .header("Authorization","Bearer "+apiKey).header("Content-Type","application/json")
    .POST(HttpRequest.BodyPublishers.ofString(body,StandardCharsets.UTF_8)).build();
   HttpResponse<String> res=HttpClient.newHttpClient().send(req,HttpResponse.BodyHandlers.ofString());
   if(res.statusCode()<200||res.statusCode()>=300){String detail=res.body()==null?"":res.body();System.err.println("RESEND_DELIVERY_FAILED status="+res.statusCode()+" body="+detail);throw new IllegalStateException("Email provider rejected delivery: "+res.statusCode()+" "+detail);}
  }catch(InterruptedException e){Thread.currentThread().interrupt();throw new IllegalStateException("Email delivery interrupted.");}
   catch(Exception e){if(e instanceof IllegalStateException i)throw i;throw new IllegalStateException("Email delivery failed.");}
 }
 private String escape(String s){return s==null?"":s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");}
}