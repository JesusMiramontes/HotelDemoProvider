package com.miramontes.hoteldemoprovider.repository;

import com.miramontes.hoteldemoprovider.model.HotelModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelModel, Integer> {
    List<HotelModel> findByNameContainingIgnoreCase(String name);
}
