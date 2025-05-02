package com.example.magazine.Controller;

import com.example.magazine.Entity.Magazine;
import com.example.magazine.Service.MagazineService;
import com.example.magazine.dtos.MagazineDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/magazines")
public class MagazineController {

    @Autowired
    MagazineService magazineService;

    @GetMapping
    public List<Magazine> getAllMagazines() {
        return magazineService.getAllMagazines();
    }
    @GetMapping("/{id}")
    public Magazine getMagazine(@PathVariable Long id) {
        return magazineService.getMagazineById(id);
    }
    @GetMapping(params = "name")
    public Magazine getMagazineByName(@RequestParam String name) {
        return magazineService.getMagazineByName(name);
    }
    @PostMapping
    public Magazine createMagazine(@RequestBody @Valid MagazineDto magazineDto) {
        return magazineService.createMagazine(magazineDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMagazineById(@PathVariable Long id) throws InterruptedException {
        magazineService.deleteMagazine(null, id);
        return ResponseEntity.ok("Magazine deleted successfully.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMagazineByName(@RequestParam String name) throws InterruptedException {
        magazineService.deleteMagazine(name, null);
        return ResponseEntity.ok("Magazine deleted successfully.");
    }
}
