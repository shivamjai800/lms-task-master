package com.project.lms.Repository;

import com.project.lms.Entities.UnitBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitBookRepository extends CrudRepository<UnitBook, String> {


}
