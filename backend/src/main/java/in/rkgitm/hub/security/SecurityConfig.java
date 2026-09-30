package in.rkgitm.hub.security;
import org.springframework.context.annotation.*;import org.springframework.beans.factory.annotation.Value;import org.springframework.web.cors.*;import java.util.List;import org.springframework.security.config.annotation.web.builders.HttpSecurity;import org.springframework.security.config.http.SessionCreationPolicy;import org.springframework.security.web.SecurityFilterChain;import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration public class SecurityConfig{
 private final JwtFilter jwt;
 @Value("${FRONTEND_URL:http://localhost:3000}") String frontendUrl;
 public SecurityConfig(JwtFilter jwt){this.jwt=jwt;}
 @Bean SecurityFilterChain filter(HttpSecurity http)throws Exception{
  return http.csrf(c->c.disable()).cors(c->{})
   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a
    .requestMatchers("/api/auth/login","/api/registration","/api/registration/faculty-status/**","/api/registration/student-status/**","/api/achievements/public","/api/content/public/**","/api/profile/**","/api/health").permitAll()
    .requestMatchers("/api/super-admin/**").hasRole("ADMIN")
    .requestMatchers("/api/registration/review/**").hasAnyRole("FACULTY","ADMIN")
    .requestMatchers(org.springframework.http.HttpMethod.POST,"/api/achievements").hasAnyRole("STUDENT","ADMIN")
    .requestMatchers("/api/content/admin/**").hasAnyRole("FACULTY","ADMIN")
    .requestMatchers("/api/achievements/pending","/api/achievements/admin","/api/achievements/*/status").hasAnyRole("FACULTY","ADMIN")
    .anyRequest().authenticated())
   .addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class).build();
 }
 @Bean CorsConfigurationSource corsConfigurationSource(){
  CorsConfiguration c=new CorsConfiguration();
  c.setAllowedOriginPatterns(List.of("*"));
  c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
  c.setAllowedHeaders(List.of("Authorization","Content-Type","Accept","Origin"));
  c.setExposedHeaders(List.of("Content-Disposition"));
  c.setMaxAge(3600L);
  UrlBasedCorsConfigurationSource src=new UrlBasedCorsConfigurationSource();
  src.registerCorsConfiguration("/**",c);
  return src;
 }
}