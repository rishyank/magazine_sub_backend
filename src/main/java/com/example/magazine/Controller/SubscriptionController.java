package com.example.magazine.Controller;

import com.example.magazine.Entity.Subscription;
import com.example.magazine.Service.SubscriptionService;
import com.example.magazine.dtos.SubscriptionDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody @Valid SubscriptionDto.SubscriptionInput inputDto){
        return ResponseEntity.ok(subscriptionService.createSubscription(
                 inputDto.getUserId()
                ,inputDto.getMagazineId()
                ,inputDto.getPlanId()));
    }

    // List all active subscriptions of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionDto.SubscriptionResponse>> getSubscriptions(@PathVariable Long userId) {
        List<Subscription> subscriptions = subscriptionService.getActiveSubscriptions(userId);

        // Convert Subscription entities to DTOs
        List<SubscriptionDto.SubscriptionResponse> subscriptionList = subscriptions.stream()
                .map(SubscriptionDto.SubscriptionResponse::new)
                .toList();

        return ResponseEntity.ok(subscriptionList);
    }
    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Boolean> cancelSubscription(
            @RequestParam Long userId,
            @PathVariable Long subscriptionId
    ) {
        try {
            subscriptionService.cancelSubscription(userId, subscriptionId);
            return ResponseEntity.ok(true); // HTTP 200 OK with body: true
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); // HTTP 500 with false
        }
    }

    @PutMapping("/change-plan")
    public ResponseEntity<SubscriptionDto.SubscriptionResponse> modifySubscription(@RequestBody @Valid SubscriptionDto.SubscriptionChangeRequest subscriptionChangeInput) {

       Subscription modifiedSubscription = subscriptionService.modifySubscription(
               subscriptionChangeInput.getUserId()
               ,subscriptionChangeInput.getMagazineId()
               ,subscriptionChangeInput.getNewPlanId());

        SubscriptionDto.SubscriptionResponse responseDto =
                SubscriptionDto.SubscriptionResponse.fromEntity(modifiedSubscription);

        return ResponseEntity.ok(responseDto);
    }
}
