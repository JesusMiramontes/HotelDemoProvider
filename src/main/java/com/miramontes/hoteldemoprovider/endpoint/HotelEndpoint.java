package com.miramontes.hoteldemoprovider.endpoint;

import com.miramontes.hoteldemoprovider.model.HotelModel;
import com.miramontes.hoteldemoprovider.repository.HotelRepository;
import com.miramontes.hoteldemoprovider.util.AmenityUtil;
import com.miramontes.hoteldemoprovider.util.HotelUtil;
import com.miramontes.xsdclasses.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HotelEndpoint {
    public static final String NAMESPACE = "http://localhost:8080/";
    private final HotelRepository hotelRepository;

    public HotelEndpoint(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getHotelListRequest")
    @ResponsePayload
    public GetHotelListResponse getHotelList() {
        GetHotelListResponse response = new GetHotelListResponse();
        response.getHotels()
                .addAll(
                        hotelRepository.findAll().stream()
                                .map(HotelUtil::convertModelToWs)
                                .collect(Collectors.toList()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createHotelRequest")
    @ResponsePayload
    public CreateHotelResponse createHotel(@RequestPayload CreateHotelRequest request) {
        CreateHotelResponse response = new CreateHotelResponse();
        HotelModel hotelModel =
                hotelRepository.save(HotelUtil.convertWsToModel(request.getHotel()));
        response.setHotel(HotelUtil.convertModelToWs(hotelModel));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getByIdRequest")
    @ResponsePayload
    public GetByIdResponse getById(@RequestPayload GetByIdRequest request) {
        GetByIdResponse response = new GetByIdResponse();
        Optional<HotelModel> hotelModel = hotelRepository.findById(request.getId());
        // TODO: Throw error if not found.
        response.setHotel(
                HotelUtil.convertModelToWs(
                        hotelModel.orElse(
                                new HotelModel(
                                        -1, "NOTFOUND", "NOTFOUND", -1, new ArrayList<>()))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteRequest")
    @ResponsePayload
    public Response delete(@RequestPayload DeleteRequest request) {
        Response response = new Response();
        Optional<HotelModel> hotelModel = hotelRepository.findById(request.getId());
        hotelRepository.deleteById(request.getId());

        if (hotelModel.isPresent()) {
            response.setStatus(200);
            response.setMsg("DELETED.");
        } else {
            response.setStatus(404);
            response.setMsg("NOT FOUND.");
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateRequest")
    @ResponsePayload
    public UpdateResponse update(@RequestPayload UpdateRequest request) {
        UpdateResponse response = new UpdateResponse();
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
        }
        response.setHotel(
                HotelUtil.convertModelToWs(
                        hotelRepository.findById(request.getHotel().getId()).get()));
        return response;
    }
}
