/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ttu.idu0075.book;

import ee.ttu.idu0075._2015.ws.book.AddBookRequest;
import ee.ttu.idu0075._2015.ws.book.BookType;
import ee.ttu.idu0075._2015.ws.book.GetBookListRequest;
import ee.ttu.idu0075._2015.ws.book.GetBookListResponse;
import ee.ttu.idu0075._2015.ws.book.GetBookRequest;
import java.math.BigInteger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Denis
 */
@Path("books")
public class BooksResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BooksResource
     */
    public BooksResource() {
    }

    /**
     * Retrieves representation of an instance of ee.ttu.idu0075.book.BooksResource
     * @return an instance of ee.ttu.idu0075._2015.ws.book.BookType
     */
    @GET
    @Produces("application/json")
    public GetBookListResponse getBookList(@QueryParam("token") String token) {
        
        BookService bs=new BookService();
        GetBookListRequest request=new GetBookListRequest();
        request.setToken(token);
        return bs.getBookList(request);
    }

    /**
     * PUT method for updating or creating an instance of BooksResource
     * @param content representation for the resource
     */
    @GET
    @Path("{id : \\d+}")
    @Produces("application/json")
    public BookType getBook(@PathParam("id") String id,
            @QueryParam("token") String token) {
        BookService bs=new BookService();
        GetBookRequest request = new GetBookRequest();
        request.setToken(token);
        request.setId(BigInteger.valueOf(Integer.parseInt(id)));
        return bs.getBook(request);
    }
    
    /*
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public BookType addBook(@QueryParam("token") String token,@QueryParam("name") String name, @QueryParam("author") String author,
            @QueryParam("year") int year,@QueryParam("isbn") String isbn){
        BookService bs=new BookService();
        AddBookRequest request=new AddBookRequest();
        request.setToken(token);
        request.setAuthor(author);
        request.setISBN(isbn);
        request.setName(name);
        request.setYear(BigInteger.valueOf(year));
        
        return bs.addBook(request);
      
    }
*/
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public BookType addBook(BookType content, @QueryParam("token") String token){
        BookService bs=new BookService();
        AddBookRequest request=new AddBookRequest();
        request.setToken(token);
        request.setAuthor(content.getAuthor());
        request.setISBN(content.getISBN());
        request.setName(content.getName());
        request.setYear(content.getYear());
        
        return bs.addBook(request);
      
    }
}
