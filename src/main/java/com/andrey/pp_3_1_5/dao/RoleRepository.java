package com.andrey.pp_3_1_5.dao;


import com.andrey.pp_3_1_5.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
public Role findByRoleName(String role);

}
