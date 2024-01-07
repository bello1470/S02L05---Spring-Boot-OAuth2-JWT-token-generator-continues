package org.bellotech.SpringRestdemo.model;

import org.hibernate.annotations.ManyToAny;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
   

    private String name;
 private String description;
    @ManyToOne
    @JoinColumn(name = "Account_id", referencedColumnName = "id", nullable = false)
    private Account account;
    
}
