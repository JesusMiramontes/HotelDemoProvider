package com.miramontes.hoteldemoprovider.config;

import com.miramontes.hoteldemoprovider.model.AmenityModel;
import com.miramontes.hoteldemoprovider.model.HotelModel;
import com.miramontes.hoteldemoprovider.repository.AmenityRepository;
import com.miramontes.hoteldemoprovider.repository.HotelRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDb implements CommandLineRunner {
    private final HotelRepository hotelRepository;

    private final AmenityRepository amenityRepository;

    public SeedDb(HotelRepository hotelRepository, AmenityRepository amenityRepository) {
        this.hotelRepository = hotelRepository;
        this.amenityRepository = amenityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAmenities();
        seedHotels();
    }

    private void seedAmenities() {
        amenityRepository.saveAll(
                Arrays.asList(
                        AmenityModel.builder().name("WiFi").build(),
                        AmenityModel.builder().name("PPV").build(),
                        AmenityModel.builder().name("Pool Access").build()));
    }

    private void seedHotels() {
        List<AmenityModel> amenities = amenityRepository.findAll();
        hotelRepository.saveAll(
                Collections.singletonList(
                        HotelModel.builder()
                                .name("Lorem")
                                .address("ipsum")
                                .rating(5)
                                .amenities(amenities)
                                .build()));
    }
}
