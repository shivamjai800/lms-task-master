package com.project.lms.controller;

import com.project.lms.entities.User;
import com.project.lms.service.implementation.BookRecordServiceImpl;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    BookRecordServiceImpl bookRecordService;
    @GetMapping("/test")
    public String test()
    {
        List<Pair<User,Long>> l = this.bookRecordService.topUserRecords(3);
        return "welcome";
    }
}
