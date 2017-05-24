package com.innvo.repository;

import com.innvo.domain.Personpersonmbr;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Personpersonmbr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonpersonmbrRepository extends JpaRepository<Personpersonmbr,Long> {

}
