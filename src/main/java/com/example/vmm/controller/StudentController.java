package com.example.vmm.controller;

import com.example.vmm.entity.Grade;
import com.example.vmm.loader.GradeLoader;
import com.example.vmm.repository.GradeRepository;
import com.example.vmm.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class StudentController{

    final private StudentRepository studentRepository;
    final private GradeRepository gradeRepository;


    public StudentController(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;


    }


    @GetMapping("/students")
    public String getAllStudents(@RequestParam(name = "studentId", required = false) String studentId,@RequestParam(name = "subject", required = false) String subject, @RequestParam(name = "radio", required = false) Integer grade, @RequestParam(name = "date", required = false) String date, Model model) throws IOException, InterruptedException {


        if (!(studentId == null)){
            var student = studentRepository.findById(studentId).get();
            var grades = student.getGrades();
            var newGrade = new Grade(null, studentId, subject, LocalDate.parse(date), grade);
            gradeRepository.save(newGrade);
            grades.add(newGrade);
            student.setGrades(grades);
            studentRepository.save(student);
        }


        model.addAttribute("content", studentRepository.findAllSorted());
        return "students";
    }

    @GetMapping("/detail/{id}")
    public String getDetailtoStudent(@PathVariable String id, Model model){
        model.addAttribute("student", studentRepository.findById(id).get());
        return "detailPage";
    }

}
