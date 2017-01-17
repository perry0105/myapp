package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.domain.Cup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Cup entity.
 */
@SuppressWarnings("unused")
public interface CupRepository extends JpaRepository<Cup,Long> {

}
