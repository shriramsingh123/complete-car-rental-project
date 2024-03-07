import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AdminService } from 'src/app/modules/admin/services/admin.service';

@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrls: ['./search-car.component.scss']
})
export class SearchCarComponent {

  isSpinning= false;
  searchCarForm!: FormGroup;
  listOfOptions: Array<{label:string; value: string}> = [];
  listOfBrands = ["BMW","AUDI","FERARAI","TESLA","VOLVO","TOYOTA","HONDA","FORD","NISSAN","HYUNDAI","LEXUS","KIA"];
  listOfType = ["Petrol","Hybrid","Diesel","Electric","CNG"];
  listOfColor =["Red","White","Blue","Black","Orange","Grey","Silver"];
  listOfTransmission = ["Manual","Automatic"];
  cars : any = [];


  constructor(private fb: FormBuilder,private adminService:AdminService){
    this.searchCarForm = this.fb.group({
      brand:[null],
      type:[null],
      transmission:[null],
      color:[null]
    })
  }

  searchCar(){
    this.isSpinning=false;
    console.log(this.searchCarForm.value);
    this.adminService.searchCar(this.searchCarForm.value).subscribe((res) => {
      res.carDtoList.forEach((element: any) => {
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImage;
        this.cars.push(element);
      });
      this.isSpinning=false;
    })
  }

}
