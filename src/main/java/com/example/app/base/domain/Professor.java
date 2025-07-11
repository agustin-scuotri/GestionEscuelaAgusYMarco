package com.example.app.base.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "professors")
public class Professor extends Person {
	
	@Column(name = "salary")
	private Double salary;
	
	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Course> courses = new ArrayList<>();

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}
