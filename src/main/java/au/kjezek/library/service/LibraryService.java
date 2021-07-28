package au.kjezek.library.service;

import au.kjezek.library.domain.Book;
import au.kjezek.library.repository.LibraryRepository;
import org.springframework.stereotype.Service;

/**
 * Library service layer
 */
@Service
public class LibraryService {

    /** Spring dependency. */
    private LibraryRepository repository;

    /**
     * Autowired dependencies.
     * @param repository repository
     */
    public LibraryService(LibraryRepository repository) {
        this.repository = repository;
    }

}
