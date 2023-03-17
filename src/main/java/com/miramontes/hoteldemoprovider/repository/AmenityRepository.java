package com.miramontes.hoteldemoprovider.repository;

import com.miramontes.hoteldemoprovider.model.AmenityModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<AmenityModel, Integer> {
    Optional<AmenityModel> findByNameContainingIgnoreCase(String name);
}
