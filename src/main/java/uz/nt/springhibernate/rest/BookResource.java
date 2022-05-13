package uz.nt.springhibernate.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uz.nt.springhibernate.dto.ResponseDto;
import uz.nt.springhibernate.model.Book;
import uz.nt.springhibernate.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookResource {

    @Autowired
    private BookService bookService;

//    CREATE
    @PostMapping("/add")
    public ResponseDto<Book> addBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

//    READ
    @GetMapping("/byId/{id}")
    public ResponseDto<Book> getBookById(@PathVariable Integer id){
        return bookService.getBookById(id);
    }

//    READ ALL
    @GetMapping("/get-all")
    public ResponseDto<List<Book>> getAllBooks(){
        return bookService.getAllBooks();
    }

//    READ ALL BY NATIVE Query SEARCH
    @GetMapping("/get-all-by-native-query")
    public ResponseDto<List<Book>> getAllBooksWithNativeQueryAndParams(@RequestParam MultiValueMap<String, String> params){

       return bookService.getAllBooksWithNativeQueryAndParams(params);
    }

//    UPDATE
    @PutMapping("/update")
    public ResponseDto<Book> updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

//  DELETE
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseDto<Book> deleteBookById(@PathVariable Integer id){
        return bookService.deleteBookById(id);
    }

}
