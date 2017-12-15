package com.wikift.support.service.role;

import com.wikift.model.role.RoleEntity;

public interface RoleService {

    RoleEntity findByRoleName(String rolename);

}
