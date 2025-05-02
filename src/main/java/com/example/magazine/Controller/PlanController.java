package com.example.magazine.Controller;

import com.example.magazine.Service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.magazine.Entity.Plan;

import java.util.List;

@RestController
@RequestMapping("api/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping("{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlans() {
        return ResponseEntity.ok(planService.allPlans());
    }
}
