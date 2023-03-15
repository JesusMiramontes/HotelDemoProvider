package com.miramontes.hoteldemoprovider.util;

import com.miramontes.hoteldemoprovider.model.HotelModel;
import com.miramontes.xsdclasses.Hotel;

public class HotelUtil {

    private HotelUtil() {}

    public static Hotel convertModelToWs(HotelModel model) {
        Hotel hotel = new Hotel();
        hotel.setAddress(model.getAddress());
        hotel.setName(model.getName());
        hotel.setRating(model.getRating());
        return hotel;
    }
}
