package com.example.magazine.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "subscription")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subscription
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference  // Prevents infinite recursion with the user’s subscriptions
    private User user;

    // Subscription also belongs to a Magazine
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazine_id", nullable = false)
    private Magazine magazine;

    // Each Subscription is associated with one Plan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    // The price at renewal for this subscription (base_price - discount)
    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private LocalDate renewalDate;

    @Column(nullable = false)
    private boolean isActive = true;

    public Subscription() {
    }

    public Subscription(User user, Magazine magazine, Plan plan, double price, LocalDate renewalDate) {
        this.user = user;
        this.magazine = magazine;
        this.plan = plan;
        this.price = price;
        this.renewalDate = renewalDate;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", user=" + user +
                ", magazine=" + magazine +
                ", plan=" + plan +
                ", price=" + price +
                ", renewalDate=" + renewalDate +
                ", isActive=" + isActive +
                '}';
    }
}