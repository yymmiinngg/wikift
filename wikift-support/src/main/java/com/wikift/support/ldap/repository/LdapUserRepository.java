package com.wikift.support.ldap.repository;

import com.wikift.support.ldap.model.LdapUserModel;
import org.springframework.data.ldap.repository.LdapRepository;

public interface LdapUserRepository extends LdapRepository<LdapUserModel> {

}
