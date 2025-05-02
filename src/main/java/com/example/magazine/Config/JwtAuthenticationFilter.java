package com.example.magazine.Config;

import com.example.magazine.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Use constructor injection
    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService
                                 ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Retrieve "Authorization" header
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If no token or not "Bearer ", just continue the filter chain
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract the token from the header by excluding bearer
        final String jwtToken = authHeader.substring(7);

        // 3. Extract username from token
        String username;
        try {
            username = jwtService.extractUsername(jwtToken);
        } catch (Exception e) {
            // If token can't be parsed, continue filter chain (or throw)
            filterChain.doFilter(request, response);
            return;
        }

        // 4. If we have a username and SecurityContext is empty, try to authenticate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // Check if token is valid
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                // Create Authentication
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 5. Continue filter chain
        filterChain.doFilter(request, response);
    }
}