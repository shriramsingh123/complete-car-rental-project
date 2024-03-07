package com.shriram.Car_Rental_Spring.services.admin;

import com.shriram.Car_Rental_Spring.dto.BookACarDto;
import com.shriram.Car_Rental_Spring.dto.CarDto;
import com.shriram.Car_Rental_Spring.dto.CarDtoListDto;
import com.shriram.Car_Rental_Spring.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean postCar(CarDto carDto) throws IOException;
    List<CarDto> getAllCars();
    void deleteCar(Long id);
    CarDto getCarById(Long id);
    boolean updateCar(Long carId,CarDto carDto) throws IOException;

    List<BookACarDto> getBookings();

    boolean changeBookingStatus(Long bookingId,String status);

    CarDtoListDto searchCar(SearchCarDto searchCarDto);


}
