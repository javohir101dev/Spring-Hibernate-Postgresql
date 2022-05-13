package uz.nt.springhibernate.service;

import lombok.RequiredArgsConstructor;
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

    public ResponseDto<Book> saveBook(Book book){

        boolean success = bookDao.addBook(book);

        if (success){
            return new ResponseDto<Book>(true, 0, "Ok", book);
        }
        return new ResponseDto<Book>(false, -1, "Error in saving", null);
    }

    public ResponseDto<Book> getBookById(Integer id) {
        Book bookById = bookDao.getBookById(id);
        if (bookById == null){
            return new ResponseDto<>(false, -1, "This book is not found", null);
        }
        return new ResponseDto<>(true, 0, "Ok", bookById);
    }

    public ResponseDto<List<Book>> getAllBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        if (bookList == null){
            return new ResponseDto<>(false, -1, "There are no books in the database", null);
        }
        return new ResponseDto<>(true, 0, "Ok", bookList);

    }

    public ResponseDto<Book> updateBook(Book book) {
        if (book == null || book.getId() == null){
            return new ResponseDto<>(false, -1, "This book or its id cannot be null", null);
        }

        Book bookById = bookDao.getBookById(book.getId());

        if (bookById == null){
            return new ResponseDto<>(false, -1,
                    String.format("Book with id: %s is not found",
                            book.getId()), null);
        }

       Book savedBook =  bookDao.updateBook(book);

        if (savedBook == null){
            return new ResponseDto<>(false, -1, "Error in updating book", null);
        }
        return new ResponseDto<>(true, 0, "Ok", savedBook);
    }

    public ResponseDto<Book> deleteBookById(Integer id) {
        if (id == null){
            return new ResponseDto<>(false, -1, "Id cannot be null", null);
        }

        Book bookById = bookDao.getBookById(id);

        if (bookById == null){
            return new ResponseDto<>(false, -1,
                    String.format("Book with id: %s is not found",
                            id), null);
        }

          Book deletedBook = bookDao.deleteBookById(bookById);

        if (deletedBook == null){
            return new ResponseDto<>(false, -1, "Error in deleting book", null);
        }
        return new ResponseDto<>(true, 0, "Ok", deletedBook);
    }

    public ResponseDto<List<Book>> getAllBooksWithNativeQueryAndParams(MultiValueMap<String, String> params) {
        List<Book> bookList = bookDao.getAllBooksWithNativeQueryAndParams(params);
        if (bookList != null && !bookList.isEmpty()){
            return new ResponseDto<>(true, 0, "Ok", bookList);
        }
        return new ResponseDto<>(false, -1, "Books is not found", null);
    }
}
