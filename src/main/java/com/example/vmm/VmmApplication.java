package com.example.vmm;

import com.example.vmm.entity.Grade;
import com.example.vmm.loader.GradeLoader;
import com.example.vmm.repository.GradeRepository;
import com.example.vmm.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.Subject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@SpringBootApplication
public class VmmApplication implements ApplicationRunner {

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    StudentRepository studentRepository;

    public static void main(String[] args) {

        SpringApplication.run(VmmApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {


        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/grades.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split(",");
                gradeRepository.save(
                        new Grade(null,
                                splitted[0],
                                splitted[2],
                                LocalDate.parse(splitted[1]),
                                Integer.parseInt(splitted[0])
                        ));
                var saved = gradeRepository.save(new Grade(null,
                                                                    splitted[0],
                                                                    splitted[2],
                                                                    LocalDate.parse(splitted[1]),
                                                                    Integer.parseInt(splitted[3]
                                                                    )));
                // LazyInitialiazationException

                var studentGradeList = studentRepository.findById(splitted[0]).get().getGrades();
                var student = studentRepository.findById(splitted[0]).get();
                studentGradeList.add(saved);
                student.setGrades(studentGradeList);
                studentRepository.save(student);


            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


            /*
        var in = Files.lines(Path.of("C:\\Users\\danie\\OneDrive\\Schuljahr22-23\\Programieren\\thymeleaf-basics\\vmm\\src\\main\\resources\\grades.csv"));
        var grades = in.toList();
        var newGrades = grades.subList(1, grades.size());
        newGrades.forEach(grade -> {
            var array = grade.split(",");
            if (!(array[0] == "student_id")) {
                var saved = gradeRepository.save(new Grade(null, array[0], array[2], LocalDate.parse(array[1]), Integer.parseInt(array[3])));
                var studentGradeList = studentRepository.findById(array[0]).get().getGrades();
                var student = studentRepository.findById(array[0]).get();
                studentGradeList.add(saved);
                student.setGrades(studentGradeList);
                studentRepository.save(student);
            }
        });

             */
    }
}
