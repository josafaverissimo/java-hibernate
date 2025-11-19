package com.josafaverissimo.queries;

import jakarta.persistence.NamedQuery;

@NamedQuery(name = "BooksByTitle", query = "from Book where title like :titlePattern order by title")
public class BookQueries { }
