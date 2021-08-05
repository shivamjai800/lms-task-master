package com.project.lms.service.implementation;

import com.project.lms.entities.UnitBook;
import com.project.lms.repository.UnitBookRepository;
import com.project.lms.service.UnitBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitBookServiceImplementation implements UnitBookService {

    @Autowired
    UnitBookRepository unitBookRepository;

    @Override
    public UnitBook getUnitBookById(String id) {
        return this.unitBookRepository.findById(id).get();
    }

    @Override
    public void updateUnitBookBy(UnitBook newUnitBook, String id) {

        UnitBook oldUnitBook = this.unitBookRepository.findById(id).get();
        oldUnitBook.setAssigned(newUnitBook.isAssigned());
        this.unitBookRepository.save(oldUnitBook);
    }
}
