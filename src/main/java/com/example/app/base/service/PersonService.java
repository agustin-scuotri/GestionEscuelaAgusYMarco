package com.example.app.base.service;

import com.example.app.base.domain.Person;
import com.example.app.base.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository repo;

    @Autowired
    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public Person save(Person person) {
        return repo.save(person);
    }

    public List<Person> findAll() {
        return repo.findAll();
    }

    public Optional<Person> findById(Long id) {
        return repo.findById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    // Búsqueda por apellido
    public List<Person> findByLastName(String lastName) {
        return repo.findByLastName(lastName);
    }
}
