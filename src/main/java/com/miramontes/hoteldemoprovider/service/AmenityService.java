package com.miramontes.hoteldemoprovider.service;

import com.miramontes.hoteldemoprovider.model.AmenityModel;
import com.miramontes.hoteldemoprovider.repository.AmenityRepository;
import com.miramontes.hoteldemoprovider.util.AmenityUtil;
import com.miramontes.hoteldemoprovider.util.ResponseUtil;
import com.miramontes.xsdclasses.ResponseAmenitiesList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmenityService {

    @Autowired private AmenityRepository repository;

    public List<AmenityModel> convertIdsListToAmenitiesList(List<Integer> amenitiesIds) {
        List<AmenityModel> amenities = new ArrayList<>();
        amenitiesIds.forEach(
                id -> {
                    Optional<AmenityModel> byId = getAmenityById(id);
                    byId.ifPresent(amenities::add);
                });
        return amenities;
    }

    public Optional<AmenityModel> getAmenityById(Integer id) {
        return repository.findById(id);
    }

    public Optional<AmenityModel> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public List<AmenityModel> convertAmenitiesNamesToList(List<String> amenityNames) {
        List<AmenityModel> amenities = new ArrayList<>();

        amenityNames.forEach(
                name -> {
                    Optional<AmenityModel> optionalAmenityModel =
                            findByNameContainingIgnoreCase(name);

                    optionalAmenityModel.ifPresent(amenities::add);
                });
        return amenities;
    }

    public ResponseAmenitiesList getResponseAmenityList() {
        ResponseAmenitiesList response = new ResponseAmenitiesList();
        response.getAmenities()
                .addAll(
                        repository.findAll().stream()
                                .map(AmenityUtil::convertModelToWs)
                                .collect(Collectors.toList()));

        response.setResponseStatus(ResponseUtil.ok());
        return response;
    }
}
