import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { StorageService } from '../../services/storage/storage.service';
import { Router } from '@angular/router';
import { NzMessageComponent, NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  isSpinning:boolean=false;

  loginFrom!: FormGroup;

  constructor(private fb: FormBuilder ,
              private authService: AuthService,
              private router : Router,
              private message : NzMessageService){}

  ngOnInit(){
      this.loginFrom = this.fb.group({
        email: [null,[Validators.email, Validators.required]],
        password: [null,[Validators.required]]
      })
  }

  login(){
    console.log(this.loginFrom.value);
    this.authService.login(this.loginFrom.value).subscribe((res)=>{
      console.log(res);
      if(res.userId != null){
        const user ={
          id : res.userId,
          role:res.userRole
        }
        StorageService.saveUser(user);
        StorageService.saveToken(res.jwt);
        if(StorageService.isAdminLoggedIn()){
          this.router.navigateByUrl("/admin/dashboard");
        }else if (StorageService.isCustomerLoggedIn()){
          this.router.navigateByUrl("/customer/dashboard");
        }else{
          this.message.error("Bad credentials",{ nzDuration: 50000});
        }
      }
    })
  }

}
