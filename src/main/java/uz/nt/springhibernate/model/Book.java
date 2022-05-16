package uz.nt.springhibernate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
//    @GeneratedValue(generator = "book_id_seq")
//    @SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nameuz")
    private String nameUz;

    @Column(name = "nameru")
    private String nameRu;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "genre")
    private String genre;

    @Column(name = "publisher_id")
    private Integer publisher;

    public Book(Integer id, String nameUz, BigDecimal cost, Date publishedDate, Integer pageCount, String genre) {
        this.id = id;
        this.nameUz = nameUz;
        this.cost = cost;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.genre = genre;
    }
}
