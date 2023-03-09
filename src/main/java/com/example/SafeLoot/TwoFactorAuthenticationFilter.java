package com.example.SafeLoot;



import com.example.SafeLoot.entity.User;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TwoFactorAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final GoogleAuthenticator googleAuthenticator;

    protected TwoFactorAuthenticationFilter(AuthenticationManager authenticationManager, GoogleAuthenticator googleAuthenticator) {
        super(new AntPathRequestMatcher("/login-2fa", "POST"));
        setAuthenticationManager(authenticationManager);
        this.googleAuthenticator = googleAuthenticator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String code = request.getParameter("code");
        User user = (User) auth.getPrincipal();
        boolean isCodeValid = googleAuthenticator.authorize(user.getEmail(), Integer.parseInt(code));

        if (!isCodeValid)       throw new BadCredentialsException("Invalid verification code");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(auth.getAuthorities());
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}
