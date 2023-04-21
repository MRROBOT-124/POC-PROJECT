package com.realtime.project.repository;

import com.realtime.project.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORY CLASS THAT CONTAINS ALL THE CRUD
 * OPERATIONS FOR THE CLIENT
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    public Optional<Client> findByClientId(String clientId);
}
