package com.miramontes.hoteldemoprovider.service;

import com.miramontes.hoteldemoprovider.model.AmenityModel;
import com.miramontes.hoteldemoprovider.model.HotelModel;
import com.miramontes.hoteldemoprovider.repository.HotelRepository;
import com.miramontes.hoteldemoprovider.util.AmenityUtil;
import com.miramontes.hoteldemoprovider.util.HotelUtil;
import com.miramontes.hoteldemoprovider.util.ResponseUtil;
import com.miramontes.xsdclasses.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    @Autowired private HotelRepository repository;
    @Autowired private AmenityService amenityService;

    public ResponseHotelList getResponseHotelList() {
        ResponseHotelList response = new ResponseHotelList();
        response.getHotels()
                .addAll(
                        repository.findAll().stream()
                                .map(HotelUtil::convertModelToWs)
                                .collect(Collectors.toList()));

        response.setResponseStatus(ResponseUtil.ok());
        return response;
    }

    public ResponseHotel getHotelById(GetByIdRequest request) {
        ResponseHotel response = new ResponseHotel();
        Optional<HotelModel> hotelModel = repository.findById(request.getId());

        if (hotelModel.isEmpty()) {
            response.setResponseStatus(ResponseUtil.notFound());
        } else {
            response.setResponseStatus(ResponseUtil.found());
            response.setHotel(HotelUtil.convertModelToWs(hotelModel.get()));
        }

        return response;
    }

    public Response delete(DeleteRequest request) {
        Response response = new Response();
        Optional<HotelModel> hotelModel = repository.findById(request.getId());
        repository.deleteById(request.getId());

        if (hotelModel.isPresent()) {
            response.setResponseStatus(ResponseUtil.deleted());
        } else {
            response.setResponseStatus(ResponseUtil.notFound());
        }
        return response;
    }

    public ResponseHotel update(UpdateRequest request) {
        ResponseHotel response = new ResponseHotel();
        Optional<HotelModel> optional = repository.findById(request.getHotel().getId());
        request.getHotel().getAmenities().clear();
        if (optional.isPresent()) {
            optional.get()
                    .getAmenities()
                    .forEach(
                            a ->
                                    request.getHotel()
                                            .getAmenities()
                                            .add(AmenityUtil.convertModelToWs(a)));
            repository.save(HotelUtil.convertWsToModel(request.getHotel()));

            response.setResponseStatus(ResponseUtil.updated());
            response.setHotel(HotelUtil.convertModelToWs(optional.get())); // fix here
        } else {
            response.setResponseStatus(ResponseUtil.notFound());
        }
        return response;
    }

    public ResponseHotel create(CreateHotelRequest request) {
        ResponseHotel response = new ResponseHotel();

        List<AmenityModel> amenities =
                amenityService.convertIdsListToAmenitiesList(request.getAmenitiesIds());

        HotelModel hotelModel =
                save(
                        HotelModel.builder()
                                .name(request.getName())
                                .address(request.getAddress())
                                .rating(request.getRating())
                                .amenities(amenities)
                                .build());

        response.setHotel(HotelUtil.convertModelToWs(hotelModel));
        response.setResponseStatus(ResponseUtil.created());
        return response;
    }

    public ResponseHotel updateAmenitiesByName(UpdateAmenitiesHotelLinkByNameRequest request) {
        ResponseHotel response = new ResponseHotel();

        List<AmenityModel> amenities =
                amenityService.convertAmenitiesNamesToList(request.getAmenityNames());

        Optional<HotelModel> hotel = findById(request.getHotelId());
        if (hotel.isPresent()) {
            hotel.get().getAmenities().clear();
            hotel.get().getAmenities().addAll(amenities);

            response.setHotel(HotelUtil.convertModelToWs(hotel.get()));
            response.setResponseStatus(ResponseUtil.updated());
        } else {
            response.setResponseStatus(ResponseUtil.notFound());
        }

        return response;
    }

    private Optional<HotelModel> findById(int id) {
        return repository.findById(id);
    }

    public HotelModel save(HotelModel hotel) {
        return repository.save(hotel);
    }

    public ResponseHotel updateAmenitiesById(UpdateAmenitiesHotelLinkByIdRequest request) {

        ResponseHotel response = new ResponseHotel();

        Optional<HotelModel> hotel = findById(request.getHotelId());

        if (hotel.isPresent()) {
            hotel.get().getAmenities().clear();
            hotel.get().getAmenities().addAll(convertAmenitiesIdsToList(request.getAmenityIds()));

            response.setHotel(HotelUtil.convertModelToWs(hotel.get()));
            response.setResponseStatus(ResponseUtil.updated());
        } else {
            response.setResponseStatus(ResponseUtil.notFound());
        }

        return response;
    }

    private List<AmenityModel> convertAmenitiesIdsToList(List<Integer> amenityIds) {
        List<AmenityModel> amenities = new ArrayList<>();
        amenityIds.forEach(
                id -> {
                    Optional<AmenityModel> byId = amenityService.findById(id);
                    byId.ifPresent(amenities::add);
                });
        return amenities;
    }
}
