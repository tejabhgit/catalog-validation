package com.kp.org.svc.catalogvalidation.config;


import com.kp.org.svc.catalogvalidation.domain.UserLdap;
import com.kp.org.svc.catalogvalidation.domain.UserSession;
import com.kp.org.svc.catalogvalidation.repository.UserLdapRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private UserLdapRepository userLdapRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        log.info("Authenticating for user: {} with password: {}",username,password);
        try {
            UserLdap userLdap = userLdapRepository.findByUsernameAndPassword(username,password);  //superuser authentication
            //UserLdap user = userLdapRepository.findByUsername(username); //user invalid or valid ?

            log.info(userLdap.toString());
            if(userLdap == null){
                throw new BadCredentialsException("Invalid username and password combination");
            }
        } catch (Exception e){
            log.error("{}",e.getMessage(),e);
            throw ((e instanceof BadCredentialsException) ? (BadCredentialsException) e :
                    new BadCredentialsException("Unable to authenticate user: "+username));
        }

        UserSession userSession = new UserSession(username,"", Arrays.asList(new SimpleGrantedAuthority("USER")));
        return new UsernamePasswordAuthenticationToken(userSession,password,Arrays.asList(new SimpleGrantedAuthority("USER")));
    }

}
