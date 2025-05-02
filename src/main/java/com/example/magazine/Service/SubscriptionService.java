package com.example.magazine.Service;

import com.example.magazine.Entity.Magazine;
import com.example.magazine.Entity.Plan;
import com.example.magazine.Entity.Subscription;
import com.example.magazine.Entity.User;
import com.example.magazine.Exception.CustomException;
import com.example.magazine.Repository.MagazineRepository;
import com.example.magazine.Repository.PlanRepository;
import com.example.magazine.Repository.SubscriptionRepository;
import com.example.magazine.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private PlanRepository planRepository;

    @Transactional
    public Subscription createSubscription(long userId, long magazineId, long planId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new CustomException("User not found with provided ID", HttpStatus.NOT_FOUND));
        Magazine magazine = magazineRepository.findById(magazineId)
                .orElseThrow(() -> new CustomException("Magazine not found with provided ID", HttpStatus.NOT_FOUND));
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException("Plan not found with provided ID", HttpStatus.NOT_FOUND));

        if (subscriptionRepository.findByUserAndMagazineIdAndIsActiveTrue(user, magazineId).isPresent()) {
            throw new CustomException("User already has an active subscription for this magazine. Please modify instead.",HttpStatus.CONFLICT);
        }

        double basePrice = magazine.getBase_price();
        double discount = plan.getDiscount();
        double priceAfterDiscount = basePrice - (basePrice * discount);

        // Next renewal date => now + plan.renewalPeriod months
        LocalDate renewalDate = LocalDate.now().plusMonths(plan.getRenewalPeriod());

        Subscription subscription = new Subscription(user, magazine, plan, priceAfterDiscount, renewalDate);

        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public Subscription modifySubscription(Long userId, Long magazineId, Long newPlanId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("No user found", HttpStatus.NOT_FOUND));

        Subscription existingSubscription = subscriptionRepository.findByUserAndMagazineIdAndIsActiveTrue(user, magazineId)
                .orElseThrow(() -> new RuntimeException("No active subscription found for this magazine"));


        if(existingSubscription.getPlan().getId() == newPlanId) {
            throw new CustomException("subscription exist for magazine for given plan",HttpStatus.CONFLICT);
        }

        existingSubscription.setActive(false);
        subscriptionRepository.save(existingSubscription); // update the current subscription status to flase

        return createSubscription(userId,magazineId,newPlanId);
    }

    public List<Subscription> getActiveSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("No user found", HttpStatus.NOT_FOUND));

        List<Subscription> activeSubscriptions = subscriptionRepository.findByUserAndIsActiveTrue(user);

        if (activeSubscriptions.isEmpty()) {
            throw new CustomException("No active subscriptions found for user.", HttpStatus.NO_CONTENT);
        }
        return activeSubscriptions;
    }

    @Transactional
    public void cancelSubscription(Long userId, Long subscriptionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("No user found", HttpStatus.NOT_FOUND));

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new CustomException("No user found", HttpStatus.NOT_FOUND));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new CustomException("Not allowed to cancel this subscription", HttpStatus.FORBIDDEN);
        }

        subscription.setActive(false);
        subscriptionRepository.save(subscription);
    }

}
