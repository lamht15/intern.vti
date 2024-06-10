package com.vti.Final.Java.Advance.config.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice//Xử lý các Exception
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    //Lấy ngôn ngữ = MessageSource - interface có kn lấy data từ các file Config
    @Autowired
    private MessageSource messageSource;

    private String getMessage(String key){
        return messageSource.getMessage(key, null, "Default Message", LocaleContextHolder.getLocale());
    }

    //Xử lý các Exception # nếu k thuộc các Exception kia
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception exception){
        String message = getMessage("Exception.message");
        String detailMessage = exception.getLocalizedMessage();
        int code = 1;
        String moreInformation = "http://localhost:8080/api/v1/exception/1";

        ErrorResponse errorResponse = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        log.error(detailMessage, exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //URL Not Found
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception, HttpHeaders headers, HttpStatus status, WebRequest webRequest){
        String message = getMessage("NoHandlerFoundException.message") + exception.getHttpMethod() + " " + exception.getRequestURL();
        String detailMessage = exception.getLocalizedMessage();
        int code = 2;
        String moreInformation = "http://localhost:8080/api/v1/exception/2";

        ErrorResponse errorResponse = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        log.error(detailMessage, exception);
        return new ResponseEntity<>(errorResponse, status);
    }

    //Not support HTTP Method
    private String getMessageFromHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        String message = exception.getMethod() + " " + getMessage("HttpRequestMethodNotSupportedException.message");
        for (HttpMethod method : exception.getSupportedHttpMethods()){
            message += method + " ";
        }
        return message;
    }
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest webRequest)
    {
        String message = getMessageFromHttpRequestMethodNotSupportedException(exception);
        String detailMessage = exception.getLocalizedMessage();
        int code = 3;
        String moreInformation = "http://localhost:8080/api/v1/exception/3";

        ErrorResponse errorResponse = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        log.error(detailMessage, exception);
        return new ResponseEntity<>(errorResponse, status);
    }

    //Media type not supported
    public String getMessageFromHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception){
        String message = exception.getContentType() + getMessage("HttpMediaTypeNotSupportedException.message");
        for (MediaType mediaType : exception.getSupportedMediaTypes()){
            message += mediaType + " ";
        }
        return message;
    }
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest webRequest){
        String message = getMessageFromHttpMediaTypeNotSupportedException(exception);
        String detailMessage = exception.getLocalizedMessage();
        int code = 4;
        String moreInformation = "http://localhost:8080/api/v1/exception/4";

        ErrorResponse errorResponse = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        log.error(detailMessage, exception);
        return  new ResponseEntity<>(errorResponse, status);
    }

    //@Valid - Có nhưng vi phạm >< Thiếu
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest webRequest){
        String message = getMessage("MethodArgumentNotValidException.mesage");
        String detailMessage = exception.getLocalizedMessage();

        Map<String, String> errors = new HashMap<>();//Tạo list chứa các errors
        for (ObjectError error : exception.getBindingResult().getAllErrors()){//Lấy hết errors vi phạm - trường username, pass,...
            String fieldname = ((FieldError) error).getField();//Lấy tên field
            String errorMessage = error.getDefaultMessage();//Lấy mô tả lỗi
            errors.put(fieldname, errorMessage);//Ném vô list errors
        }

        int code = 5;
        String moreInformation = "http://localhost:8080/api/v1/exception/5";

        ErrorResponse errorResponse = new ErrorResponse(message, detailMessage, errors, code, moreInformation);
        log.error(detailMessage, exception);
        return new ResponseEntity<>(errorResponse, status);
    }

    //Thiếu Parameter
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status, WebRequest webRequest){
        String message = exception.getParameterName() + " " + getMessage("MissingServletRequestParameterException.message");
        String detailMessage = exception.getLocalizedMessage();
        int code = 6;
        String moreInformation = "http://localhost:8080/api/v1/exception/6";

        ErrorResponse errorResponse = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        log.error(detailMessage, exception);
        return new ResponseEntity<>(errorResponse, status);
    }

    //Check kiểu dữ liệu
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception){
        String message = exception.getName() + " " + getMessage("MethodArgumentTypeMismatchException.message") + exception.getRequiredType();
        String detailMessage = exception.getLocalizedMessage();
        int code = 7;
        String moreInformation = "http://localhost:8080/api/v1/exception/7";

        ErrorResponse errorResponse = new ErrorResponse(message, detailMessage, null, code, moreInformation);
        log.error(detailMessage, exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
