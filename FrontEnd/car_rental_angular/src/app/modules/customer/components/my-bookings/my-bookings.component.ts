import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrls: ['./my-bookings.component.scss']
})
export class MyBookingsComponent {

  constructor(private service: CustomerService){this.getMyBookings()}

  bookings: any;
  isSpinning = false;

  getMyBookings(){
    this.isSpinning = true;
    this.service.getBookingsByUserId().subscribe((res) =>{
      this.isSpinning = false;
      console.log(res);
      this.bookings=res;
    })
  }

}
