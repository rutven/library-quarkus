package name.legkodymov.resource;

import name.legkodymov.model.Book;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @GET
    public List<Book> list() {
        return Book.listAll();
    }

    @GET
    @Path("/{id}")
    public Book get(@PathParam("id") Long id) {
        return Book.findById(id);
    }

    @POST
    @Transactional
    public Response create(Book book) {
        book.persist();
        return Response.created(URI.create("/books/" + book.id)).build();
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
    @Path("/{title}")
    public List<Book> getBooksByTitle(@PathParam("title") String title) {
        return Book.find("title", title).list();
    }

    @GET
    @Path("/{author}")
    public List<Book> getBooksByAuthor(@PathParam("author") String author) {
        return Book.find("author", author).list();
    }


}
