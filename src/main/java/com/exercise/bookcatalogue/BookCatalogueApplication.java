package com.exercise.bookcatalogue;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "CBA - Book Catalogue - API",
                description = "These set of APIs will let you manage Book Catalogue.",
                version = "1.0.0"
        )
)
public class BookCatalogueApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookCatalogueApplication.class, args);
    }

}
