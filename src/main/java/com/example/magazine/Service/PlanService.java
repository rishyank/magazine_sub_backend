package com.example.magazine.Service;

import com.example.magazine.Entity.Plan;
import com.example.magazine.Exception.CustomException;
import com.example.magazine.Repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;
    public List<Plan> allPlans() {
        try {
            List<Plan> plans = planRepository.findAll();

            if (plans.isEmpty()) {
                throw new CustomException("No plan found", HttpStatus.NO_CONTENT); // Optional
            }
            return plans;
        } catch (Exception e) {
            throw new CustomException("Error retrieving Plans", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Plan getPlanById(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new CustomException("Plan not found", HttpStatus.NOT_FOUND));
    }
}
