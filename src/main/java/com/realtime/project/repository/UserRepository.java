package com.realtime.project.repository;

import com.realtime.project.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORY CLASS THAT CONTAINS ALL THE CRUD
 * OPERATIONS FOR THE USER
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {
}
