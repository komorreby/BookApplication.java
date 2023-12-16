package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.demo")
public class BookApp implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args)throws Exception
    {
        List<Book> books = Arrays.asList(
                new Book("Little Life","Hanya Yanagihara"),
                new Book("Normal People","Sally Rooney")
        );

        books.forEach(book -> System.out.println("Title: "+ book.getTitle() + ", Author: "+ book.getAuthor()));
        bookRepository.saveAll(books);
        saveToFile(books,"books.txt");
    }

    private void saveToFile(List<Book> books,String fileName) throws IOException
    {
        try(FileWriter writer = new FileWriter(fileName))
        {
            for(Book book: books)
            {
                writer.write("Title: " +book.getTitle() + ", Author: "+ book.getAuthor()+"\n");
            }
        }
    }
    @Configuration
    public class JacksonConfig {

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(BookApp.class, args);
    }
}
