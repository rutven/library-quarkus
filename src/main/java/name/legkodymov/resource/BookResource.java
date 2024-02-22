/*
 * Copyright (c) 2021. Sergey Legkodymov (rutven@gmail.com)
 */

package name.legkodymov.resource;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Sort;
import name.legkodymov.model.Author;
import name.legkodymov.model.Book;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @GET
    public List<Book> list() {
        return Book.listAll(Sort.by("title"));
    }

    @GET
    @Path("/{id}")
    public Book get(@PathParam("id") Long id) {
        Book book = Book.findById(id);
        Log.info("findById - " + id + " book - " + book);
        return book;
    }

    @POST
    @Transactional
    public Book create(Book book) {
        book.author = Author.findAuthor(book.author);
        book.persist();
        return book;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Book update(@PathParam("id") Long id, Book book) {
        Book entity = Book.findById(id);
        if (entity == null) {
            throw new NotFoundException("Book with id = " + id + " not found");
        }
        entity.author = book.author;
        entity.title = book.title;
        entity.issueDate = book.issueDate;
        entity.size = book.size;

        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Book entity = Book.findById(id);
        if (entity == null) {
            throw new NotFoundException("Book with id = " + id + " not found");
        }

        entity.delete();
    }

    @GET
    @Path("/title/{title}")
    public List<Book> getBooksByTitle(@PathParam("title") String title) {
        return Book.find("title", title).list();
    }

    @GET
    @Path("/author/{author}")
    public List<Book> getBooksByAuthor(@PathParam("author") String author) {
        return Book.find("author", Sort.by("title"), author).list();
    }

}
