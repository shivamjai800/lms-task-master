package com.project.lms.Entities;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book", uniqueConstraints = { @UniqueConstraint(columnNames = { "title", "author" }) })
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "it should not be blank ")
	@Size(min = 1, max = 35, message = "About Me must be between 1 and 35 characters")
	@Column(name="title")
	private String title;

	@NotBlank(message = "it should not be blank ")
	@Size(min = 1, max = 35, message = "About Me must be between 1 and 35 characters")
	@Column(name="author")
	private String author;

	@Column(name="quantity")
	private int quantity;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "bookParentId")
	private Set<UnitBook> unitBooks;
	
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH},fetch = FetchType.EAGER)
	@JoinColumn(name = "bookId")
	private List<BookRecord> request;
	
	public Book() {
		super();
		this.unitBooks = new HashSet<UnitBook>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Set<UnitBook> getUnitBooks() {
		return unitBooks;
	}

	public void setUnitBooks(Set<UnitBook> unitBooks) {
		this.unitBooks = unitBooks;
	}
	
	public List<BookRecord> getRequest() {
		return request;
	}

	public void setRequest(List<BookRecord> request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", quantity=" + quantity + ", unitBooks="
				+ unitBooks + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Book book = (Book) o;
		return getId() == book.getId() && getQuantity() == book.getQuantity() && getTitle().equals(book.getTitle()) && getAuthor().equals(book.getAuthor());
	}

}
