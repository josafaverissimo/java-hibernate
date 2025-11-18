package com.josafaverissimo.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {
  @Id
  String id;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  Person person;

  @ManyToMany(fetch = FetchType.LAZY)
  Set<Book> books = new HashSet<>();

  public Author() {}

  public Author(String id, Person Person) {
    this.id = id;
    this.person = Person;
  }

  public Set<Book> getBooks() {
    return books;
  }
}
