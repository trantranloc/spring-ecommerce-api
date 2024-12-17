package com.spring.springecommerceapi.repository;

import com.spring.springecommerceapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role , String> {
    Role findByName(String name);
}
