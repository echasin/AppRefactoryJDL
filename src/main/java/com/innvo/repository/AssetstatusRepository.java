package com.innvo.repository;

import com.innvo.domain.Assetstatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Assetstatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetstatusRepository extends JpaRepository<Assetstatus,Long> {

}
