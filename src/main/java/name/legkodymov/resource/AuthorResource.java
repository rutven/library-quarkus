/*
 * Copyright (c) 2021. Sergey Legkodymov (rutven@gmail.com)
 */

package name.legkodymov.resource;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Sort;
import name.legkodymov.model.Author;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @GET
    public List<Author> list() {
        Log.info("get all authors");
        return Author.listAll(Sort.by("name"));
    }

    @GET
    @Path("/{id}")
    public Author get(@PathParam("id") Long id) {
        Optional<Author> optional = Author.findByIdOptional(id);
        optional.ifPresentOrElse(author -> Log.info("get author - " + author.toString()),
                () -> Log.warn("author with id = " + id + " not found!"));
        return optional.orElseThrow(() -> new NotFoundException("Author with id = " + id + " not found!"));
    }

    @POST
    @Transactional
    public Author create(Author author) {
        author.persist();
        Log.info("author created: name = " + author.name + ", id = " + author.id);
        return author;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Author update(@PathParam("id") Long id, Author author) {
        Author realAuthor = Author.findById(id);
        if (realAuthor == null) {
            Log.warn("Author with id = " + id + " not found");
            throw new NotFoundException("Author with id = " + id + " not found");
        }

        realAuthor.name = author.name;
        realAuthor.persist();
        Log.info("Author updated - id = " + id);
        return realAuthor;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Optional<Author> optional = Author.findByIdOptional(id);
        optional.ifPresentOrElse(PanacheEntityBase::delete,
                () -> {
                    Log.warn("Author with id = " + id + " not found");
                    throw new NotFoundException("Author with id = " + id + " not found");
                });
    }

}
