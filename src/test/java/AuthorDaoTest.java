import dao.AuthorDao;
import model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Query<Author> query;
    @InjectMocks
    private AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void shouldAllAuthors() {
        List<Author> authors = Arrays.asList(
                new Author("Ибрагим", "Бахарчиев", "Саидович"));

        Mockito.when(session.createQuery("FROM Author", Author.class)).thenReturn(query);

        Mockito.when(query.getResultList()).thenReturn(authors);

        List<Author> result = authorDao.getAllAuthors();
        assertEquals(1, result.size());
        assertEquals("Ибрагим", result.get(0).getName());
    }

    @Test
    void shouldNewAuthor() {
        Author author = new Author("Иван", "Иванов", "Иванович");

        authorDao.createAuthor(author);

        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).save(author);

    }

    @Test
    void shouldAuthorById() {
        int id = 1;
        Author author = new Author("Иван", "Иванов", "Иванович");

        when(session.get(Author.class, id)).thenReturn(author);
        Author authorResult = authorDao.getByAuthor(id);

        verify(sessionFactory).getCurrentSession();
        verify(session).get(Author.class, id);

        assertEquals(author.getName(), authorResult.getName());
        assertNotNull(authorResult);


    }

    @Test
    void shouldReturnNullIfAuthorNotFound() {
        int id = 1;

        when(session.get(Author.class, id)).thenReturn(null);

        Author author = authorDao.getByAuthor(id);

        verify(sessionFactory).getCurrentSession();
        verify(session).get(Author.class, id);

        assertNull(author);

    }

    @Test
    void shouldUpdateAuthor() {
        int id = 1;
        Author author = new Author("Иван", "Иванов", "Иванович"); // Автор в БД
        Author newAuthor = new Author("Ибрагим", "Бахарчиев", "Иванович"); // Новые данные

        // Настраиваем моки
        when(session.get(Author.class, id)).thenReturn(author);

        // Вызываем метод
        authorDao.update(id, newAuthor);

        // Проверяем, что session.get() был вызван
        verify(session).get(Author.class, id);

        // Проверяем, что поля обновились
        assertEquals("Ибрагим", author.getName());
        assertEquals("Бахарчиев", author.getSurname());
        assertEquals("Иванович", author.getLastName());

        // Проверяем, что session.update() был вызван
        verify(session).update(author);
    }

    @Test
    void shouldDeleteById() {
        int id = 1;
        Author author = new Author("Иван", "Иванов", "Иванович");
        when(session.get(Author.class,id)).thenReturn(author);

        authorDao.remove(id);

        verify(session).get(Author.class,id);
        verify(session).remove(author);


    }


}
