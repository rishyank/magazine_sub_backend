package com.example.magazine.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "plan")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;
    String description;
    @Column(nullable = false)
    @JsonProperty("renewal_period")
    private  int renewalPeriod; // in months, cannot be zero
    @Column(nullable = false)
    private int tier;
    @Column(nullable = false)
    private double discount;   // e.g. 0.1 for 10%

    @OneToMany(mappedBy = "plan",fetch =FetchType.LAZY, cascade =CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<Subscription> subscriptions;
    public Plan() {}
    public Plan(String title, String description, int renewalPeriod, int tier, double discount) {
        this.title = title;
        this.description = description;
        this.renewalPeriod = renewalPeriod;
        this.tier = tier;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getRenewalPeriod() {
        return renewalPeriod;
    }
    public void setRenewalPeriod(int renewalPeriod) {
        this.renewalPeriod = renewalPeriod;
    }
    public int getTier() {
        return tier;
    }
    public void setTier(int tier) {
        this.tier = tier;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<Subscription> getSubscriptions() { return subscriptions; }
    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
