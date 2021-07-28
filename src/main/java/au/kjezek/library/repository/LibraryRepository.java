package au.kjezek.library.repository;

import au.kjezek.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Library Repository
 */
public interface LibraryRepository extends PagingAndSortingRepository<Book, String> {


    /**
     * Find a book by its name.
     * @param name the name
     * @param pageable pagination
     * @return a page
     */
    Page<Book> findByName(String name, Pageable pageable);

    /**
     * Find a book by its author.
     * @param name the author's name
     * @param surename the author's  surename
     * @param pageable pagination
     * @return a page
     */
    @Query("{'author.name': ?0, 'author.surename': ?1}")
    Page<Book> findByAuthor(String name, String surename, Pageable pageable);
}
