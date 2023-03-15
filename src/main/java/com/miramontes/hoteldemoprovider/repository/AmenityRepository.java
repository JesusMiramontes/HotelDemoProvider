package com.miramontes.hoteldemoprovider.repository;

import com.miramontes.hoteldemoprovider.model.AmenityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<AmenityModel, Integer> {}
