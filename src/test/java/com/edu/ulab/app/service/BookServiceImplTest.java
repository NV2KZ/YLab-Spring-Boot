package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тестирование функционала {@link BookServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing book functionality.")
public class BookServiceImplTest {
    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @Test
    @DisplayName("Создание книги. Должно пройти успешно.")
    void createBook_Test() {
        //given
        UserEntity user = new UserEntity();
        user.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setUserId(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setUserId(user.getId());

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setUserId(user.getId());

        //when

        when(bookMapper.bookDtoToBookEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookEntityToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.createBook(bookDto);
        assertEquals(1L, bookDtoResult.getId());
        assertEquals(1000, bookDtoResult.getPageCount());
        assertEquals("test title", bookDtoResult.getTitle());
        assertEquals("test author", bookDtoResult.getAuthor());
        assertEquals(user.getId(), bookDtoResult.getUserId());
    }

    @Test
    @DisplayName("Обновление книги. Должно пройти успешно.")
    void updateBook_Test() {
        //given
        UserEntity user = new UserEntity();
        user.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setUserId(user.getId());
        bookDto.setAuthor("test update author");
        bookDto.setTitle("test update title");
        bookDto.setPageCount(1000);

        Book book = new Book();
        book.setId(1L);
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setUserId(user.getId());

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setPageCount(1000);
        updatedBook.setTitle("test update title");
        updatedBook.setAuthor("test update author");
        updatedBook.setUserId(user.getId());

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(user.getId());
        result.setAuthor("test update author");
        result.setTitle("test update title");
        result.setPageCount(1000);

        //when
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.bookDtoToBookEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(updatedBook);
        when(bookMapper.bookEntityToBookDto(updatedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.updateBook(bookDto);
        assertEquals(1L, bookDtoResult.getId());
        assertEquals(1000, bookDtoResult.getPageCount());
        assertEquals("test update title", bookDtoResult.getTitle());
        assertEquals("test update author", bookDtoResult.getAuthor());
        assertEquals(user.getId(), bookDtoResult.getUserId());
    }

    @Test
    @DisplayName("Получение книги. Должно пройти успешно.")
    void getBookById_Test() {

        //given

        UserEntity user = new UserEntity();
        user.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setUserId(user.getId());

        BookDto result = new BookDto();
        result.setId(1L);
        result.setPageCount(1000);
        result.setTitle("test title");
        result.setAuthor("test author");
        result.setUserId(user.getId());

        //when

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookMapper.bookEntityToBookDto(book)).thenReturn(result);

        //then

        BookDto bookDtoResult = bookService.getBookById(book.getId());
        assertEquals(1L, bookDtoResult.getId());
        assertEquals(1000, bookDtoResult.getPageCount());
        assertEquals("test title", bookDtoResult.getTitle());
        assertEquals("test author", bookDtoResult.getAuthor());
        assertEquals(user.getId(), bookDtoResult.getUserId());
    }

    @Test
    @DisplayName("Получение списка книг по id пользователя. Должно пройти успешно.")
    void getBooksByUserId_Test() {

        //given

        UserEntity user = new UserEntity();
        user.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setUserId(user.getId());

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setPageCount(1000);
        bookDto.setTitle("test title");
        bookDto.setAuthor("test author");
        bookDto.setUserId(user.getId());

        List<Book> bookEntityList = List.of(book);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setPageCount(1000);
        result.setTitle("test title");
        result.setAuthor("test author");
        result.setUserId(user.getId());

        //when

        when(bookRepository.findAllBooksByUserId(user.getId())).thenReturn(bookEntityList);
        when(bookMapper.bookEntityToBookDto(book)).thenReturn(result);

        //then

        List<BookDto> bookDtoResult = bookService.getBooksByUserId(user.getId());
        assertEquals(user.getId(), bookDtoResult.get(0).getUserId());
    }

    @Test
    @DisplayName("Удаление книги. Должно пройти успешно.")
    void deleteBook_Test() {

        Long bookId = 1L;

        bookRepository.deleteById(bookId);

        verify(bookRepository, times(1)).deleteById(eq(bookId));
    }
}
