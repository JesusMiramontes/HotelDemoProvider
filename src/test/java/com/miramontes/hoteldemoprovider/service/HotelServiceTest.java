package com.miramontes.hoteldemoprovider.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.miramontes.hoteldemoprovider.model.HotelModel;
import com.miramontes.hoteldemoprovider.repository.AmenityRepository;
import com.miramontes.hoteldemoprovider.repository.HotelRepository;
import com.miramontes.xsdclasses.Hotel;
import com.miramontes.xsdclasses.ResponseHotelList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock HotelRepository hotelRepository;

    @Mock AmenityRepository amenityRepository;

    @InjectMocks HotelService service;

    private static final HotelModel dummyHotelModel =
            HotelModel.builder().id(1).name("lorem").address("ipsum").rating(1).build();
    private static final HotelModel dummyHotelModel2 =
            HotelModel.builder().id(2).name("et dolor").address("sit").rating(2).build();
    private static final HotelModel dummyHotelModel3 =
            HotelModel.builder().id(3).name("amet").address("").rating(5).build();

    private static final List<HotelModel> listDummyModel =
            Arrays.asList(dummyHotelModel, dummyHotelModel2, dummyHotelModel3);

    @Test
    void getResponseHotelList() {
        when(hotelRepository.findAll()).thenReturn(listDummyModel);

        ResponseHotelList responseHotelList = service.getResponseHotelList();

        assertNotNull(responseHotelList);
        assertEquals(responseHotelList.getHotels().get(1).getClass(), Hotel.class);
    }

    @Test
    void getResponseHotelListByNameLike() {}

    @Test
    void getHotelById() {}

    @Test
    void delete() {}

    @Test
    void update() {}

    @Test
    void create() {}

    @Test
    void updateAmenitiesByName() {}

    @Test
    void save() {}

    @Test
    void updateAmenitiesById() {}
}
