package com.example.logistics.repository;

import com.example.logistics.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository< Location, String> {
}
