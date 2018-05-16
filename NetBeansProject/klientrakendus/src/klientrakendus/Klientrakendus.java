/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klientrakendus;

import ee.ttu.idu0075._2015.ws.book.AddBookRequest;
import ee.ttu.idu0075._2015.ws.book.AddOrderBookRequest;
import ee.ttu.idu0075._2015.ws.book.AddOrderRequest;
import ee.ttu.idu0075._2015.ws.book.BookType;
import ee.ttu.idu0075._2015.ws.book.GetBookListRequest;
import ee.ttu.idu0075._2015.ws.book.GetBookListResponse;
import ee.ttu.idu0075._2015.ws.book.GetBookRequest;
import ee.ttu.idu0075._2015.ws.book.GetOrderBookListRequest;
import ee.ttu.idu0075._2015.ws.book.GetOrderListRequest;
import ee.ttu.idu0075._2015.ws.book.GetOrderListResponse;
import ee.ttu.idu0075._2015.ws.book.GetOrderRequest;
import ee.ttu.idu0075._2015.ws.book.OrderBookListType;
import ee.ttu.idu0075._2015.ws.book.OrderBookType;
import ee.ttu.idu0075._2015.ws.book.OrderType;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Denis
 */
public class Klientrakendus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    boolean run=true;
    while(run){
        Scanner obj=new Scanner(System.in);
        System.out.println("Enter Operation number: \n"
                + "1. Add Book\n"
                + "2. Add Order\n"
                + "3. Get Book\n"
                + "4. Get Book List\n"
                + "5. Get Order\n"
                + "6. Get Order List\n"
                + "7. Add Order Book\n"
                + "8. Get Order Book List\n"
                + "9. Exit ");
        String operation=obj.next();
        try{
            int operationInt=Integer.parseInt(operation);
            switch(operationInt){
                case 1: addBookOp();
                       
                case 2: addOrderOp();
                case 3: getBookOp();
                case 4: getBookListOp();
                case 5: getOrderOp();
                case 6: getOrderListOp();
                case 7: addOrderBookOp();
                case 8: getOrderBookListOp();
                case 9: run=false;
        }

        }catch(Exception ex){
            System.out.println("You must enter a number");
            run=false;
            return;
        }
        }
    }
  

    private static void addBookOp(){
        Scanner obj=new Scanner(System.in);
        AddBookRequest request=new AddBookRequest();
        System.out.println("Enter token: ");
        request.setToken(obj.next());
        System.out.println("Book Name: ");
        request.setName(obj.next());
        System.out.println("Book Author: ");
        request.setAuthor(obj.next());
        System.out.println("Book year: ");
        BigInteger year=BigInteger.valueOf(Integer.parseInt(obj.next()));
        request.setYear(year);
        System.out.println("Enter ISBN: ");
        request.setISBN(obj.next());
        addBook(request);
        System.exit(0);
    }
    
    
    private static BookType addBook(ee.ttu.idu0075._2015.ws.book.AddBookRequest parameter) {
        ee.ttu.idu0075._2015.ws.book.BookService service = new ee.ttu.idu0075._2015.ws.book.BookService();
        ee.ttu.idu0075._2015.ws.book.OrderPortType port = service.getBookPort();
        return port.addBook(parameter);
    }
    
    private static void addOrderOp() throws DatatypeConfigurationException{
        Scanner obj=new Scanner(System.in);
        AddOrderRequest request=new AddOrderRequest();
        System.out.println("Enter token: ");
        request.setToken(obj.next());
        System.out.println("Order No: ");
        request.setOrderNo(obj.next());
        System.out.println("Order Date: (e.g. 2018-01-01)");
        String orderDate=obj.next();
        String[] orderDates=orderDate.split("-");
        int year=Integer.parseInt(orderDates[0]);
        int month=Integer.parseInt(orderDates[1]);
        int day=Integer.parseInt(orderDates[2]);
        XMLGregorianCalendar orderDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(year,month,day,0,0,0,0,0);
        request.setOrderDate(orderDateXml);
        
        System.out.println("Due Date: (e.g. 2018-01-02)");
        String dueDate=obj.next();
        String[] dueDates=dueDate.split("-");
        year=Integer.parseInt(dueDates[0]);
        month=Integer.parseInt(dueDates[1]);
        day=Integer.parseInt(dueDates[2]);
        XMLGregorianCalendar dueDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(year,month,day,0,0,0,0,0);
        request.setDueDate(dueDateXml);

        
        System.out.println("Customer Name: ");
        request.setCustomerName(obj.next());
        
        
        
        addOrder(request);
        System.exit(0);

    }

    private static OrderType addOrder(ee.ttu.idu0075._2015.ws.book.AddOrderRequest parameter) {
        ee.ttu.idu0075._2015.ws.book.BookService service = new ee.ttu.idu0075._2015.ws.book.BookService();
        ee.ttu.idu0075._2015.ws.book.OrderPortType port = service.getBookPort();
        return port.addOrder(parameter);
    }
    
    private static void getBookOp(){
      
            GetBookRequest request=new GetBookRequest();
            Scanner obj=new Scanner(System.in);
            System.out.println("Enter Token: ");
            request.setToken(obj.next());
            System.out.println("Enter book id: ");
            request.setId(BigInteger.valueOf(Integer.parseInt(obj.next())));
            BookType book=getBook(request);
            System.out.println("id: "+book.getId());
            System.out.println("Name: "+book.getName());
            System.out.println("Author: "+book.getAuthor());
            System.out.println("Year: "+book.getYear());
            System.out.print("ISBN: "+book.getISBN());
            System.exit(0);
            
      
    }
    
    private static BookType getBook(ee.ttu.idu0075._2015.ws.book.GetBookRequest parameter) {
        ee.ttu.idu0075._2015.ws.book.BookService service = new ee.ttu.idu0075._2015.ws.book.BookService();
        ee.ttu.idu0075._2015.ws.book.OrderPortType port = service.getBookPort();
        return port.getBook(parameter);
    }
    
    
    
    private static void getOrderOp(){
        GetOrderRequest request = new GetOrderRequest();
        Scanner obj=new Scanner(System.in);
        System.out.println("Enter Token: ");
        request.setToken(obj.next());
        System.out.println("Enter order id: ");
        request.setId(BigInteger.valueOf(Integer.parseInt(obj.next())));
        OrderType order=getOrder(request);
        System.out.println("Id: "+order.getId());
        System.out.println("orderNo: "+order.getOrderNo());
        System.out.println("orderDate: "+order.getOrderDate());
        System.out.println("dueDate: "+order.getDueDate());
        System.out.println("customerName: "+order.getCustomerName());
        System.out.println("netAmount: "+order.getNetAmount());
        System.out.println("vatAmount: "+order.getVatAmount());
        System.exit(0);
    }
    
    private static OrderType getOrder(ee.ttu.idu0075._2015.ws.book.GetOrderRequest parameter) {
        ee.ttu.idu0075._2015.ws.book.BookService service = new ee.ttu.idu0075._2015.ws.book.BookService();
        ee.ttu.idu0075._2015.ws.book.OrderPortType port = service.getBookPort();
        return port.getOrder(parameter);
    }
    
    private static void getBookListOp(){
        GetBookListRequest request=new GetBookListRequest();
        Scanner obj=new Scanner(System.in);
        System.out.println("Enter token: ");
        request.setToken(obj.next());
        GetBookListResponse books=getBookList(request);
        for(BookType book:books.getBook()){
            System.out.println("id: "+book.getId());
            System.out.println("Name: "+book.getName());
            System.out.println("Author: "+book.getAuthor());
            System.out.println("Year: "+book.getYear());
            System.out.println("ISBN: "+book.getISBN());
            System.out.println("\n");
        }
        System.exit(0);
    }
    
    private static GetBookListResponse getBookList(ee.ttu.idu0075._2015.ws.book.GetBookListRequest parameter) {
        ee.ttu.idu0075._2015.ws.book.BookService service = new ee.ttu.idu0075._2015.ws.book.BookService();
        ee.ttu.idu0075._2015.ws.book.OrderPortType port = service.getBookPort();
        return port.getBookList(parameter);
    }
    private static void getOrderListOp() throws DatatypeConfigurationException{
        GetOrderListRequest request=new GetOrderListRequest();
        Scanner obj=new Scanner(System.in);
        System.out.println("Enter token: ");
        request.setToken(obj.next());
        System.out.println("Has related books: \"jah/ei\"");
        if(obj.next().equalsIgnoreCase("jah")){
            request.setHasRelatedBooks("jah");
        }else{
            request.setHasRelatedBooks("ei");
        }
        System.out.println("Start Date: (e.g. 2018-01-01)");
        String orderDate=obj.next();
        String[] orderDates=orderDate.split("-");
        int year=Integer.parseInt(orderDates[0]);
        int month=Integer.parseInt(orderDates[1]);
        int day=Integer.parseInt(orderDates[2]);
        XMLGregorianCalendar orderDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(year,month,day,0,0,0,0,0);
        request.setStartDate(orderDateXml);
        
        System.out.println("End Date: (e.g. 2018-01-01)");
        String dueDate=obj.next();
        String[] dueDates=dueDate.split("-");
        year=Integer.parseInt(dueDates[0]);
        month=Integer.parseInt(dueDates[1]);
        day=Integer.parseInt(dueDates[2]);
        XMLGregorianCalendar dueDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(year,month,day,0,0,0,0,0);
        request.setEndDate(dueDateXml);

        GetOrderListResponse list=getOrderList(request);
        for(OrderType order:list.getOrder()){
            System.out.println("Id: "+order.getId());
            System.out.println("orderNo: "+order.getOrderNo());
            System.out.println("orderDate: "+order.getOrderDate());
            System.out.println("dueDate: "+order.getDueDate());
            System.out.println("customerName: "+order.getCustomerName());
            System.out.println("netAmount: "+order.getNetAmount());
            System.out.println("vatAmount: "+order.getVatAmount());
        }
        System.exit(0);

        
    }
    private static GetOrderListResponse getOrderList(ee.ttu.idu0075._2015.ws.book.GetOrderListRequest parameter) {
        ee.ttu.idu0075._2015.ws.book.BookService service = new ee.ttu.idu0075._2015.ws.book.BookService();
        ee.ttu.idu0075._2015.ws.book.OrderPortType port = service.getBookPort();
        return port.getOrderList(parameter);
    }
    
    private static void addOrderBookOp(){
        AddOrderBookRequest request=new AddOrderBookRequest();
        Scanner obj=new Scanner(System.in);
        System.out.println("Enter token:");
        request.setToken(obj.next());
        System.out.println("order id: ");
        request.setOrderId(BigInteger.valueOf(Integer.parseInt(obj.next())));
        System.out.println("book id:");
        request.setBookId(BigInteger.valueOf(Integer.parseInt(obj.next())));
        addOrderBook(request);
        System.exit(0);
    }
    
    private static OrderBookType addOrderBook(ee.ttu.idu0075._2015.ws.book.AddOrderBookRequest parameter) {
        ee.ttu.idu0075._2015.ws.book.BookService service = new ee.ttu.idu0075._2015.ws.book.BookService();
        ee.ttu.idu0075._2015.ws.book.OrderPortType port = service.getBookPort();
        return port.addOrderBook(parameter);
    }
    
    private static void getOrderBookListOp(){
        GetOrderBookListRequest request=new GetOrderBookListRequest();
        Scanner obj=new Scanner(System.in);
        System.out.println("Enter token:");
        request.setToken(obj.next());
        System.out.println("order id: ");
        request.setOrderId(BigInteger.valueOf(Integer.parseInt(obj.next())));
        OrderBookListType list=getOrderBookList(request);
        for(OrderBookType orderBook:list.getOrderBook()){
            System.out.println("id: "+orderBook.getBook().getId());
            System.out.println("name: "+orderBook.getBook().getName());
            System.out.println("author: "+orderBook.getBook().getAuthor());
            System.out.println("year: "+orderBook.getBook().getYear());
            System.out.println("ISBN: "+orderBook.getBook().getISBN());
            System.out.println("Quantity: "+orderBook.getQuantity());
            System.out.println("Unit Price: "+orderBook.getUnitPrice()+"\n");
            
        }
    }

    private static OrderBookListType getOrderBookList(ee.ttu.idu0075._2015.ws.book.GetOrderBookListRequest parameter) {
        ee.ttu.idu0075._2015.ws.book.BookService service = new ee.ttu.idu0075._2015.ws.book.BookService();
        ee.ttu.idu0075._2015.ws.book.OrderPortType port = service.getBookPort();
        return port.getOrderBookList(parameter);
    }
    
}
