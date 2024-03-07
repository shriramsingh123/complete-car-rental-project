package com.shriram.Car_Rental_Spring.services.customer;

import com.shriram.Car_Rental_Spring.dto.BookACarDto;
import com.shriram.Car_Rental_Spring.dto.CarDto;
import com.shriram.Car_Rental_Spring.dto.CarDtoListDto;
import com.shriram.Car_Rental_Spring.dto.SearchCarDto;
import com.shriram.Car_Rental_Spring.entity.BookACar;
import com.shriram.Car_Rental_Spring.entity.Car;
import com.shriram.Car_Rental_Spring.entity.User;
import com.shriram.Car_Rental_Spring.enums.BookCarStatus;
import com.shriram.Car_Rental_Spring.repository.BookACarRepository;
import com.shriram.Car_Rental_Spring.repository.CarRepository;
import com.shriram.Car_Rental_Spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final BookACarRepository bookACarRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookACarDto bookACarDto) {
        Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
        Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());
        if (optionalCar.isPresent() && optionalUser.isPresent()){
            Car existingCar = optionalCar.get();
            BookACar bookACar = new BookACar();
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(existingCar);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);
            bookACar.setFromDate(bookACarDto.getFromDate());
            bookACar.setToDate(bookACarDto.getToDate());
            long diffInMilliSeconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
            bookACar.setDays(days);
            bookACar.setPrice(existingCar.getPrice() * days);
            bookACarRepository.save(bookACar);
            return true;
        }
        return false;
    }

    @Override
    public CarDto getCarById(Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }
    @Override
    public List<BookACarDto> getBookingsByUserId(Long userId) {
        List<BookACar> allByUserId = bookACarRepository.findAllByUserId(userId);
        List<BookACarDto> bookACarDtoList = new ArrayList<>();
        for (BookACar bookACar : allByUserId){
            BookACarDto bookACarDto = new BookACarDto();
            bookACarDto.setId(bookACar.getId());
            bookACarDto.setFromDate(bookACar.getFromDate());
            bookACarDto.setToDate(bookACar.getToDate());
            bookACarDto.setDays(bookACar.getDays());
            bookACarDto.setPrice(bookACar.getPrice());
            bookACarDto.setBookCarStatus(bookACar.getBookCarStatus());
            bookACarDto.setEmail(bookACar.getUser().getEmail());
            bookACarDto.setUsername(bookACar.getUser().getUsername());
            bookACarDto.setUserId(bookACar.getUser().getId());
            bookACarDto.setCarId(bookACar.getCar().getId());
            bookACarDtoList.add(bookACarDto);
        }
        return bookACarDtoList;
    }

    @Override
    public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
        Car car = new Car();
        car.setBrand(searchCarDto.getBrand());
        car.setType(searchCarDto.getType());
        car.setTransmission(searchCarDto.getTransmission());
        car.setColor(searchCarDto.getColor());
        ExampleMatcher exampleMatcher =
                ExampleMatcher.matching()
                        .withMatcher("brand",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("type",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("transmission",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("color",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Car> carExample = Example.of(car,exampleMatcher);
        List<Car> carList = carRepository.findAll(carExample);
        CarDtoListDto carDtoListDto = new CarDtoListDto();
        carDtoListDto.setCarDtoList(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));
        return carDtoListDto;
    }


}
