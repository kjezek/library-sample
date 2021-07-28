package au.kjezek.library.repository;

import au.kjezek.library.domain.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Library Repository
 */
public interface LibraryRepository extends PagingAndSortingRepository<Book, String> {


}
