/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ttu.idu0075.book;

import ee.ttu.idu0075._2015.ws.book.BookType;
import ee.ttu.idu0075._2015.ws.book.GetBookListResponse;
import ee.ttu.idu0075._2015.ws.book.GetOrderListResponse;
import ee.ttu.idu0075._2015.ws.book.OrderBookListType;
import ee.ttu.idu0075._2015.ws.book.OrderBookType;
import ee.ttu.idu0075._2015.ws.book.OrderType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Denis
 */

@WebService(serviceName = "BookService", portName = "BookPort", endpointInterface = "ee.ttu.idu0075._2015.ws.book.OrderPortType", targetNamespace = "http://www.ttu.ee/idu0075/2015/ws/book", wsdlLocation = "WEB-INF/wsdl/BookService/BookService.wsdl")
public class BookService {

    static int nextBookId=1;
    static int nextOrderId=1;
    static List<BookType> bookList=new ArrayList<BookType>();
    static List<OrderType> orderList=new ArrayList<OrderType>();
    String RequiredToken="secret";
    
    public ee.ttu.idu0075._2015.ws.book.BookType getBook(ee.ttu.idu0075._2015.ws.book.GetBookRequest parameter) {
        BookType bt=null;
        if(parameter.getToken().equalsIgnoreCase(RequiredToken)){
            for(int i=0;i<bookList.size();i++){
                if(bookList.get(i).getId().equals(parameter.getId())){
                    bt=bookList.get(i);
                }
            }     
        }
    return bt;
    }

    public ee.ttu.idu0075._2015.ws.book.BookType addBook(ee.ttu.idu0075._2015.ws.book.AddBookRequest parameter) {
        BookType bt=new BookType();
        if(parameter.getToken().equalsIgnoreCase(RequiredToken)){
            bt.setAuthor(parameter.getAuthor());
            bt.setISBN(parameter.getISBN());
            bt.setId(BigInteger.valueOf(nextBookId++));
            bt.setName(parameter.getName());
            bt.setYear(parameter.getYear());
            bookList.add(bt);
        }
        return bt;
    }

    public ee.ttu.idu0075._2015.ws.book.GetBookListResponse getBookList(ee.ttu.idu0075._2015.ws.book.GetBookListRequest parameter) {
        GetBookListResponse response=new GetBookListResponse();
        if(parameter.getToken().equalsIgnoreCase(RequiredToken)){
            for(BookType bookType:bookList){
                response.getBook().add(bookType);
            }
        }
        return response;
    
    }

    public ee.ttu.idu0075._2015.ws.book.OrderType getOrder(ee.ttu.idu0075._2015.ws.book.GetOrderRequest parameter) {
        OrderType ot=null;
        if(parameter.getToken().equalsIgnoreCase(RequiredToken)){
            for(int i=0;i<orderList.size();i++){
                if(orderList.get(i).getId().equals(parameter.getId())){
                    ot=orderList.get(i);
                }
            }
        }
        return ot;
    }

    public ee.ttu.idu0075._2015.ws.book.OrderType addOrder(ee.ttu.idu0075._2015.ws.book.AddOrderRequest parameter) {
        OrderType ot=new OrderType();
        if(parameter.getToken().equalsIgnoreCase(RequiredToken)){
            ot.setId(BigInteger.valueOf(nextOrderId++));
            ot.setOrderNo(parameter.getOrderNo());
            ot.setOrderDate(parameter.getOrderDate());
            ot.setDueDate(parameter.getDueDate());
            ot.setCustomerName(parameter.getCustomerName());
            OrderBookListType oblt=new OrderBookListType();
            ot.setOrderBookList(oblt);
            orderList.add(ot);
        }
        return ot;
    }

    public ee.ttu.idu0075._2015.ws.book.GetOrderListResponse getOrderList(ee.ttu.idu0075._2015.ws.book.GetOrderListRequest parameter) {
        GetOrderListResponse response=new GetOrderListResponse();
        if(parameter.getToken().equalsIgnoreCase(RequiredToken)){
            for(OrderType orderType:orderList){
                int compareStart=orderType.getOrderDate().compare(parameter.getStartDate());                
                int compareEnd=orderType.getOrderDate().compare(parameter.getEndDate());
                if(compareStart==1&&compareEnd==-1||compareStart==0||compareEnd==0){
                    if(parameter.getHasRelatedBooks().equalsIgnoreCase("jah")&&!orderType.getOrderBookList().getOrderBook().isEmpty())
                        response.getOrder().add(orderType);
                    if(parameter.getHasRelatedBooks().equalsIgnoreCase("ei")&&orderType.getOrderBookList().getOrderBook().isEmpty())
                        response.getOrder().add(orderType);
                }
                
            }
        }
        return response;
    }

    public ee.ttu.idu0075._2015.ws.book.OrderBookListType getOrderBookList(ee.ttu.idu0075._2015.ws.book.GetOrderBookListRequest parameter) {
        OrderBookListType oblt=orderList.get(parameter.getOrderId().intValue()-1).getOrderBookList();
        return oblt;
    }

    public ee.ttu.idu0075._2015.ws.book.OrderBookType addOrderBook(ee.ttu.idu0075._2015.ws.book.AddOrderBookRequest parameter) {
        OrderBookType obt=null;
        if(parameter.getToken().equalsIgnoreCase(RequiredToken)){
            OrderType ot=orderList.get(parameter.getOrderId().intValue()-1);
            BookType bt=bookList.get(parameter.getBookId().intValue()-1);
            obt=new OrderBookType();
            obt.setBook(bt);
            obt.setQuantity(parameter.getQuantity());
            obt.setUnitPrice(parameter.getUnitPrice());
            OrderBookListType oblt=orderList.get(parameter.getOrderId().intValue()-1).getOrderBookList();
            oblt.getOrderBook().add(obt);
            ot.setOrderBookList(oblt);
            
            double NetAmount=0.0;
            for(int i=0;i<ot.getOrderBookList().getOrderBook().size();i++){
                System.out.println(ot.getOrderBookList().getOrderBook().size());
                double quantity=ot.getOrderBookList().getOrderBook().get(i).getQuantity();
                double price=ot.getOrderBookList().getOrderBook().get(i).getUnitPrice();
                double CurrentBookNetAmount=quantity*price;
                NetAmount=NetAmount+CurrentBookNetAmount;               
            }
            ot.setNetAmount(NetAmount);
            ot.setVatAmount(NetAmount*1.2);
        }
        return obt;
    }
    
}
