package com.example.app.base.repository;

import com.example.app.base.domain.Professor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Professor> findBySalaryGreaterThan(Double minSalary);
}
