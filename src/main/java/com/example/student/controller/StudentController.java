package com.example.student.controller;

import org.springframework.web.bind.annotation.*;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return repository.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents(@RequestParam(required = false) Integer course,
                                        @RequestParam(required = false) Double minGrade) {
        if (course != null && minGrade != null) {
            return repository.findByCourse(course).stream()
                    .filter(student -> student.getAverageGrade() >= minGrade)
                    .toList();
        }
        if (course != null) {
            return repository.findByCourse(course);
        }
        if (minGrade != null) {
            return repository.findByAverageGradeGreaterThanEqual(minGrade);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return repository.findById(id).map(student -> {
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setCourse(updatedStudent.getCourse());
            student.setAverageGrade(updatedStudent.getAverageGrade());
            return repository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
