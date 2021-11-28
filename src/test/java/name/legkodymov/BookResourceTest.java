/*
 * Copyright (c) 2021. Sergey Legkodymov (rutven@gmail.com)
 */

package name.legkodymov;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import name.legkodymov.model.Author;
import name.legkodymov.model.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class BookResourceTest {

    final String bookBasePath = "/books";

    @Test
    public void testCreateBook() {
        Author asimov = new Author();
        asimov.name = "Isaac Asimov";

        Book book = new Book();
        book.title = "Foundation";
        book.author = asimov;
        book.size = 255;
        book.issueDate = LocalDate.of(1942, 5, 1);

        Book created = given().basePath(bookBasePath).contentType(ContentType.JSON).body(book)
                .when().post()
                .then().statusCode(200).extract().as(Book.class);

        Log.info("Book created - " + created.toString());

        assertBooks(book, created);
    }

    private void assertBooks(Book book, Book created) {
        assertEquals(book.size, created.size);
        assertEquals(book.title, created.title);
        assertEquals(book.issueDate, created.issueDate);
        assertEquals(book.author.name, created.author.name);
    }

    @Test
    public void testUpdateBook() {
        Author heinlein = new Author();
        heinlein.name = "Robert A. Heinlein";

        Book book = new Book();
        book.author = heinlein;
        book.title = "Starship Troopers";
        book.size = 263;
        book.issueDate = LocalDate.of(1959, 11, 01);

        Book created = given().basePath(bookBasePath).contentType(ContentType.JSON).body(book)
                .when().post()
                .then().statusCode(200).extract().as(Book.class);

        Log.info("Book created - " + created.toString());

        created.title = "Friday";
        created.issueDate = LocalDate.of(1982, 04, 01);

        Book updated = given().basePath(bookBasePath).contentType(ContentType.JSON).body(created)
                .when().put("/" + created.id)
                .then().statusCode(200).extract().as(Book.class);

        Log.info("Book updated = " + updated.toString());

        assertBooks(created, updated);
    }

}
