package com.example.vmm.repository;

import com.example.vmm.entity.Grade;
import com.example.vmm.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
