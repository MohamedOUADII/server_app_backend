package com.example.fullstackapp.repository;

import com.example.fullstackapp.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository <Server,Long>{
    Server findByIpAddress(String ipAddress);
}
