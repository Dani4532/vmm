package com.example.vmm.repository;

import com.example.vmm.entity.Grade;
import com.example.vmm.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {



    @Query("""
            select student
            from STUDENTS student
            order by student.firstName asc 
            """)
    List<Student> findAllSorted();

}
