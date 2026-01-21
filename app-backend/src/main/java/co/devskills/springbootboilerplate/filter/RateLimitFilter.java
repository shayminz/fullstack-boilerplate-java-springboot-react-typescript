package co.devskills.springbootboilerplate.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import co.devskills.springbootboilerplate.service.RateLimiterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiter;

    public RateLimitFilter(RateLimiterService rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
    
        String clientId = request.getHeader("Authorization"); 
    
        if (clientId == null || clientId.isBlank()) {
            clientId = request.getRemoteAddr();  // fallback
        }
        System.out.println("RateLimitFilter hit for path: " + request.getRequestURI() + " | clientId: " + clientId);


        if (!rateLimiter.allowRequest(clientId)) {
            System.out.println("RateLimitFilter blocked request for clientId: " + clientId);

            response.setStatus(429);
            response.getWriter().write("Too Many Requests");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
