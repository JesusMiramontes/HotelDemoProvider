package com.miramontes.hoteldemoprovider.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "hotel")
public class HotelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String address;
    private Integer rating;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "hotel_amenity",
            joinColumns = {@JoinColumn(name = "hotel_id")},
            inverseJoinColumns = {@JoinColumn(name = "amenity_id")})
    private List<AmenityModel> amenities;
}
