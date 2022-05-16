package uz.nt.springhibernate.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import uz.nt.springhibernate.dto.ResponseDto;
import uz.nt.springhibernate.model.Book;
import uz.nt.springhibernate.repository.BookDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {




    private final BookDao bookDao;

    public ResponseDto<Book> saveBook(Book book, SessionFactory sessionFactory){

        boolean success = bookDao.addBookPostgres(book, sessionFactory);

        if (success){
            return new ResponseDto<Book>(true, 0, "Ok", book);
        }
        return new ResponseDto<Book>(false, -1, "Error in saving", null);
    }

    public ResponseDto<Book> getBookById(Integer id, SessionFactory sessionFactory) {
        Book bookById = bookDao.getBookByIdPostgres(id, sessionFactory);
        if (bookById == null){
            return new ResponseDto<>(false, -1, "This book is not found", null);
        }
        return new ResponseDto<>(true, 0, "Ok", bookById);
    }

    public ResponseDto<List<Book>> getAllBooks(SessionFactory sessionFactory) {
        List<Book> bookList = bookDao.getAllBooksPostgres(sessionFactory);
        if (bookList == null){
            return new ResponseDto<>(false, -1, "There are no books in the database", null);
        }
        return new ResponseDto<>(true, 0, "Ok", bookList);

    }

    public ResponseDto<Book> updateBook(Book book, SessionFactory sessionFactory) {
        if (book == null || book.getId() == null){
            return new ResponseDto<>(false, -1, "This book or its id cannot be null", null);
        }

        Book bookById = bookDao.getBookByIdPostgres(book.getId(),  sessionFactory);

        if (bookById == null){
            return new ResponseDto<>(false, -1,
                    String.format("Book with id: %s is not found",
                            book.getId()), null);
        }

       Book savedBook =  bookDao.updateBookPostgres(book, sessionFactory);

        if (savedBook == null){
            return new ResponseDto<>(false, -1, "Error in updating book", null);
        }
        return new ResponseDto<>(true, 0, "Ok", savedBook);
    }

    public ResponseDto<Book> deleteBookById(Integer id, SessionFactory sessionFactory) {
        if (id == null){
            return new ResponseDto<>(false, -1, "Id cannot be null", null);
        }

        Book bookById = bookDao.getBookByIdPostgres(id, sessionFactory);

        if (bookById == null){
            return new ResponseDto<>(false, -1,
                    String.format("Book with id: %s is not found",
                            id), null);
        }

          Book deletedBook = bookDao.deleteBookByIdPostgres(bookById, sessionFactory);

        if (deletedBook == null){
            return new ResponseDto<>(false, -1, "Error in deleting book", null);
        }
        return new ResponseDto<>(true, 0, "Ok", deletedBook);
    }

    public ResponseDto<List<Book>> getAllBooksWithNativeQueryAndParams(MultiValueMap<String,
            String> params, SessionFactory sessionFactory) {
        List<Book> bookList = bookDao.getAllBooksWithNativeQueryAndParamsPostgres(params, sessionFactory);
        if (bookList != null && !bookList.isEmpty()){
            return new ResponseDto<>(true, 0, "Ok", bookList);
        }
        return new ResponseDto<>(false, -1, "Books is not found", null);
    }
}
