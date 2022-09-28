package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.web.request.BookRequest;
import org.mapstruct.Mapper;
/**
 * Interface for mapping operations for a Book type.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {
    /**
     * Map bookRequest to bookDto.
     * @param bookRequest – must not be null.
     * @return BookDto object.
     */
    BookDto bookRequestToBookDto(BookRequest bookRequest);

    /**
     * Map bookDto to bookRequest.
     * @param bookDto – must not be null.
     * @return BookRequest object.
     */
    BookRequest bookDtoToBookRequest(BookDto bookDto);

    /**
     * Map bookEntity to bookDto.
     * @param book – object of Book. Must not be null.
     * @return BookDto object.
     */
    BookDto bookEntityToBookDto(Book book);

    /**
     * Map bookDto to bookEntity.
     * @param bookDto – must not be null.
     * @return BookEntity object.
     */
    Book bookDtoToBookEntity(BookDto bookDto);
}
