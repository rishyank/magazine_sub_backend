package com.example.magazine.Service;

import com.example.magazine.Entity.Magazine;
import com.example.magazine.Exception.CustomException;
import com.example.magazine.Repository.MagazineRepository;
import com.example.magazine.dtos.MagazineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MagazineService {
    @Autowired
    MagazineRepository magazineRepository;

    @Transactional(rollbackFor = Exception.class)
    public Magazine createMagazine(MagazineDto input) {

        Optional<Magazine> existingMagazine = magazineRepository.findByName(input.getName());
        if (existingMagazine.isPresent()) {
            throw  new CustomException("Magazine with name '" + input.getName() + "' already exists.", HttpStatus.CONFLICT);
        }

        if (input.getBase_price() <= 0) {
            throw new CustomException("Base price must be greater than zero",HttpStatus.BAD_REQUEST);
        }

        Magazine magazine = new Magazine(input.getName(), input.getDescription(), input.getBase_price(), input.getImage_url());
        return magazineRepository.save(magazine);
    };

    public Magazine getMagazineById(Long id) {
        return magazineRepository.findById(id)
                .orElseThrow(() -> new CustomException("Magazine not found",HttpStatus.NOT_FOUND));
    }

    public Magazine getMagazineByName(String name) {
        return magazineRepository.findByName(name)
                .orElseThrow(() -> new CustomException("Magazine not found",HttpStatus.NOT_FOUND));
    }

    public  List<Magazine> getAllMagazines() {
        try {
            List<Magazine> magazines = magazineRepository.findAll();

            if (magazines.isEmpty()) {
                throw new CustomException("No magazines found", HttpStatus.NO_CONTENT);
            }
            return magazines;
        } catch (Exception e) {
            throw new CustomException("Error retrieving  magazines", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public boolean deleteMagazine(String name, Long id) throws InterruptedException {

        Optional<Magazine> existingMagazine = Optional.empty();

        if (id != null) {
            existingMagazine = magazineRepository.findById(id);
        } else if (name != null && !name.isEmpty()) {
            existingMagazine = magazineRepository.findByName(name);
        }

        if (existingMagazine.isPresent()) {
            magazineRepository.delete(existingMagazine.get());
            return true;
        } else {
            throw new CustomException("Magazine not found with provided ID or Name", HttpStatus.NOT_FOUND);
        }
    }
}
