package com.shriram.Car_Rental_Spring.controller;

import com.shriram.Car_Rental_Spring.dto.BookACarDto;
import com.shriram.Car_Rental_Spring.dto.CarDto;
import com.shriram.Car_Rental_Spring.dto.SearchCarDto;
import com.shriram.Car_Rental_Spring.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping(path = "/car",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postCar(@ModelAttribute CarDto carDto) throws IOException {
        Long imgLen=carDto.getImage().getSize();
        boolean success = adminService.postCar(carDto);
        if(success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars(){
        return ResponseEntity.ok(adminService.getAllCars());
    }



    @GetMapping("/hello")
    public String welcome(){
        return "Your Admin Controller working fine";
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        adminService.deleteCar(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id){
        CarDto carDto = adminService.getCarById(id);
        return ResponseEntity.ok(carDto);
    }

    @PutMapping("/car/{carId}")
    public ResponseEntity<Void> updateCar(@PathVariable Long carId,@ModelAttribute CarDto carDto){

        try {
            boolean success = adminService.updateCar(carId, carDto);
            if (success) return ResponseEntity.status(HttpStatus.OK).build();
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }

    @GetMapping("/car/bookings")
    public ResponseEntity<List<BookACarDto>> getBookings(){
        return ResponseEntity.ok(adminService.getBookings());
    }

    @GetMapping("/car/booking/{bookingId}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId,@PathVariable String status){
        boolean success = adminService.changeBookingStatus(bookingId, status);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto){
        return ResponseEntity.ok(adminService.searchCar(searchCarDto));
    }
}
