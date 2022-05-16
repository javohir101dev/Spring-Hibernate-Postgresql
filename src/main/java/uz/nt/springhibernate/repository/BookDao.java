package uz.nt.springhibernate.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import uz.nt.springhibernate.helper.NumberHelper;
import uz.nt.springhibernate.helper.StringHelper;
import uz.nt.springhibernate.model.Book;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Repository
//@RequiredArgsConstructor
public class BookDao {


    public boolean addBookPostgres(Book book, SessionFactory postgresSession) {
        Session session = postgresSession.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(book);
            transaction.commit();

            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public Book getBookByIdPostgres(Integer id, SessionFactory postgresSession) {
        Session session = postgresSession.openSession();
        try {
            Book book = session.get(Book.class, id);
            return book;
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }

    public List<Book> getAllBooksWithNativeQueryAndParamsPostgres(MultiValueMap<String,
            String> params,
                                                                  SessionFactory postgresSession) {

        StringBuilder queryBuilder = new StringBuilder(" ");
        if (params.containsKey("id") && NumberHelper.isValidNumber(params.getFirst("id"))) {
            queryBuilder.append(" and b.id=:id ");
        }

        if (params.containsKey("genre") && StringHelper.isValid(params.getFirst("genre"))) {
            queryBuilder.append(" and b.genre=:genre ");
        }

        if (params.containsKey("name") && StringHelper.isValid(params.getFirst("name"))) {
            queryBuilder.append(" and b.nameuz=:name ");
        }


        try (Session session = postgresSession.openSession()) {
            String queryStr = "select * from book b where 1=1 " + queryBuilder;
            Query<Book> query = session.createNativeQuery(queryStr, Book.class);

            if (params.containsKey("id") && NumberHelper.isValidNumber(params.getFirst("id"))) {
                query.setParameter("id", NumberHelper.tiInt(params.getFirst("id")));
            }

            if (params.containsKey("genre") && StringHelper.isValid(params.getFirst("genre"))) {
                query.setParameter("genre", params.getFirst("genre"));
            }

            if (params.containsKey("name") && StringHelper.isValid(params.getFirst("name"))) {
                query.setParameter("name", params.getFirst("name"));
            }

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Book> getAllBooksWithNativeQuerySecondWayPostgres(MultiValueMap<String, String> params,
                                                                  SessionFactory postgresSession) {

        List<Book> resulBookList = new ArrayList<>();

        try (Session session = postgresSession.openSession()) {

            NativeQuery sqlQuery = session.createSQLQuery("select * from book ");
            List<Object[]> rows = sqlQuery.list();

            for (Object[] row : rows) {
                Book queryBook = new Book();
                queryBook.setId(row[0] == null ? null : Integer.valueOf(row[0].toString()));
                queryBook.setCost(row[1] == null ? null : (BigDecimal) row[1]);
                queryBook.setGenre(row[2] == null ? null : row[2].toString());
                queryBook.setNameRu(row[3] == null ? null : row[3].toString());
                queryBook.setNameUz(row[4] == null ? null : row[4].toString());
                queryBook.setPageCount(row[5] == null ? null : Integer.valueOf(row[5].toString()));
                queryBook.setPublishedDate(row[6] == null ? null : Date.valueOf(row[6].toString().substring(0, 10)));
                queryBook.setAuthorId(row[7] == null ? null : Integer.valueOf(row[7].toString()));
                queryBook.setPublisher(row[8] == null ? null : Integer.valueOf(row[8].toString()));
                resulBookList.add(queryBook);
            }
            return resulBookList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Book> getAllBooksPostgres( SessionFactory postgresSession) {

        try (Session session = postgresSession.openSession()) {

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

            Root<Book> root = criteriaQuery.from(Book.class);
            criteriaQuery.select(root).where(
                    criteriaBuilder.gt(root.get("cost"), 100000),  // gt means greaterThan
                    criteriaBuilder.between(root.get("pageCount"), 100, 200)
            );

            criteriaQuery.orderBy(
                    criteriaBuilder.desc(root.get("pageCount")),
                    criteriaBuilder.desc(root.get("id"))
            );

//            select * from book where cost > 100000 and page_count between 100 and 200 order by page_count desc, id desc

            Query<Book> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book updateBookPostgres(Book book, SessionFactory postgresSession) {
        Session session = postgresSession.openSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();

            session.update(book);

            transaction.commit();

            return book;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }

    public Book deleteBookByIdPostgres(Book book, SessionFactory postgresSession) {
        Session session = postgresSession.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            session.delete(book);

            transaction.commit();

            return book;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }


}
