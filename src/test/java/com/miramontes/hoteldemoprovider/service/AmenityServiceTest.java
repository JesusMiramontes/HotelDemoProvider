package com.miramontes.hoteldemoprovider.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.miramontes.hoteldemoprovider.model.AmenityModel;
import com.miramontes.hoteldemoprovider.repository.AmenityRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AmenityServiceTest {

    @Mock AmenityRepository repository;

    @InjectMocks AmenityService service;

    private static final AmenityModel dummyAmenity =
            new AmenityModel(1, "lorem", new ArrayList<>());
    private static final AmenityModel dummyAmenity2 =
            new AmenityModel(2, "ipsum", new ArrayList<>());
    private static final Optional<AmenityModel> optionalDummyAmenity = Optional.of(dummyAmenity);
    private static final Optional<AmenityModel> optionalDummyAmenity2 = Optional.of(dummyAmenity2);
    private static final Optional<AmenityModel> optionalNullDummyAmenity = Optional.empty();

    @BeforeEach
    void setUp() {}

    @Test
    void convertIdsListToAmenitiesList() {
        when(repository.findById(1)).thenReturn(optionalDummyAmenity);
        when(repository.findById(2)).thenReturn(optionalDummyAmenity2);

        List<AmenityModel> retrieved =
                service.convertIdsListToAmenitiesList(Arrays.asList(1, 2, 3));
        assertArrayEquals(
                Arrays.asList(dummyAmenity, dummyAmenity2).toArray(), retrieved.toArray());
    }

    @Test
    void getAmenityById_existing() {
        when(repository.findById(1)).thenReturn(optionalDummyAmenity);
        Optional<AmenityModel> retrieved = service.getAmenityById(1);

        assertNotNull(retrieved);
        assertTrue(retrieved.isPresent());
        assertEquals(dummyAmenity, retrieved.get());
    }

    @Test
    void getAmenityById_nonExisting() {
        when(repository.findById(100)).thenReturn(optionalNullDummyAmenity);
        Optional<AmenityModel> retrieved = service.getAmenityById(100);

        assertNotNull(retrieved);
        assertTrue(retrieved.isEmpty());
    }

    @Test
    void findByNameContainingIgnoreCase() {
        when(repository.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(optionalDummyAmenity);
        Optional<AmenityModel> retrieved = service.findByNameContainingIgnoreCase(anyString());

        assertNotNull(retrieved);
        assertTrue(retrieved.isPresent());
        assertEquals(dummyAmenity, retrieved.get());
    }

    @Test
    void convertAmenitiesNamesToList() {
        when(repository.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(optionalDummyAmenity);

        List<AmenityModel> retrieved = service.convertAmenitiesNamesToList(List.of("lorem"));

        assertArrayEquals(List.of(dummyAmenity).toArray(), retrieved.toArray());
    }
}
