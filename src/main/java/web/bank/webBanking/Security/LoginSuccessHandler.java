package web.bank.webBanking.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Configuration
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String target = determineTargerUrl(authentication);

        if(response.isCommitted()){
            return;
        }

        RedirectStrategy redirectStrategy =new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, target);
    }

    protected String determineTargerUrl(Authentication authentication){
        String url ="/login?error=true";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority a : authorities){
            roles.add(a.getAuthority());
        }

        if(roles.contains("ROLE_ADMIN")){
            url = "/admin";
        }else if (roles.contains("ROLE_CLIENT")){
            url = "/client";
        }else if(roles.contains("ROLE_BANKER")){
            url = "/banker";
        }

        return url;
    }
}