package com.dev4sep.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class GraphQLServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphQLServerApplication.class, args);
	}

	@Controller
	public class BookController {
		@QueryMapping
		public Book bookById(@Argument String id) {
			return Book.getById(id);
		}

		@SchemaMapping
		public Author author(Book book) {
			return Author.getById(book.authorId());
		}
	}

	public record Book (String id, String name, int pageCount, String authorId) {

		private static List<Book> books = Arrays.asList(
				new Book("book-1", "Java", 416, "author-1"),
				new Book("book-2", "C", 208, "author-2"),
				new Book("book-3", "Python", 436, "author-3")
		);

		public static Book getById(String id) {
			return books.stream()
					.filter(book -> book.id().equals(id))
					.findFirst()
					.orElse(null);
		}
	}

	public record Author (String id, String firstName, String lastName) {

		private static List<Author> authors = Arrays.asList(
				new Author("author-1", "Lay", "1"),
				new Author("author-2", "Lay", "2"),
				new Author("author-3", "Lay", "3")
		);

		public static Author getById(String id) {
			return authors.stream()
					.filter(author -> author.id().equals(id))
					.findFirst()
					.orElse(null);
		}
	}
}
