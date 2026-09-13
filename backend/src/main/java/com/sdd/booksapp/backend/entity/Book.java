package com.sdd.booksapp.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Year;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Publication year is required")
    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(length = 2000)
    private String description;

    // Constructors
    public Book() {}

    public Book(String title, String author, String category, Integer publicationYear, String coverImageUrl, String description) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.publicationYear = publicationYear;
        this.coverImageUrl = coverImageUrl;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
