import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-get-bookings',
  templateUrl: './get-bookings.component.html',
  styleUrls: ['./get-bookings.component.scss']
})
export class GetBookingsComponent {

  bookings : any;
  isSpinning = false;

  constructor(private adminService : AdminService,private message: NzMessageService){
    this.getBookings();
  }

  getBookings(){
    this.isSpinning = true;
    this.adminService.getCarBookings().subscribe((res) => {
      this.isSpinning = false;
      console.log(res);
      this.bookings = res;
    })
  }

  changeBookingStatus(bookingId: number , status: string){
    this.isSpinning=true;
    console.log(bookingId,status);
    this.adminService.changeBookingStatus(bookingId,status).subscribe((res) => {
      this.isSpinning=false;
      console.log(res);
      this.getBookings();
      this.message.success("Booking Status Changed Successfully",{nzDuration: 5000});
    },error =>{
      this.message.error("Something went wrong",{nzDuration: 5000});
    })
  }

}
