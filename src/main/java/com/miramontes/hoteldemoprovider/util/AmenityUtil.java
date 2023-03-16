package com.miramontes.hoteldemoprovider.util;

import com.miramontes.hoteldemoprovider.model.AmenityModel;
import com.miramontes.xsdclasses.Amenity;

public class AmenityUtil {

    private AmenityUtil() {}

    public static Amenity convertModelToWs(AmenityModel model) {
        Amenity amenity = new Amenity();
        amenity.setId(model.getId());
        amenity.setName(model.getName());
        return amenity;
    }

    public static AmenityModel convertWsToModel(Amenity ws) {
        AmenityModel model = new AmenityModel();
        model.setId(ws.getId());
        model.setName(ws.getName());
        return model;
    }
}
