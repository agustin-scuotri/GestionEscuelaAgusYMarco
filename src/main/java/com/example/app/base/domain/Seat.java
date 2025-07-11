package com.example.app.base.domain;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat extends AbstractEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Column(name = "exam_date", nullable = false)
	private LocalDate year;
	
	@Column(name = "mark")
	private Double mark;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	public LocalDate getYear() {
		return year;
	}

	public void setYear(LocalDate year) {
		this.year = year;
	}

	public Double getMark() {
		return mark;
	}

	public void setMark(Double mark) {
		this.mark = mark;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
