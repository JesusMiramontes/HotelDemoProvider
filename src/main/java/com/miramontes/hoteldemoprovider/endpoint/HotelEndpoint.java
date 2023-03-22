package com.miramontes.hoteldemoprovider.endpoint;

import com.miramontes.hoteldemoprovider.model.AmenityModel;
import com.miramontes.hoteldemoprovider.model.HotelModel;
import com.miramontes.hoteldemoprovider.repository.AmenityRepository;
import com.miramontes.hoteldemoprovider.repository.HotelRepository;
import com.miramontes.hoteldemoprovider.util.AmenityUtil;
import com.miramontes.hoteldemoprovider.util.HotelUtil;
import com.miramontes.xsdclasses.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HotelEndpoint {
    public static final String NAMESPACE = "http://localhost:8080/";
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;

    public HotelEndpoint(HotelRepository hotelRepository, AmenityRepository amenityRepository) {
        this.hotelRepository = hotelRepository;
        this.amenityRepository = amenityRepository;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getHotelListRequest")
    @ResponsePayload
    public ResponseHotelList getHotelList() {
        ResponseHotelList response = new ResponseHotelList();
        response.getHotels()
                .addAll(
                        hotelRepository.findAll().stream()
                                .map(HotelUtil::convertModelToWs)
                                .collect(Collectors.toList()));
        response.setCode(HttpStatus.OK.value());
        response.setMsg(HttpStatus.OK.name());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createHotelRequest")
    @ResponsePayload
    public ResponseHotel createHotel(@RequestPayload CreateHotelRequest request) {
        ResponseHotel response = new ResponseHotel();
        HotelModel hotelModel =
                hotelRepository.save(
                        HotelModel.builder()
                                .name(request.getName())
                                .address(request.getAddress())
                                .rating(request.getRating())
                                .build());

        response.setHotel(HotelUtil.convertModelToWs(hotelModel));
        response.setCode(HttpStatus.CREATED.value());
        response.setMsg(HttpStatus.CREATED.name());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getByIdRequest")
    @ResponsePayload
    public ResponseHotel getById(@RequestPayload GetByIdRequest request) {
        ResponseHotel response = new ResponseHotel();
        Optional<HotelModel> hotelModel = hotelRepository.findById(request.getId());

        if (hotelModel.isEmpty()) {
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMsg(HttpStatus.NOT_FOUND.name());
        } else {
            response.setCode(HttpStatus.OK.value());
            response.setMsg("FOUND");
            response.setHotel(HotelUtil.convertModelToWs(hotelModel.get()));
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteRequest")
    @ResponsePayload
    public Response delete(@RequestPayload DeleteRequest request) {
        Response response = new Response();
        Optional<HotelModel> hotelModel = hotelRepository.findById(request.getId());
        hotelRepository.deleteById(request.getId());

        if (hotelModel.isPresent()) {
            response.setCode(HttpStatus.OK.value());
            response.setMsg("DELETED");
        } else {
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMsg(HttpStatus.NOT_FOUND.name());
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateRequest")
    @ResponsePayload
    public ResponseHotel update(@RequestPayload UpdateRequest request) {
        ResponseHotel response = new ResponseHotel();
        Optional<HotelModel> optional = hotelRepository.findById(request.getHotel().getId());
        request.getHotel().getAmenities().clear();
        if (optional.isPresent()) {
            optional.get()
                    .getAmenities()
                    .forEach(
                            a ->
                                    request.getHotel()
                                            .getAmenities()
                                            .add(AmenityUtil.convertModelToWs(a)));
            hotelRepository.save(HotelUtil.convertWsToModel(request.getHotel()));

            response.setCode(HttpStatus.OK.value());
            response.setMsg("UPDATED");
            response.setHotel(
                    HotelUtil.convertModelToWs(
                            hotelRepository.findById(request.getHotel().getId()).get()));
        } else {
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMsg(HttpStatus.NOT_FOUND.name());
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateAmenitiesHotelLinkByNameRequest")
    @ResponsePayload
    public ResponseHotel updateAmenitiesByName(
            @RequestPayload UpdateAmenitiesHotelLinkByNameRequest request) {
        ResponseHotel response = new ResponseHotel();

        List<AmenityModel> amenities = new ArrayList<>();
        request.getAmenityNames()
                .forEach(
                        name -> {
                            Optional<AmenityModel> optionalAmenityModel =
                                    amenityRepository.findByNameContainingIgnoreCase(name);

                            optionalAmenityModel.ifPresent(amenities::add);
                        });

        Optional<HotelModel> hotel = hotelRepository.findById(request.getHotelId());
        if (hotel.isPresent()) {
            hotel.get().getAmenities().clear();
            hotel.get().getAmenities().addAll(amenities);

            response.setHotel(HotelUtil.convertModelToWs(hotel.get()));
            response.setCode(HttpStatus.OK.value());
            response.setMsg("UPDATED");
        } else {
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMsg("HOTEL NOT FOUND");
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateAmenitiesHotelLinkByIdRequest")
    @ResponsePayload
    public ResponseHotel updateAmenitiesById(
            @RequestPayload UpdateAmenitiesHotelLinkByIdRequest request) {
        ResponseHotel response = new ResponseHotel();

        List<AmenityModel> amenities = new ArrayList<>();
        request.getAmenityIds()
                .forEach(
                        id -> {
                            Optional<AmenityModel> byId = amenityRepository.findById(id);
                            byId.ifPresent(amenities::add);
                        });

        Optional<HotelModel> hotel = hotelRepository.findById(request.getHotelId());

        if (hotel.isPresent()) {
            hotel.get().getAmenities().clear();
            hotel.get().getAmenities().addAll(amenities);

            response.setHotel(HotelUtil.convertModelToWs(hotel.get()));
            response.setCode(HttpStatus.OK.value());
            response.setMsg("UPDATED");
        } else {
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMsg("HOTEL NOT FOUND");
        }

        return response;
    }
}
