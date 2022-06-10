package com.itpatagonia.practicaclase20220610.service;

import com.itpatagonia.practicaclase20220610.exception.NoEntityException;
import com.itpatagonia.practicaclase20220610.model.Student;

import java.util.List;

public interface StudentService {
    public Student createStudent(Student student);

    public List<Student> findAll();

    public Student findById(Long id) throws NoEntityException;

    public Student updateStudent(Student student) throws NoEntityException;

    public void deleteStudent(Long id) throws NoEntityException;

}
