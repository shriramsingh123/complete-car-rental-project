package com.shriram.Car_Rental_Spring.dto;

import com.shriram.Car_Rental_Spring.enums.BookCarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookACarDto {

    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;

    private Long carId;
    private Long userId;

    private String username;
    private String email;
}
