package com.example.demo.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.user.Telephone;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone, Integer> {

}
