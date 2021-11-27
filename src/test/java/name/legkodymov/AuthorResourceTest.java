/*
 * Copyright (c) 2021. Sergey Legkodymov (rutven@gmail.com)
 */

package name.legkodymov;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import name.legkodymov.model.Author;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class AuthorResourceTest {

    final String authorBasePath = "/authors";

    @Test
    public void testCreateAuthor() {
        Author author = new Author();
        author.name = "Arthur C. Clarke";

        given().basePath(authorBasePath).contentType(ContentType.JSON).body(author)
                .when().post()
                .then().statusCode(200);

        Log.info("Author created - " + author.name);
    }

    @Test
    public void testGetAuthor() {
        Author author = new Author();
        author.name = "Robert A. Heinlein";

        Author created = given().basePath(authorBasePath).contentType(ContentType.JSON).body(author)
                .when().post()
                .then().statusCode(200).extract().as(Author.class);

        assertNotNull(created.id, "Author id is null!");

        Author received = given().basePath(authorBasePath).contentType(ContentType.JSON)
                .when().get("/" + created.id)
                .then().statusCode(200).extract().as(Author.class);
        assertEquals(author.name, received.name);
    }

    @Test
    public void testAuthorDelete() {
        Author author = new Author();
        author.name = "Ray Bradbury";

        Author created = given().basePath(authorBasePath).contentType(ContentType.JSON).body(author)
                .when().post()
                .then().statusCode(200).extract().as(Author.class);

        assertNotNull(created.id, "Author id is null");

        given().basePath(authorBasePath).contentType(ContentType.JSON)
                .when().delete("/" + created.id)
                .then().statusCode(204);

        given().basePath(authorBasePath).contentType(ContentType.JSON)
                .when().get("/" + created.id)
                .then().statusCode(404);
    }

    @Test
    public void testUpdateAuthor() {
        Author author = new Author();
        author.name = "Ray Bradbury";

        Author created = given().basePath(authorBasePath).contentType(ContentType.JSON).body(author)
                .when().post()
                .then().statusCode(200).extract().as(Author.class);

        assertNotNull(created.id, "Author id is null");

        created.name = "Mark Twain";
        Author updated = given().basePath(authorBasePath).contentType(ContentType.JSON).body(created)
                .when().put("/" + created.id)
                .then().statusCode(200).extract().as(Author.class);

        assertEquals(created.name, updated.name);
    }

    //TODO add delete test with books
}
