package in.rkgitm.hub.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class DatabaseConfig {
    @Bean
    DataSource dataSource(Environment env) {
        String raw = env.getProperty("DATABASE_URL");
        if (raw == null || raw.isBlank()) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:postgresql://localhost:5432/rkgitm_hub");
            ds.setUsername(env.getProperty("DATABASE_USER", "postgres"));
            ds.setPassword(env.getProperty("DATABASE_PASSWORD", "postgres"));
            return ds;
        }
        raw = raw.trim();
        if (raw.startsWith("jdbc:")) raw = raw.substring(5);
        URI uri = URI.create(raw);
        String[] userInfo = uri.getRawUserInfo() == null ? new String[0] : uri.getRawUserInfo().split(":", 2);
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://" + uri.getHost() + ":" + (uri.getPort() == -1 ? 5432 : uri.getPort()) + uri.getPath());
        if (userInfo.length > 0) ds.setUsername(URLDecoder.decode(userInfo[0], StandardCharsets.UTF_8));
        else ds.setUsername(env.getProperty("DATABASE_USER", "postgres"));
        if (userInfo.length > 1) ds.setPassword(URLDecoder.decode(userInfo[1], StandardCharsets.UTF_8));
        else ds.setPassword(env.getProperty("DATABASE_PASSWORD", ""));
        return ds;
    }
}
