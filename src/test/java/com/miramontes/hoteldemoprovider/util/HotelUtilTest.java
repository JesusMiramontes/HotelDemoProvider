package com.miramontes.hoteldemoprovider.util;

import static org.junit.jupiter.api.Assertions.*;

import com.miramontes.hoteldemoprovider.model.HotelModel;
import com.miramontes.xsdclasses.Hotel;
import org.junit.jupiter.api.Test;

class HotelUtilTest {

    @Test
    void convertModelToWs() {
        HotelModel model =
                HotelModel.builder().id(1).name("lorem").address("ipsum").rating(5).build();
        Hotel converted = HotelUtil.convertModelToWs(model);

        assertEquals(model.getId(), converted.getId());
        assertEquals(model.getName(), converted.getName());
        assertEquals(model.getAddress(), converted.getAddress());
        assertEquals(model.getRating(), converted.getRating());
    }

    @Test
    void convertWsToModel() {
        // Set up
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("ipsum");
        hotel.setAddress("et dolor");
        hotel.setRating(4);

        // Convert
        HotelModel converted = HotelUtil.convertWsToModel(hotel);

        // Assertions
        assertEquals(hotel.getId(), converted.getId());
        assertEquals(hotel.getName(), converted.getName());
        assertEquals(hotel.getAddress(), converted.getAddress());
        assertEquals(hotel.getRating(), converted.getRating());
    }
}
