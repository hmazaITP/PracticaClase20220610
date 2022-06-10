package com.itpatagonia.practicaclase20220610.service;

import com.itpatagonia.practicaclase20220610.exception.NoEntityException;
import com.itpatagonia.practicaclase20220610.model.Student;
import com.itpatagonia.practicaclase20220610.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
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

    public int calcularEdadPromedio(){
        List<Student> studentList = studentRepository.findAll();
        return (int)studentList.stream().mapToInt(s -> Period.between(s.getBirthday(),LocalDate.now()).getYears()).average().orElseThrow();
    }

    public int sumarEdades(){
        List<Student> studentList = studentRepository.findAll();
        return (int)studentList.stream().mapToInt(s -> Period.between(s.getBirthday(),LocalDate.now()).getYears()).sum();
    }

    public int edadMasGrande(){
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream().mapToInt(s -> Period.between(s.getBirthday(),LocalDate.now()).getYears()).max().orElse(0);

    }
    public int edadMasChica(){
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream().mapToInt(s -> Period.between(s.getBirthday(),LocalDate.now()).getYears()).min().orElse(0);
    }

    public String nombresEstudiantes(){
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream().map(Student::getName).collect(Collectors.joining(" - "));
    }

    public List<Student> listarMayoresConC(){
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream().filter( s -> Period.between(s.getBirthday(),LocalDate.now()).getYears()>= 18)
                .filter(s -> s.getName().charAt(0)== 'C').collect(Collectors.toList());
    }

    public List<Student> listarMenoresConS(){
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream().filter( s -> Period.between(s.getBirthday(),LocalDate.now()).getYears()< 18)
                .filter(s -> s.getName().charAt(0)== 'S').collect(Collectors.toList());
    }

    public List<Student> listar3ConMasEdad(){
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream().sorted((s1,s2) -> Integer.compare(
                Period.between(s2.getBirthday(),LocalDate.now()).getYears(),
                Period.between(s1.getBirthday(),LocalDate.now()).getYears()

        )).limit(3).collect(Collectors.toList());
               //si queremos devolver solo los nombres  .map(s -> s.getName()).collect(Collectors.joining(", "));
    }

    //Inventar un ejemplo

}
