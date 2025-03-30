package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.student.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByCourse(Integer course);
    List<Student> findByAverageGradeGreaterThanEqual(Double grade);
}