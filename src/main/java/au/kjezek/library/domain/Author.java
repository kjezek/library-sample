package au.kjezek.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Author of a book.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document
public class Author {

    @Indexed
    private String name;
    @Indexed
    private String surename;

    public Author(String name, String surename) {
        this.name = name;
        this.surename = surename;
    }

}
