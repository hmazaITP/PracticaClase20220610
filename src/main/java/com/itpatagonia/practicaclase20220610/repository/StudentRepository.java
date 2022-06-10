package com.itpatagonia.practicaclase20220610.repository;

import com.itpatagonia.practicaclase20220610.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
