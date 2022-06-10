package com.itpatagonia.practicaclase20220610.controller;

import com.itpatagonia.practicaclase20220610.exception.NoEntityException;
import com.itpatagonia.practicaclase20220610.model.Student;
import com.itpatagonia.practicaclase20220610.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students/v1")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @PostMapping(value = "/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student act = studentService.createStudent(student);
        return new ResponseEntity<Student>(act, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {

        try {
            Student student = studentService.findById(id);
            return ResponseEntity.ok(student);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            //return new ResponseEntity<>("Student No encontrado", HttpStatusCode.valueOf(400));
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST + "Student No encontrado ");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student studentNew = new Student();
        try {
            studentNew = studentService.updateStudent(student);
            return ResponseEntity.ok(studentNew);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            //return new ResponseEntity<>("Student No encontrado", HttpStatusCode.valueOf(400));
            return ResponseEntity.status(400).body(studentNew);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student Eliminado");
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            //return new ResponseEntity<>("Student No encontrado", HttpStatusCode.valueOf(400));
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST + "Student No Eliminado");
        }
    }

    @GetMapping("/calcularedad/{id}")
    public int getEstimateAge(@PathVariable Long id) {
        try {
            //return new ResponseEntity<>(studentService.calcularEdad(id), HttpStatusCode.valueOf(200));
            return studentService.calcularEdad(id);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @GetMapping("/listareversa")
    public List<Student> getReverseList() {
        List<Student> studentList = studentService.findAll();
        return studentList.stream().sorted((a1, a2) -> (int)(Period.between(a1.getBirthday(), LocalDate.now()).getYears() -
                Period.between(a2.getBirthday(), LocalDate.now()).getYears())).collect(Collectors.toList());

    }

    //Completar

}
