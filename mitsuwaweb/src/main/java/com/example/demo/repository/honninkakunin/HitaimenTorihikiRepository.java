package com.example.demo.repository.honninkakunin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.honninkakunin.HitaimenTorihiki;

@Repository
public interface HitaimenTorihikiRepository extends JpaRepository<HitaimenTorihiki, Integer> {

}
