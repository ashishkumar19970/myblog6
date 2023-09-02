package com.blopapi.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details){
        this.details=details;
        this.message=message;
        this.timestamp=timestamp;
    }

}
