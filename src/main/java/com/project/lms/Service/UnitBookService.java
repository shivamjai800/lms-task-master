package com.project.lms.Service;

import com.project.lms.Entities.UnitBook;
import com.project.lms.Repository.UnitBookRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service("UnitBookService")
public interface UnitBookService{

    public UnitBook getUnitBookById(String id);
    public void updateUnitBookBy(UnitBook newUnitBook, String id);
}
