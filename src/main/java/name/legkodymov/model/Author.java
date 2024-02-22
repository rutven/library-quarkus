/*
 * Copyright (c) 2021. Sergey Legkodymov (rutven@gmail.com)
 */

package name.legkodymov.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Entity;
import java.util.Optional;

@Entity
public class Author extends PanacheEntity {
    public String name;

    public static Author findAuthor(Author author) {
        if (author.id != null) {
            return findById(author.id);
        } else {
            Optional<Author> realAuthor = find("name", author.name).firstResultOptional();
            return realAuthor.orElse(createAuthor(author.name));
        }
    }

    private static Author createAuthor(String name) {
        Author newAuthor = new Author();
        newAuthor.name = name;
        newAuthor.persist();
        return newAuthor;
    }
}
