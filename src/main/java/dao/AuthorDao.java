package dao;


import model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AuthorDao {

    private final SessionFactory sessionFactory;


    @Autowired
    public AuthorDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Author> getAllAuthors() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Author", Author.class).getResultList();
    }

    @Transactional
    public void createAuthor(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.save(author);
    }

    @Transactional
    public Author getByAuthor(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Author.class, id);
    }

    @Transactional
    public void update(int id, Author author) {
        Session session = sessionFactory.getCurrentSession();
        Author author1 = session.get(Author.class, id);
        author1.setName(author.name);
        author1.setSurname(author.getSurname());
        author1.setLastName(author.getLastName());
        session.update(author1);
    }

    @Transactional
    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(getByAuthor(id));
    }

}
