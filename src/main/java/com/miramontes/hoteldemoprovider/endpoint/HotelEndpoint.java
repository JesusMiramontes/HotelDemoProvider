package com.miramontes.hoteldemoprovider.endpoint;

import com.miramontes.hoteldemoprovider.repository.HotelRepository;
import com.miramontes.hoteldemoprovider.util.HotelUtil;
import com.miramontes.xsdclasses.GetHotelListRequest;
import com.miramontes.xsdclasses.GetHotelListResponse;
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
    public GetHotelListResponse getHotelList(@RequestPayload GetHotelListRequest request) {
        GetHotelListResponse response = new GetHotelListResponse();
        response.getHotels()
                .addAll(
                        hotelRepository.findAll().stream()
                                .map(HotelUtil::convertModelToWs)
                                .collect(Collectors.toList()));
        return response;
    }
}
