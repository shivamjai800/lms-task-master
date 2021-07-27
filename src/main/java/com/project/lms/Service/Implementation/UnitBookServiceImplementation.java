package com.project.lms.Service.Implementation;

import com.project.lms.Entities.UnitBook;
import com.project.lms.Repository.UnitBookRepository;
import com.project.lms.Service.UnitBookService;
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
