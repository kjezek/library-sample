package au.kjezek.library.rest;

import au.kjezek.library.domain.Book;
import au.kjezek.library.repository.LibraryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Rest interface for the Library
 */
@RestController("/books/")
@Tag(name = "books", description = "Library API to manipulate with books. ")
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

    @Operation(summary = "Create or Update a book",
            description = "Input is a book, which is created. If the book with the given ID already exists, it is updated.",
            tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully created or updated",
                    content = @Content(schema = @Schema(implementation = Book.class))) })
    @PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody final Book book) {
        logger.debug("Start: POST book: {}", book);
        Book newBook = repository.save(book);
        logger.debug("End: POST book: {}->{}", book, newBook);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a book",
            description = "Input is a book ID and the corresponding book is deleted. If the book does not exist, nothing happens. ",
            tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete successfully executed, even when the resource did not exist")})
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") final String id) {
        logger.debug("Start: DELETE bookID: {}", id);
        repository.deleteById(id);
        logger.debug("End: DELETE bookID: {}", id);
    }

    @Operation(summary = "Get a book",
            description = "Input is a book ID and the detail of the book is returned. ",
            tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "if the book exists",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "if the book does NOT exists")})
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") final String id) {
        logger.debug("Start: GET bookID: {}", id);
        Optional<Book> book = repository.findById(id);
        logger.debug("End: GET bookID: {}->{}", id, book);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Paginated list with books",
            description = "Input is a pagination query and the list of books in the page is returned",
            tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully created or updated",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Book.class)))) })
    @GetMapping("/pages/")
    public Page<Book> findPaginated(final Pageable pageable) {
        logger.debug("Start: GET pages {}", pageable);
        Page<Book> resultPage = repository.findAll(pageable);
        logger.debug("End: GET pages {}, elements: {}", pageable, resultPage.getTotalElements());
        return resultPage;
    }

}
