package com.example.magazine.Repository;

import com.example.magazine.Entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long>{
    Optional<Magazine> findByName(String magazine_name);
}
