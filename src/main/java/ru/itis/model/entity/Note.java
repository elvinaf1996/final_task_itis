package ru.itis.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 2000)
    private String text;

    private String userName;
}