package com.miramontes.hoteldemoprovider.util;

import static org.junit.jupiter.api.Assertions.*;

import com.miramontes.hoteldemoprovider.model.AmenityModel;
import com.miramontes.xsdclasses.Amenity;
import org.junit.jupiter.api.Test;

class AmenityUtilTest {

    @Test
    void convertModelToWs() {
        AmenityModel model = AmenityModel.builder().id(1).name("lorem").build();
        Amenity converted = AmenityUtil.convertModelToWs(model);

        assertEquals(model.getId(), converted.getId());
        assertEquals(model.getName(), converted.getName());
    }

    @Test
    void convertWsToModel() {
        Amenity amenity = new Amenity();
        amenity.setId(1);
        amenity.setName("ipsum");

        AmenityModel converted = AmenityUtil.convertWsToModel(amenity);

        assertEquals(amenity.getId(), converted.getId());
        assertEquals(amenity.getName(), converted.getName());
    }
}
