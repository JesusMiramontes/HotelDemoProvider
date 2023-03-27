package com.miramontes.hoteldemoprovider.endpoint;

import com.miramontes.hoteldemoprovider.service.AmenityService;
import com.miramontes.hoteldemoprovider.service.HotelService;
import com.miramontes.xsdclasses.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HotelEndpoint {
    public static final String NAMESPACE = "http://localhost:8081/";
    private final HotelService hotelService;
    private final AmenityService amenityService;

    public HotelEndpoint(HotelService hotelService, AmenityService amenityService) {
        this.hotelService = hotelService;
        this.amenityService = amenityService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getHotelListRequest")
    @ResponsePayload
    public ResponseHotelList getHotelList() {
        return hotelService.getResponseHotelList();
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getHotelListByNameLikeRequest")
    @ResponsePayload
    public ResponseHotelList getHotelListRequestByNameLike(
            @RequestPayload GetHotelListByNameLikeRequest request) {
        return hotelService.getResponseHotelListByNameLike(request.getName());
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createHotelRequest")
    @ResponsePayload
    public ResponseHotel createHotel(@RequestPayload CreateHotelRequest request) {
        return hotelService.create(request);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getByIdRequest")
    @ResponsePayload
    public ResponseHotel getById(@RequestPayload GetByIdRequest request) {
        return hotelService.getHotelById(request);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteRequest")
    @ResponsePayload
    public Response delete(@RequestPayload DeleteRequest request) {
        return hotelService.delete(request);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateRequest")
    @ResponsePayload
    public ResponseHotel update(@RequestPayload UpdateRequest request) {
        return hotelService.update(request);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateAmenitiesHotelLinkByNameRequest")
    @ResponsePayload
    public ResponseHotel updateAmenitiesByName(
            @RequestPayload UpdateAmenitiesHotelLinkByNameRequest request) {
        return hotelService.updateAmenitiesByName(request);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateAmenitiesHotelLinkByIdRequest")
    @ResponsePayload
    public ResponseHotel updateAmenitiesById(
            @RequestPayload UpdateAmenitiesHotelLinkByIdRequest request) {
        return hotelService.updateAmenitiesById(request);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getAmenitiesListRequest")
    @ResponsePayload
    public ResponseAmenitiesList amenitiesList(@RequestPayload GetAmenitiesListRequest request) {
        return amenityService.getResponseAmenityList();
    }
}
