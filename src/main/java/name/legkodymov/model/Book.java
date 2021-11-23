package name.legkodymov.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Book extends PanacheEntity {
    public String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate issueDate;
    public String author;
    public Integer size;

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", issueDate=" + issueDate +
                ", author='" + author + '\'' +
                ", size=" + size +
                ", id=" + id +
                '}';
    }
}
