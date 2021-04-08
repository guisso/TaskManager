/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.guisso.jakartaee8.taskmanager.person;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Local
public interface PersonServiceBeanLocal {

    public List<Person> findAll();

    public Person findPersonById(Long taskId);
    
}
