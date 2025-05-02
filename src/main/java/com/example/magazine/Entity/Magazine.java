package com.example.magazine.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="magazine")
@JsonInclude(JsonInclude.Include.NON_NULL)  // Ensures only non-null fields are serialized
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private double base_price;  // must be > 0

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    @OneToMany(mappedBy = "magazine",fetch =FetchType.LAZY, cascade =CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<Subscription> subscriptions;
    public Magazine() {
    }
    public Magazine(String name, String description, double basePrice,String imageUrl) {
        this.name = name;
        this.description = description;
        this.base_price = basePrice;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getBase_price() {
        return base_price;
    }
    public void setBase_price(double base_price) {
        this.base_price = base_price;
    }

    public List<Subscription> getSubscriptions() { return subscriptions; }
    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}