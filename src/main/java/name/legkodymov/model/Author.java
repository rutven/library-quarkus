/*
 * Copyright (c) 2021. Sergey Legkodymov (rutven@gmail.com)
 */

package name.legkodymov.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import java.util.Optional;

@Entity
public class Author extends PanacheEntity {
    public String name;

    public static Author findAuthor(Author author) {
        if (author.id != null) {
            return findById(author.id);
        } else {
            Optional<Author> realAuthor = find("name", author.name).firstResultOptional();
            if (realAuthor.isEmpty()) {
                Author newAuthor = new Author();
                newAuthor.name = author.name;
                newAuthor.persist();
                return newAuthor;
            } else {
                return realAuthor.get();
            }
        }
    }
}
