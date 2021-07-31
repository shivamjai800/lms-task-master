package com.project.lms.Repository;

import com.project.lms.Entities.Book;
import com.project.lms.Entities.BookRecord;
import com.project.lms.Entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.script.ScriptEngine;
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
}

