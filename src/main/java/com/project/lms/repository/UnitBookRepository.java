package com.project.lms.repository;

import com.project.lms.entities.UnitBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitBookRepository extends CrudRepository<UnitBook, String> {


}
