package com.josafaverissimo.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "people")
public class Person {
  @Id String id;

  String name;

  @OneToOne(
      mappedBy = Author_.PERSON,
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH},
      orphanRemoval = true)
  Author author;

  public Person() {}

  public Person(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
