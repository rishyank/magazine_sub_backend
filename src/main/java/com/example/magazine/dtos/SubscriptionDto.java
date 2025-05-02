package com.example.magazine.dtos;


import com.example.magazine.Entity.Subscription;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDate;

public class SubscriptionDto {

    /** ✅ Request DTO for Creating a Subscription **/
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubscriptionInput {

        @NotNull(message = "User ID cannot be empty.")
        @Positive(message = "User ID must be greater than zero.")
        private Long userId;

        @NotNull(message = "Magazine ID cannot be empty.")
        @Positive(message = "Magazine ID must be greater than zero.")
        private Long magazineId;

        @NotNull(message = "Plan ID cannot be empty.")
        @Positive(message = "Plan ID must be greater than zero.")
        private Long planId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubscriptionChangeRequest {

        @NotNull(message = "User ID cannot be empty.")
        @Positive(message = "User ID must be greater than zero.")
        private Long userId;

        @NotNull(message = "Magazine ID cannot be empty.")
        @Positive(message = "Magazine ID must be greater than zero.")
        private Long magazineId;

        @NotNull(message = "Plan ID cannot be empty.")
        @Positive(message = "Plan ID must be greater than zero.")
        private Long newPlanId;
    }

    /** ✅ Response DTO for Returning Subscription Details **/

    @Getter
    public static class SubscriptionResponse{

        private final Long id;
        private final String magazineName;
        private final Long magazineId;
        private final String planTitle;
        private final double price;
        private final LocalDate renewalDate;
        private final boolean isActive;

        public SubscriptionResponse(Subscription subscription) {
            this.id = subscription.getId();
            this.magazineName = subscription.getMagazine().getName();
            this.magazineId = subscription.getMagazine().getId();
            this.planTitle = subscription.getPlan().getTitle();
            this.price = subscription.getPrice();
            this.renewalDate = subscription.getRenewalDate();
            this.isActive = subscription.isActive();
        }

        public static SubscriptionResponse fromEntity(Subscription subscription) {
            return new SubscriptionResponse(subscription);
        }
    }


}
