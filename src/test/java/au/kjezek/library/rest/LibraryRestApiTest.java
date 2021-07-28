package au.kjezek.library.rest;

import au.kjezek.library.domain.Author;
import au.kjezek.library.domain.Book;
import au.kjezek.library.repository.LibraryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryRestApiTest {

    /** Test fixture. */
    @Autowired
    private LibraryRestApi api;
    @Autowired
    private LibraryRepository repo;

    private final Book bookRomeo = new Book(null, "Romeo and Juliet", new Author("William", "Shakespeare"));
    private final Book book1984 = new Book(null, "1984", new Author("George", "Orwell"));

    @Test
    public void testCreateBook() {
        ResponseEntity<Book> result = api.createBook(bookRomeo);

        // return is ok
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertBook(result.getBody());

        // data in the database
        Optional<Book> resultDb = repo.findById(result.getBody().getId());
        assertTrue(resultDb.isPresent());
        assertBook(resultDb.get());
    }

    @Test
    public void testUpdateBook() {
        ResponseEntity<Book> result = api.createBook(bookRomeo);
        assertNotNull(result.getBody());
        // update
        Book update = new Book(result.getBody().getId(), book1984.getName(), book1984.getAuthor());
        ResponseEntity<Book> resultUpdated = api.createBook(update);

        // data in the database
        assertNotNull(resultUpdated.getBody());
        Optional<Book> resultDb = repo.findById(resultUpdated.getBody().getId());
        assertTrue(resultDb.isPresent());
        assertEquals(book1984.getName(), resultDb.get().getName());
        assertEquals(book1984.getAuthor().getName(), resultDb.get().getAuthor().getName());
        assertEquals(book1984.getAuthor().getSurename(),  resultDb.get().getAuthor().getSurename());

    }

    @Test
    public void testDeleteBook() {
        Book persisted = repo.save(bookRomeo);
        api.deleteBook(persisted.getId());
        // the book is gone
        assertFalse(repo.findById(persisted.getId()).isPresent());
    }

    @Test
    public void testPagination() {
        // init data
        for (int i = 1; i < 10; i++) {
            repo.save(new Book(null, bookRomeo.getName(), bookRomeo.getAuthor()));
            repo.save(new Book(null, book1984.getName(), book1984.getAuthor()));
        }

        Pageable pageRequest = PageRequest.of(0, 5);
        Page<Book> p = api.findPaginatedAuthorName(bookRomeo.getAuthor().getName(), bookRomeo.getAuthor().getSurename(), pageRequest);

        assertEquals(5, p.getSize());
        assertEquals(2, p.getTotalPages());
        assertEquals(10, p.getTotalElements());

        for (Book b : p.getContent()) {
            assertBook(b);
        }
    }

    @Test
    public void testGetBook() {
        Book persisted = repo.save(bookRomeo);

        ResponseEntity<Book> result  = api.getBook(persisted.getId());
        // return is ok
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertBook(result.getBody());
    }

    private void assertBook(Book book) {
        assertEquals("Romeo and Juliet", book.getName());
        assertEquals("William", book.getAuthor().getName());
        assertEquals("Shakespeare", book.getAuthor().getSurename());
    }
}
