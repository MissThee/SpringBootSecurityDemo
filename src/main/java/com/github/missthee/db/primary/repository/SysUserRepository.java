package com.github.missthee.db.primary.repository;

import com.github.missthee.db.primary.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SysUserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {

    @Query(value = "select t from SysUser t where t.username=:username")
    SysUser findFirstByUsernameQuery(@Param("username") String username);

    Optional<SysUser> findFirstByUsername(String username);

    Page<SysUser> findAll(Specification<SysUser> specification, Pageable pageable);

}
