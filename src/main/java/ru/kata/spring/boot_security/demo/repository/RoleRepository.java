package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    Role findByName(String name);

}
