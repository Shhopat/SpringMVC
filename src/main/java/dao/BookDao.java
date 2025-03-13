package dao;

import model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookDao {
    private final SessionFactory sessionFactory;
    private final AuthorDao authorDao;

    @Autowired
    public BookDao(SessionFactory sessionFactory, AuthorDao authorDao) {
        this.sessionFactory = sessionFactory;
        this.authorDao = authorDao;
    }

    @Transactional
    public List<Book> getBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book", Book.class).getResultList();
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public Book getBookById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

}
