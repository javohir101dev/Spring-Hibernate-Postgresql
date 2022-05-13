package uz.nt.springhibernate.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.nt.springhibernate.dto.ResponseDto;
import uz.nt.springhibernate.model.Book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class BookDao {

    private final SessionFactory sessionFactory;

    public boolean addBook(Book book){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(book);
            transaction.commit();

            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    public Book getBookById(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
//            transaction.begin();
            Book book = session.get(Book.class, id);
//            transaction.commit();
            return book;
        }catch (Exception e){
//            transaction.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public List<Book> getAllBooksWithNativeQuery() {

        Session session = sessionFactory.openSession();
        try {
            List<Book> bookList = session.createNativeQuery("Select * from book", Book.class).getResultList();
            return bookList;
        }catch (Exception e){
            return null;
        }finally {
            session.close();
        }
    }

    public List<Book> getAllBooks() {

        Session session = sessionFactory.openSession();
//        Transaction transaction = session.getTransaction();
        try {
//            transaction.begin();
//            List<Book> bookList = Collections.unmodifiableList(session.createCriteria(Book.class).list());
            List<Book> bookList = session.createCriteria(Book.class).list();


//            transaction.commit();
            return bookList;
        }catch (Exception e){
//            transaction.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public Book updateBook(Book book) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            session.update(book);

            transaction.commit();

            return book;
        }catch (Exception e){
            transaction.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public Book deleteBookById(Book book) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            session.delete(book);

            transaction.commit();

            return book;
        }catch (Exception e){
            transaction.rollback();
            return null;
        }finally {
            session.close();
        }
    }


//    NativeQuery sqlQuery = session.createSQLQuery("select * from book where id = :id ");
//        sqlQuery.setParameter("id", 4);
//    List <Object[]> rows = sqlQuery.list();
//
//        for (Object[] row : rows) {
//        Book book2 = new Book();
//        book2.setId(Integer.parseInt(row[0].toString()));
//        System.out.println(book2);
//    }

//    List<Book> resultList = session.createNativeQuery("Select * from book", Book.class).getResultList();


}
