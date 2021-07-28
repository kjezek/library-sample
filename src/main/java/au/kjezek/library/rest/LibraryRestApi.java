package au.kjezek.library.rest;

import au.kjezek.library.domain.Book;
import au.kjezek.library.repository.LibraryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Rest interface for the Library
 */
@RestController("/book/")
public class LibraryRestApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** Spring dependency. */
    private final LibraryRepository repository;

    /**
     * Autowired dependencies.
     * @param repository repository
     */
    public LibraryRestApi(final LibraryRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody final Book book) {
        logger.debug("Start: POST book: {}", book);
        Book newBook = repository.save(book);
        logger.debug("End: POST book: {}->{}", book, newBook);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") final String id) {
        logger.debug("Start: DELETE bookID: {}", id);
        repository.deleteById(id);
        logger.debug("End: DELETE bookID: {}", id);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") final String id) {
        logger.debug("Start: GET bookID: {}", id);
        Optional<Book> book = repository.findById(id);
        logger.debug("End: GET bookID: {}->{}", id, book);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(name = "/pages/", path = "pageable")
    public Page<Book> findPaginated(final Pageable pageable) {
        logger.debug("Start: GET pages {}", pageable);
        Page<Book> resultPage = repository.findAll(pageable);
        logger.debug("End: GET pages {}, elements: {}", pageable, resultPage.getTotalElements());
        return resultPage;
    }

}
