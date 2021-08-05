package com.project.lms.service;

import com.project.lms.entities.UnitBook;
import org.springframework.stereotype.Service;

@Service("UnitBookService")
public interface UnitBookService{

    public UnitBook getUnitBookById(String id);
    public void updateUnitBookBy(UnitBook newUnitBook, String id);
}
