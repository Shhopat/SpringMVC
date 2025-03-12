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

}
