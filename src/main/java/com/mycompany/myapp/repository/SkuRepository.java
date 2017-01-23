package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sku;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sku entity.
 */
@SuppressWarnings("unused")
public interface SkuRepository extends JpaRepository<Sku,Long> {

}
