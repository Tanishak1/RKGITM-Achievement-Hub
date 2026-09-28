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
        HikariDataSource ds = new HikariDataSource();

        // Render may contain either a normal PostgreSQL URI or an already-converted JDBC URL.
        if (raw.startsWith("jdbc:postgresql://")) {
            ds.setJdbcUrl(raw);
            ds.setUsername(env.getProperty("DATABASE_USER", "postgres"));
            String password = env.getProperty("DATABASE_PASSWORD");
            if (password != null && !password.isBlank()) ds.setPassword(password);
            return ds;
        }

        if (!raw.startsWith("postgresql://") && !raw.startsWith("postgres://")) {
            throw new IllegalArgumentException("DATABASE_URL must start with postgresql://, postgres://, or jdbc:postgresql://");
        }

        URI uri = URI.create(raw);
        if (uri.getHost() == null || uri.getPath() == null) {
            throw new IllegalArgumentException("DATABASE_URL is not a valid PostgreSQL URI");
        }

        String[] userInfo = uri.getRawUserInfo() == null ? new String[0] : uri.getRawUserInfo().split(":", 2);
        int port = uri.getPort() == -1 ? 5432 : uri.getPort();
        ds.setJdbcUrl("jdbc:postgresql://" + uri.getHost() + ":" + port + uri.getPath());
        if (userInfo.length > 0) ds.setUsername(URLDecoder.decode(userInfo[0], StandardCharsets.UTF_8));
        else ds.setUsername(env.getProperty("DATABASE_USER", "postgres"));
        if (userInfo.length > 1) ds.setPassword(URLDecoder.decode(userInfo[1], StandardCharsets.UTF_8));
        else {
            String password = env.getProperty("DATABASE_PASSWORD");
            if (password != null) ds.setPassword(password);
        }
        return ds;
    }
}
