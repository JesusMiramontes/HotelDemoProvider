package com.miramontes.hoteldemoprovider.config;

import com.miramontes.hoteldemoprovider.model.HotelModel;
import com.miramontes.hoteldemoprovider.repository.HotelRepository;
import java.util.Collections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDb implements CommandLineRunner {
    private final HotelRepository hotelRepository;

    public SeedDb(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedHotels();
    }

    private void seedHotels() {
        hotelRepository.saveAll(
                Collections.singletonList(
                        HotelModel.builder().name("Lorem").address("ipsum").rating(5).build()));
    }
}
