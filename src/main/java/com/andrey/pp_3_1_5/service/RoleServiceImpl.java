package com.andrey.pp_3_1_5.service;

import com.andrey.pp_3_1_5.dao.RoleRepository;
import com.andrey.pp_3_1_5.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRoleById(List<Long> id) {
        return roleRepository.findAllById(id);
    }

    @Override
    public Role findByRoleName(String role) {
        return roleRepository.findByRoleName(role);
    }

}



