package edu.sjsu.cmpe275.lab2.dao;


import edu.sjsu.cmpe275.lab2.entities.Person;
 
public interface PersonDAO {
 
    public Person add(Person person);
    public void  update(Person person);
    public Person  getPerson(Long id);
    public void  delete(Long id);
 
}