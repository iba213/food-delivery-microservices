package com.champsoft.fooddelivery.restaurants.infrastructure.persistence;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
public class RestaurantJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private RestaurantAddressEmbeddable address;

    @Column(nullable = false)
    private boolean open;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItemJpaEntity> menuItems = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestaurantAddressEmbeddable getAddress() {
        return address;
    }

    public void setAddress(RestaurantAddressEmbeddable address) {
        this.address = address;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<MenuItemJpaEntity> getMenuItems() {
        return menuItems;
    }
}
