package com.melwin.qrmenu.entity;

import com.melwin.qrmenu.enums.RestaurantType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RestaurantType type;

    @Lob
    @Column(name = "address")
    private String address;

    @Column(name = "is_open")
    private Boolean isOpen;

    @Column(name = "is_active", insertable = false)
    private Boolean isActive;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Instant updatedAt;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

}