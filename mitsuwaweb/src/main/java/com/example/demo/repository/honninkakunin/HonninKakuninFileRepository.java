package com.example.demo.repository.honninkakunin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.honninkakunin.HonninKakuninFile;

@Repository
public interface HonninKakuninFileRepository extends JpaRepository<HonninKakuninFile, Integer> {

}
