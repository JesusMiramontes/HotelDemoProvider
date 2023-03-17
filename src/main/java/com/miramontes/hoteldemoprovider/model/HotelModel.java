package com.miramontes.hoteldemoprovider.model;

import jakarta.persistence.*;
import java.util.ArrayList;
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

    @ManyToMany(
            cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "hotel_amenity",
            joinColumns = {@JoinColumn(name = "amenity_id")},
            inverseJoinColumns = {@JoinColumn(name = "hotel_id")})
    private List<AmenityModel> amenities = new ArrayList<>();
}
