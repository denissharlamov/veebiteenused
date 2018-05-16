/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ttu.idu0075.book;

import ee.ttu.idu0075._2015.ws.book.AddBookRequest;
import ee.ttu.idu0075._2015.ws.book.AddOrderBookRequest;
import ee.ttu.idu0075._2015.ws.book.AddOrderRequest;
import ee.ttu.idu0075._2015.ws.book.BookType;
import ee.ttu.idu0075._2015.ws.book.GetOrderBookListRequest;
import ee.ttu.idu0075._2015.ws.book.GetOrderListRequest;
import ee.ttu.idu0075._2015.ws.book.GetOrderListResponse;
import ee.ttu.idu0075._2015.ws.book.GetOrderRequest;
import ee.ttu.idu0075._2015.ws.book.OrderBookListType;
import ee.ttu.idu0075._2015.ws.book.OrderBookType;
import ee.ttu.idu0075._2015.ws.book.OrderType;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * REST Web Service
 *
 * @author Denis
 */
@Path("orders")
public class OrdersResource {

   @Context
    private UriInfo context;

    /**
     * Creates a new instance of OrdersResource
     */
    public OrdersResource() {
    }

    /**
     * Retrieves representation of an instance of ee.ttu.idu0075.book.OrdersResource
     * @param token
     * @param startDate
     * @return an instance of ee.ttu.idu0075._2015.ws.book.OrderType
     */
    
    @GET
    @Produces("application/json")
    public GetOrderListResponse getOrderList(@QueryParam("token") String token, @QueryParam("startDate") String startDate, 
            @QueryParam("endDate") String endDate, @QueryParam("hasRelatedBooks") String hasRelatedBooks) throws DatatypeConfigurationException {
        BookService bs=new BookService();
        GetOrderListRequest request=new GetOrderListRequest();
        request.setToken(token);
        request.setHasRelatedBooks(hasRelatedBooks);
        
        String[] startDateSplit=startDate.split("-");
        int startDateYear=Integer.parseInt(startDateSplit[0]);
        int startDateMonth=Integer.parseInt(startDateSplit[1]);
        int startDateDay=Integer.parseInt(startDateSplit[2]);
        
        String[] endDateSplit=endDate.split("-");
        int endDateYear=Integer.parseInt(endDateSplit[0]);
        int endDateMonth=Integer.parseInt(endDateSplit[1]);
        int endDateDay=Integer.parseInt(endDateSplit[2]);
        
        XMLGregorianCalendar startDateXml=DatatypeFactory.newInstance().newXMLGregorianCalendar(startDateYear, startDateMonth, startDateDay, 0, 0, 0, 0, 0);
        XMLGregorianCalendar endDateXml=DatatypeFactory.newInstance().newXMLGregorianCalendar(endDateYear, endDateMonth, endDateDay, 0, 0, 0, 0, 0);
        
        request.setStartDate(startDateXml);
        request.setEndDate(endDateXml);
        
        return bs.getOrderList(request);      
    }

    /**
     * PUT method for updating or creating an instance of OrdersResource
     * @param content representation for the resource
     */
    @GET
    @Path("{id : \\d+}")
    @Produces("application/json")
    public OrderType getOrder(@PathParam("id") String id,
            @QueryParam("token") String token) {
        BookService bs=new BookService();
        GetOrderRequest request = new GetOrderRequest();
        request.setToken(token);
        request.setId(BigInteger.valueOf(Integer.parseInt(id)));
        return bs.getOrder(request);
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public OrderType addOrder(OrderType content, @QueryParam("token") String token){
        BookService bs=new BookService();
        AddOrderRequest request=new AddOrderRequest();
        request.setToken(token);
        request.setOrderNo(content.getOrderNo());
        request.setOrderDate(content.getOrderDate());
        request.setDueDate(content.getDueDate());
        request.setCustomerName(content.getCustomerName());
        return bs.addOrder(request);
}
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/addOrderBook")
    public OrderBookType addOrderBook(AddOrderBookRequest content/*, @QueryParam("token") String token*/){
        BookService bs=new BookService();
        AddOrderBookRequest request=new AddOrderBookRequest();
        request.setToken(content.getToken());
        request.setOrderId(content.getOrderId());
        request.setBookId(content.getBookId());
        request.setQuantity(content.getQuantity());
        request.setUnitPrice(content.getUnitPrice());
        return bs.addOrderBook(request);
    }

    @GET
    @Produces("application/json")
    @Path("/orderBookList/{id}")
    public OrderBookListType getOrderBookList(@PathParam("id") int orderId,@QueryParam("token") String token){
        BookService bs=new BookService();
        GetOrderBookListRequest request=new GetOrderBookListRequest();
        request.setToken(token);
        request.setOrderId(BigInteger.valueOf(orderId));
        return bs.getOrderBookList(request);
    }
}
