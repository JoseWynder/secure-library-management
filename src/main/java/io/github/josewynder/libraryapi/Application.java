package io.github.josewynder.libraryapi;

import io.github.josewynder.libraryapi.model.Author;
import io.github.josewynder.libraryapi.repository.AuthorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext
				context = SpringApplication.run(Application.class, args);
		AuthorRepository authorRepository =
				context.getBean(AuthorRepository.class);

		exampleSaveRecord(authorRepository);
	}

	public static void exampleSaveRecord(AuthorRepository authorRepository) {
		Author author = new Author();
		author.setName("Jose");
		author.setNationality("Brazilian");
		author.setBirthDate(LocalDate.of(1950, 1, 31));

		Author savedAuthor = authorRepository.save(author);
		System.out.println("Saved Author: " + savedAuthor);
	}
}
