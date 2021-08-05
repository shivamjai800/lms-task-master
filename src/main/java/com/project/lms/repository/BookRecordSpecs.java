package com.project.lms.repository;

import com.project.lms.entities.BookRecord;
import com.project.lms.entities.Status;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class BookRecordSpecs {

    public static Specification<BookRecord> filterStatus(Status status)
    {
        return (bookRecordRoot, criteriaQuery,criteriaBuilder )-> {
            Predicate statusRequested, statusApproved, statusCancelled, statusReturned;
            List<Predicate> predicateList = new ArrayList<>();
            if(status.isReturned())
                predicateList.add(criteriaBuilder.equal(bookRecordRoot.get("status"),"RETURNED"));
            if(status.isApproved())
                predicateList.add(criteriaBuilder.equal(bookRecordRoot.get("status"),"APPROVED"));
            if(status.isCancelled())
                predicateList.add(criteriaBuilder.equal(bookRecordRoot.get("status"),"CANCELLED"));
            if(status.isRequested())
                predicateList.add(criteriaBuilder.equal(bookRecordRoot.get("status"),"REQUESTED"));
            Predicate allStatusCondition = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
//            System.out.println("Inside specs filter status = "+status.toString());

            return allStatusCondition;

        };

    }

    public static Specification<BookRecord> filterUsername(String username)
    {
        return (bookRecordRoot, criteriaQuery,criteriaBuilder )-> {
            Predicate userUsername = criteriaBuilder.equal(bookRecordRoot.get("userUsername"), username);
            return userUsername;

        };
    }
    public static Specification<BookRecord> filterByUsernameAndStatus(String username, Status status)
    {
        System.out.println("Inside custom specs filter by username and status = "+status.toString());
        return Specification.where(filterUsername(username)).and(filterStatus(status));
    }

//    public static Specification<BookRecord> getTopBooks()
//    {
//        return (bookRecordRoot, criteriaQuery,criteriaBuilder )-> {
//            Root<BookRecord> bookRecord = criteriaQuery.from(BookRecord.class);
//            Subquery<BookRecord> bookRecordSubquery = criteriaQuery.subquery(BookRecord.class);
//            bookRecordSubquery.select(bookRecordRoot);
//            bookRecordSubquery.where(criteriaBuilder.greaterThanOrEqualTo(bookRecord.get("startDateTime"), LocalDateTime.now().minusDays(30)));
//            bookRecordSubquery.groupBy(bookRecordRoot.get("bookId"));
//
//            List<Selection<?>> selections = new ArrayList<>();
//            Root<Book> book = criteriaQuery.from(Book.class);
//            selections.add(book.get("bookId"));
//            selections.add(book.get)
//
//        };
//    }
}

