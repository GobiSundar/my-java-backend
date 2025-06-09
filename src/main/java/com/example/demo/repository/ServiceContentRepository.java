package com.example.demo.repository;

import com.example.demo.model.ServiceContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceContentRepository extends JpaRepository<ServiceContent, Long> {
    List<ServiceContent> findByServiceType(String serviceType);
}
