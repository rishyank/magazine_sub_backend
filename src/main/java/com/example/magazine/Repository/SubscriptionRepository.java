package com.example.magazine.Repository;

import com.example.magazine.Entity.Subscription;
import com.example.magazine.Entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserAndIsActiveTrue(User user);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Subscription> findByUserAndMagazineIdAndIsActiveTrue(User user, Long magazineId);
}