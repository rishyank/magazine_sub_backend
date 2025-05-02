package com.example.magazine.Config;

import com.example.magazine.Entity.Magazine;
import com.example.magazine.Entity.Plan;
import com.example.magazine.Repository.MagazineRepository;
import com.example.magazine.Repository.PlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class DatabaseSeeder {
    private final PlanRepository planRepository;
    private final MagazineRepository magazineRepository;
    public DatabaseSeeder(PlanRepository planRepository, MagazineRepository magazineRepository)
    {
        this.planRepository = planRepository;
        this.magazineRepository = magazineRepository;
    }

    @Bean
    @Transactional
    public CommandLineRunner seedPlans() {
        return args -> {
            if (planRepository.count() == 0) {
                planRepository.save(new Plan("Silver Plan", "Basic plan, renews monthly", 1, 1, 0.0));
                planRepository.save(new Plan("Gold Plan", "Renews every 3 months", 3, 2, 0.05));
                planRepository.save(new Plan("Platinum Plan", "Renews every 6 months", 6, 3, 0.10));
                planRepository.save(new Plan("Diamond Plan", "Renews annually", 12, 4, 0.25));
                System.out.println("✅ Default plans seeded successfully!");
            } else {
                System.out.println("✅ Plans already exist, skipping seeding.");
            }
        };
    }


    @Bean
    @Transactional
    public CommandLineRunner seedMagazines(MagazineRepository magazineRepository) {
        return args -> {
            if (magazineRepository.count() == 0) {
                List<Magazine> defaultMagazines = List.of(
                        new Magazine(
                                "National Geographic",
                                "Exploring the world and all that's in it.",
                                150,
                                "/images/geo.jpg"
                        ),
                        new Magazine(
                                "Time",
                                "Breaking news and current events.",
                                120,
                                "/images/time.jpg"
                        ),
                        new Magazine(
                                "Vogue",
                                "Fashion and lifestyle magazine.",
                                200,
                                "/images/vogue.jpg"
                        ),
                        new Magazine(
                                "Forbes",
                                "Business and investment insights.",
                                180,
                                "/images/forbes.jpg"
                        ),
                        new Magazine(
                                "Scientific American",
                                "Science and technology news.",
                                160,
                                "/images/science.jpg"
                        )
                );

                magazineRepository.saveAll(defaultMagazines);
                System.out.println("✅ Default magazines seeded successfully!");
            } else {
                System.out.println("✅ Magazines already exist, skipping seeding.");
            }
        };
    }
}