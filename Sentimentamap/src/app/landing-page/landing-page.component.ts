import { UserService } from './../user.service';
import { LoginComponent } from './../login/login.component';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OutsidePlacement, RelativePosition, Toppy } from 'toppy';
import { FormBuilder, Validators, FormGroup, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';
import { faLessThanEqual } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {
  showLogin: boolean = false;
  loginForm!: FormGroup;
  submitted:boolean = false;
  userStatus = 0;

  showRegister: boolean = false;
  registerForm!: FormGroup;
  submittedRegister:boolean = false;

  dataTabVisible: boolean = true;
  keyTabVisible: boolean = true;

  constructor(private formBuilder: FormBuilder, public router:Router, private userService:UserService) { }

  show()
  {
    this.showLogin = true; // Show-Hide Modal Check

  }
  //Bootstrap Modal Close event
  hide()
  {
    this.showLogin = false;
  }

  showReg()
  {
    this.showRegister = true; // Show-Hide Modal Check

  }
  //Bootstrap Modal Close event
  hideReg()
  {
    this.showRegister = false;
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });

    this.registerForm = this.formBuilder.group({
      usernameReg: ['', [Validators.required]],
      passwordReg: ['', [Validators.required, Validators.minLength(6)]],
      passwordConfirmationReg: ['', [Validators.required, Validators.minLength(6)]],
      emailReg: ['', [Validators.required, Validators.email]]
    });

    this.userStatus = this.userService.getUserStatus();
  }

  get f() { return this.loginForm.controls; }
  async onSubmit() {
      this.submitted = true;
      // stop here if form is invalid
      if (this.loginForm.invalid) {
          return;
      }
      if(this.submitted)
      {
        this.showLogin = false;
        let response:any = await this.login(this.loginForm.get("username")?.value, this.loginForm.get("password")?.value)
        if (response == true) {this.userStatus = 1}
      }
  }

  get fr() { return this.registerForm.controls; }
  async onSubmitRegister() {
      this.submittedRegister = true;
      // stop here if form is invalid
      if (this.registerForm.invalid ||(this.registerForm.get("passwordReg")?.value != this.registerForm.get("passwordConfirmationReg")?.value)) {
          return;
      }
      if(this.submittedRegister)
      {
        console.log(this.registerForm.get("passwordReg")?.value)
        await this.register(
          this.registerForm.get("usernameReg")?.value,
          this.registerForm.get("passwordReg")?.value,
          this.registerForm.get("emailReg")?.value,
        );
        this.showRegister = false;
      }
  }

  checkPasswords: ValidatorFn = (group: AbstractControl):  ValidationErrors | null => {
    let pass = this.registerForm.get('passwordReg')?.value;
    let confirmPass = this.registerForm.get('passwordConfirmationReg')?.value;
    return pass === confirmPass ? null : { notSame: true }
  }

  async register(username:String, password:String, email:String){
    let response:any = await this.userService.register(username, password, email)
  }

  async login(username:string, password:string):Promise<Boolean>{
    let response:any = await this.userService.getUserDetails(username, password)
    return response
  }

  toggleDataTab(){
    this.dataTabVisible = this.dataTabVisible == false ? true : false;
  }

  toggleKeyTab(){
    this.keyTabVisible = this.keyTabVisible == false ? true : false;
  }

  logout(){
    this.userService.logout();
    this.userStatus = 0;
  }


}
