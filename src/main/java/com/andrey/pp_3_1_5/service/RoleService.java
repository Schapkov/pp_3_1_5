package com.andrey.pp_3_1_5.service;


import com.andrey.pp_3_1_5.model.Role;


import java.util.List;



public interface RoleService  {

   List<Role> getAllRole();

   List<Role> getRoleById(List<Long> id);

   public Role findByRoleName(String role);
}
