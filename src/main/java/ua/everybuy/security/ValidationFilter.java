package ua.everybuy.security;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ua.everybuy.buisnesslogic.service.integration.RequestSenderService;
import ua.everybuy.errorhandling.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.everybuy.routing.dto.request.ValidRequest;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final RequestSenderService requestSenderService;
    @Value("${auth.service.url}")
    private String authServiceUrl;
    private static final List<RequestMatcher> EXCLUDED_PATH_PATTERNS = List.of(
            new AntPathRequestMatcher("/swagger/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/**"),
            new AntPathRequestMatcher("/test/**"));

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (shouldNotFilter(request)) {
//            filterChain.doFilter(request, response);
            return;
        }

        String userId;
        ValidRequest validRequest;

        try {
            ResponseEntity<ValidRequest> exchange = requestSenderService.doRequest(request, authServiceUrl, ValidRequest.class);
            validRequest = exchange.getBody();
        } catch (HttpClientErrorException e) {
            int statusCode = e.getStatusCode().value();
            sendErrorMessage(response, e, statusCode);
            return;
        }

        if (validRequest != null) {
            userId = String.valueOf(validRequest.data().userId());
            List<SimpleGrantedAuthority> grantedAuthorities = validRequest.data().roles()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userId, null,
                    grantedAuthorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return EXCLUDED_PATH_PATTERNS
                .stream()
                .anyMatch(pattern -> pattern.matches(request));
    }

    private void sendErrorMessage(HttpServletResponse response, RuntimeException e, int statusCode) throws IOException {
        String message = statusCode == 401 ? "Unauthorized" : e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
        String json = objectMapper.writeValueAsString(errorResponse);
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}
