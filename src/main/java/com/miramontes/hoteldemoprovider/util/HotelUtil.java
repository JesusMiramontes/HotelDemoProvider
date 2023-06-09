package com.miramontes.hoteldemoprovider.util;

import com.miramontes.hoteldemoprovider.model.HotelModel;
import com.miramontes.xsdclasses.Hotel;

public class HotelUtil {

    private HotelUtil() {}

    public static Hotel convertModelToWs(HotelModel model) {
        Hotel hotel = new Hotel();
        hotel.setId(model.getId());
        hotel.setAddress(model.getAddress());
        hotel.setName(model.getName());
        hotel.setRating(model.getRating());
        model.getAmenities()
                .forEach(a -> hotel.getAmenities().add(AmenityUtil.convertModelToWs(a)));
        return hotel;
    }

    public static HotelModel convertWsToModel(Hotel ws) {
        HotelModel model = new HotelModel();
        model.setId(ws.getId());
        model.setAddress(ws.getAddress());
        model.setName(ws.getName());
        model.setRating(ws.getRating());
        ws.getAmenities().forEach(a -> model.getAmenities().add(AmenityUtil.convertWsToModel(a)));
        return model;
    }
}
