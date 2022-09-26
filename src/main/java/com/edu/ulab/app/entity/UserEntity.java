package com.edu.ulab.app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Person")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String title;
    private int age;
}
