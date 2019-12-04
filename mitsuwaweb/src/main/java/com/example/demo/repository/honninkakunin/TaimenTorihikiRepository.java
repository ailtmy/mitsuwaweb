package com.example.demo.repository.honninkakunin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.honninkakunin.TaimenTorihiki;

@Repository
public interface TaimenTorihikiRepository extends JpaRepository<TaimenTorihiki, Integer> {

	Optional<TaimenTorihiki> findByHonninKakuninId(Integer id);

}
