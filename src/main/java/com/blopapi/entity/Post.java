package com.blopapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private  String content;

    // One Post can have multiple Comments
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

}

//The provided code represents a basic implementation of a blog API using Spring Boot and JPA (Java Persistence API). Here's a breakdown of the code:
//
//        Entity Class: Post
//
//        This class represents a blog post and is annotated with @Entity to indicate that it is a JPA entity.
//        The @Table annotation specifies the table name and defines a unique constraint on the title column.
//        The class has several fields representing the post's properties, such as id, title, description, and content.
//        The @Id annotation marks the id field as the primary key.
//        The @GeneratedValue annotation specifies that the id value will be generated automatically.
//        The @Column annotation is used to define the properties of individual columns in the database table.
