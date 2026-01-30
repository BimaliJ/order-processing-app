package com.demo.order.app.exception;

public class OrderProcessingException extends Exception
{
    public OrderProcessingException(Exception e)
    {
        super(e);
    }

    public OrderProcessingException(String message, Object... params){
        super(String.format(message, params));
    }
}
