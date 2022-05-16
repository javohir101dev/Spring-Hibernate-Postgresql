package uz.nt.springhibernate.rest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uz.nt.springhibernate.dto.ResponseDto;
import uz.nt.springhibernate.model.Book;
import uz.nt.springhibernate.service.BookService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookResource {


    @Autowired
    private BookService bookService;
    @Autowired
//    @Qualifier("postgres")
    @Resource(name = "postgres")
    private SessionFactory postgresSession;

    @Autowired
//    @Qualifier("mysql")
    @Resource(name = "mysql")
    private SessionFactory mySQLSession;


//    CREATE
    @PostMapping("/postgres-add")
    public ResponseDto<Book> addBookPostgres(@RequestBody Book book){
        return bookService.saveBook(book, postgresSession);
    }

//    READ
    @GetMapping("/postgres-byId/{id}")
    public ResponseDto<Book> getBookByIdPostgres(@PathVariable Integer id){
        return bookService.getBookById(id, postgresSession);
    }

//    READ ALL
    @GetMapping("/postgres-get-all")
    public ResponseDto<List<Book>> getAllBooksPostgres(){
        return bookService.getAllBooks(postgresSession);
    }

//    READ ALL BY NATIVE Query SEARCH
    @GetMapping("/postgres-get-all-by-native-query")
    public ResponseDto<List<Book>> getAllBooksWithNativeQueryAndParamsPostgres(@RequestParam MultiValueMap<String, String> params){

       return bookService.getAllBooksWithNativeQueryAndParams(params, postgresSession);
    }

//    UPDATE
    @PutMapping("/postgres-update")
    public ResponseDto<Book> updateBookPostgres(@RequestBody Book book){
        return bookService.updateBook(book, postgresSession);
    }

//  DELETE
    @DeleteMapping("/postgres-delete-by-id/{id}")
    public ResponseDto<Book> deleteBookByIdPostgres(@PathVariable Integer id){
        return bookService.deleteBookById(id, postgresSession);
    }


//    CREATE
    @PostMapping("/mySql-add")
    public ResponseDto<Book> addBookMySQL(@RequestBody Book book){
        return bookService.saveBook(book, mySQLSession);
    }

    //    READ
    @GetMapping("/mySql-byId/{id}")
    public ResponseDto<Book> getBookByIdMySQL(@PathVariable Integer id){
        return bookService.getBookById(id, mySQLSession);
    }

    //    READ ALL
    @GetMapping("/mySql-get-all")
    public ResponseDto<List<Book>> getAllBooksMySQL(){
        return bookService.getAllBooks(mySQLSession);
    }

    //    READ ALL BY NATIVE Query SEARCH
    @GetMapping("/mySql-get-all-by-native-query")
    public ResponseDto<List<Book>> getAllBooksWithNativeQueryAndParamsMySQL(@RequestParam MultiValueMap<String, String> params){

        return bookService.getAllBooksWithNativeQueryAndParams(params, mySQLSession);
    }

    //    UPDATE
    @PutMapping("/mySql-update")
    public ResponseDto<Book> updateBookMySQL(@RequestBody Book book){
        return bookService.updateBook(book, mySQLSession);
    }

    //  DELETE
    @DeleteMapping("/mySql-delete-by-id/{id}")
    public ResponseDto<Book> deleteBookByIdMySQL(@PathVariable Integer id){
        return bookService.deleteBookById(id, mySQLSession);
    }

}
