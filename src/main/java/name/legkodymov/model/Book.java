/*
 * Copyright (c) 2021. Sergey Legkodymov (rutven@gmail.com)
 */

package name.legkodymov.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Book extends PanacheEntity {
    public String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate issueDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    public Author author;
    public Integer size;

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", issueDate=" + issueDate +
                ", author='" + author.name + '\'' +
                ", size=" + size +
                ", id=" + id +
                '}';
    }
}
