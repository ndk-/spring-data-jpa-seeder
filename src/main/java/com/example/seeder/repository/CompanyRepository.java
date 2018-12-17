package com.example.seeder.repository;

import com.example.seeder.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "company", path = "company")
public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
