package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by jt on 8/20/21.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorDao authorDao;

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setTitle("Les fleurs du Mal");
        book.setIsbn("111111111");
        book.setPublisher("Gallimard");
        book.setAuthor(authorDao.getById(3L));

        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        Book deleted = bookDao.getById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setTitle("Les fleurs du Mal");
        book.setIsbn("111111111");
        book.setPublisher("G");
        book.setAuthor(authorDao.getById(3L));

        Book saved = bookDao.saveNewBook(book);

        saved.setPublisher("Gallimard");
        Book updated = bookDao.updateBook(saved);

        assertThat(updated).isNotNull();
        assertThat(updated.getPublisher()).isEqualTo("Gallimard");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setTitle("Les fleurs du Mal");
        book.setIsbn("111111111");
        book.setPublisher("G");
        book.setAuthor(authorDao.getById(3L));
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByTitle() {
        Book book = bookDao.findBookByTitle("Spring in Action, 5th Edition");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {

        Book book = bookDao.getById(1L);

        assertThat(book).isNotNull();

    }
}