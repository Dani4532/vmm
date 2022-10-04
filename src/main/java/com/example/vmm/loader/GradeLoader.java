package com.example.vmm.loader;

import com.example.vmm.entity.Grade;
import com.example.vmm.repository.GradeRepository;
import com.example.vmm.repository.StudentRepository;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;


public class GradeLoader {

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    StudentRepository studentRepository;



    public void loadGrades() throws IOException, InterruptedException {
        var in = Files.lines(Path.of("C:\\Users\\danie\\OneDrive\\Schuljahr22-23\\Programieren\\thymeleaf-basics\\thymleafEmployeeTask\\vmm\\src\\main\\resources\\grades.csv"));
        var grades = in.toList();
        var newGrades = grades.subList(1, grades.size());
        newGrades.forEach(grade -> {
            var array = grade.split(",");
            var saved = gradeRepository.save(new Grade(null, array[0], array[2], LocalDate.parse(array[1]), Integer.parseInt(array[3])));
            var studentGradeList = studentRepository.findById(array[0]).get().getGrades();
            var student = studentRepository.findById(array[0]).get();
            studentGradeList.add(saved);
            student.setGrades(studentGradeList);
            studentRepository.save(student);
        });
    }

}
