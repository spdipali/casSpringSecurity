package com.demo.casspringsecurity.repository;

import com.demo.casspringsecurity.entity.EventAppClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventAppClientRepository extends JpaRepository<EventAppClient, String> {
}
