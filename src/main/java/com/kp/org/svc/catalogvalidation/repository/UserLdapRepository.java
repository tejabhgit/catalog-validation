package com.kp.org.svc.catalogvalidation.repository;

import com.kp.org.svc.catalogvalidation.domain.UserLdap;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLdapRepository extends LdapRepository<UserLdap> {


    UserLdap findByUsernameAndPassword(String username, String password);
    UserLdap findByUsername(String username);

}
