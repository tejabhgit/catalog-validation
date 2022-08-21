package com.kp.org.svc.catalogvalidation.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Data
@NoArgsConstructor
@ToString

@Entry(
        base = "dc=kp,dc=com",
        objectClasses = {"top","person","organizationalPerson","inetOrgPerson"}
)
public class UserLdap {

    @Id
    private Name id;

    @DnAttribute(value = "ou")
    private String group;

    @Attribute(name = "uid")
    private String username;

    @Attribute(name = "userPassword")
    private String password;

}
