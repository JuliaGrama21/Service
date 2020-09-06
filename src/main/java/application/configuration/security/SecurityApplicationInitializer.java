package application.configuration.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * We need this class to initialize spring web security.
 * Without this empty class application security will be disabled.
 */
public class SecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {}