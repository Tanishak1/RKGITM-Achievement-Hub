package in.rkgitm.hub.config;

/**
 * Security is configured centrally in in.rkgitm.hub.security.SecurityConfig.
 * This class intentionally contains no SecurityFilterChain bean so public
 * routes such as /api/achievements/public are not intercepted by a second,
 * conflicting chain.
 */
public final class SecurityConfig {
 private SecurityConfig() {}
}
