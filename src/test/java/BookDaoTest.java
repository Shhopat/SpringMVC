import dao.BookDao;
import model.Author;
import model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookDaoTest {

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Query<Book> query;

    @InjectMocks
    private BookDao bookDao;

    @BeforeEach
    void setUp() {
        Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void shouldAllBooks() {
        List<Book> list = Arrays.asList(new Book("Звезда", new Author("Иван", "Иванов", "Иванович")));
        Mockito.when(session.createQuery("FROM Book", Book.class)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(list);

        List<Book> result = bookDao.getBooks();

        Assertions.assertEquals(list.size(), result.size());
        Assertions.assertEquals(list.get(0).getName(), result.get(0).getName());

        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).createQuery("FROM Book", Book.class);
        Mockito.verify(query).getResultList();

    }

    @Test
    void shouldSaveBook() {
        Book book = new Book("Звезда", new Author("Иван", "Иванов", "Иванович"));
        bookDao.save(book);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).save(book);

    }

    @Test
    void shouldBookById() {
        int id = 1;
        Book book = new Book("Звезда", new Author("Иван", "Иванов", "Иванович"));
        Mockito.when(session.get(Book.class, id)).thenReturn(book);

        Book result = bookDao.getBookById(id);
        Assertions.assertEquals(book.getName(), result.getName());
        Assertions.assertEquals(book.getAuthor(), result.getAuthor());

        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).get(Book.class, id);

    }

    @Test
    void shouldDeleteById() {
        int id = 1;
        Book book = new Book("Звезда", new Author("Иван", "Иванов", "Иванович"));
        Mockito.when(session.get(Book.class, id)).thenReturn(book);

        bookDao.remove(id);


        Mockito.verify(session).get(Book.class, id);
        Mockito.verify(session).remove(book);


    }

    @Test
    void shouldUpdate() {
        Book book = new Book("Звезда", new Author("Иван", "Иванов", "Иванович"));
        int id = 1;
        Mockito.when(session.get(Book.class, id)).thenReturn(book);

        Book Newbook = new Book("Солнце", new Author("Иван", "Иванов", "Иванович"));

        bookDao.update(id, Newbook);

        Assertions.assertEquals(book.getName(), Newbook.getName());

        Mockito.verify(session).get(Book.class, id);
        Mockito.verify(session).update(book);


    }

}
