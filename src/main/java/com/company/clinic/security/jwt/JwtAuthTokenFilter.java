package com.company.clinic.security.jwt;

import com.company.clinic.security.services.UserPrinciple;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.stream.Collectors.toList;

public class JwtAuthTokenFilter extends OncePerRequestFilter {
    private final JwtProvider tokenProvider;

    public JwtAuthTokenFilter(JwtProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwt(request);
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                String username = tokenProvider.getUserNameFromJwtToken(jwt);
                UserClaim roles = tokenProvider.getUserRoles(jwt);

                UserPrinciple principle = new UserPrinciple(
                        roles.getUserId(),
                        username,
                        roles.getEmail(),
                        "",
                        roles.getRoles().stream().map(SimpleGrantedAuthority::new).collect(toList())
                );

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principle, null, principle.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}