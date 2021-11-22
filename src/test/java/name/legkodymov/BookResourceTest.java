package name.legkodymov;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import name.legkodymov.model.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class BookResourceTest {

    final String bookBasePath = "/books";

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book();
        book.title = "Foundation";
        book.author = "Isaac Asimov";
        book.size = 255;
        book.issueDate = LocalDate.of(1942, 5, 1);

        given().basePath(bookBasePath).contentType(ContentType.JSON).body(book)
                .when().post()
                .then().statusCode(201);
    }
}
