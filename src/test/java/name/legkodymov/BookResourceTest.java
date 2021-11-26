package name.legkodymov;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import name.legkodymov.model.Author;
import name.legkodymov.model.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class BookResourceTest {

    final String bookBasePath = "/books";

    @Test
    public void testCreateBook() throws Exception {
        Author asimov = new Author();
        asimov.name = "Isaac Asimov";

        Book book = new Book();
        book.title = "Foundation";
        book.author = asimov;
        book.size = 255;
        book.issueDate = LocalDate.of(1942, 5, 1);

        given().basePath(bookBasePath).contentType(ContentType.JSON).body(book)
                .when().post()
                .then().statusCode(201);

        Log.info("Book created - " + book);

        given().basePath(bookBasePath).contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200).body("size()", is(1));

        Log.info("Book stored in DB");

        Book newBook = given().basePath(bookBasePath)
                .when().get("/2")
                .then().statusCode(200)
                .extract().as(Book.class);

        Log.info("Book retrieved - " + newBook.toString());
    }
}
