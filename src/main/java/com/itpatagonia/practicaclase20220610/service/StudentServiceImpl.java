package com.itpatagonia.practicaclase20220610.service;

import com.itpatagonia.practicaclase20220610.exception.NoEntityException;
import com.itpatagonia.practicaclase20220610.model.Student;
import com.itpatagonia.practicaclase20220610.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) throws NoEntityException {
        return studentRepository.findById(id).orElseThrow(() -> new NoEntityException("No existe Student con " + id));
    }

    @Transactional
    @Override
    public Student updateStudent(Student student) throws NoEntityException {
        Student studentOld = studentRepository.findById(student.getId()).orElseThrow(
                () -> new NoEntityException("No existe Student con " + student.getId()));
        studentOld.setSurname(student.getSurname());
        studentOld.setName(student.getName());
        studentOld.setBirthday(student.getBirthday());
        return studentRepository.save(studentOld);
    }

    @Transactional
    @Override
    public void deleteStudent(Long id) throws NoEntityException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NoEntityException("No existe Student con " + id));
        studentRepository.delete(student);
    }

    public int calcularEdad(Long id) throws NoEntityException {
        Optional<Student> student = this.studentRepository.findById(id);
        return Period.between(student.orElseThrow(() -> new NoEntityException("No existe Student con " + id)).getBirthday(),
                LocalDate.now()).getYears();
    }

    //Completar


    //Inventar un ejemplo

}
