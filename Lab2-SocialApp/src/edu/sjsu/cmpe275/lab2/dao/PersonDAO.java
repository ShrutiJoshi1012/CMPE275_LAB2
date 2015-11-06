package edu.sjsu.cmpe275.lab2.dao;


import java.util.List;

import edu.sjsu.cmpe275.lab2.entities.Person;
 
public interface PersonDAO {
 
    public Person addPerson(Person person);
    public Person  updatePerson(Person person);
    public Person  getPerson(Long id);
    public Person  deletePerson(Long id);
    public long[]  getFriends(Long id);
 
}