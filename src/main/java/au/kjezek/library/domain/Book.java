package au.kjezek.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A book from a library.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document
public class Book {

    @Id
    private String id;
    @Indexed
    private String name;
    private Author author;

    public Book(String id, String name, Author author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

}
