package com.sandbox.authify.server.rest.middleware.filter;

import com.sandbox.authify.core.application.service.KeyManager;
import com.sandbox.authify.core.common.exception.AppException;
import com.sandbox.authify.core.port.security.JwtService;
import com.sandbox.authify.core.port.service.AccessTokenBlacklist;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.PathContainer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.List;
import java.util.Optional;

@Component
@Order(2)
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final KeyManager keyManager;
    private final JwtService jwtService;
    private final AccessTokenBlacklist accessTokenBlacklist;

    private final PathPatternParser patternParser = new PathPatternParser();

    public static final String ERROR_JWT_AUTH_FILTER_UNAUTHORIZED = "JWT_AUTH_FILTER.UNAUTHORIZED";
    public static final String ERROR_JWT_AUTH_FILTER_TOKEN_REVOKED = "JWT_AUTH_FILTER.TOKEN_REVOKED";


    @Override
    @SneakyThrows
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
    ) {
        var authHeader = Optional.ofNullable(request.getHeader("Authorization")).orElse("");

        String bearerToken;
        if (authHeader.startsWith("Bearer") && authHeader.split("\\s+").length > 1) {
            bearerToken = authHeader.substring(7);
        } else {
            throw new AppException(ERROR_JWT_AUTH_FILTER_UNAUTHORIZED);
        }
        
        var claims = jwtService.verify(bearerToken, keyManager.getRsaPublicKey());
        request.setAttribute("jwtClaims", claims);

        if (Boolean.TRUE.equals(accessTokenBlacklist.contains(bearerToken))) {
            throw new AppException(ERROR_JWT_AUTH_FILTER_TOKEN_REVOKED);
        }

        filterChain.doFilter(request, response);
    }
    
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        var patterns = List.of(
                patternParser.parse("POST /api/v1/users")
        );

        return patterns.parallelStream()
                .noneMatch(pattern -> pattern.matches(PathContainer
                        .parsePath(String.format("%s %s", request.getMethod(), request.getRequestURI()))));
	}
}
